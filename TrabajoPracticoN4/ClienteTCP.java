import java.io.*;
import java.net.*;

public class ClienteTCP {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;
    private static final int CANTIDAD_TRANSACCIONES = 1000;

    public static void main(String[] args) {
        try {
            Thread.sleep(1000); // Esperar a que el servidor inicialice
            Socket socket = new Socket(HOST, PUERTO);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Transaccion[] lista = new Transaccion[CANTIDAD_TRANSACCIONES];
            for (int i = 0; i < CANTIDAD_TRANSACCIONES; i++) {
                lista[i] = new Transaccion(i + 1, "NodoA", 1500.50 + i, System.currentTimeMillis());
            }

            // --- TRANSMISIÓN JSON ---
            long inicioJSON = System.nanoTime();
            long totalBytesJSON = 0;

            for (Transaccion t : lista) {
                byte[] bytes = ParserMensajes.aJSON(t);
                dos.writeInt(bytes.length);
                dos.write(bytes);
                totalBytesJSON += (4 + bytes.length);
            }
            dos.flush();
            long finJSON = System.nanoTime();
            double tiempoJSONms = (finJSON - inicioJSON) / 1e6;

            // --- TRANSMISIÓN BINARIA DIRECTA ---
            long inicioBinario = System.nanoTime();
            long totalBytesBinario = 0;

            for (Transaccion t : lista) {
                byte[] bytes = ParserMensajes.aBinario(t);
                dos.write(bytes);
                totalBytesBinario += bytes.length;
            }
            dos.flush();
            long finBinario = System.nanoTime();
            double tiempoBinarioms = (finBinario - inicioBinario) / 1e6;

            System.out.println("\n=== RESULTADOS DE DESEMPEÑO (CLIENTE) ===");
            System.out.printf("JSON    -> Total: %d bytes | Tiempo: %.2f ms\n", totalBytesJSON, tiempoJSONms);
            System.out.printf("BINARIO -> Total: %d bytes | Tiempo: %.2f ms\n", totalBytesBinario, tiempoBinarioms);
            System.out.printf("Reducción de tamaño: %.2f%%\n", (1.0 - ((double) totalBytesBinario / totalBytesJSON)) * 100);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}