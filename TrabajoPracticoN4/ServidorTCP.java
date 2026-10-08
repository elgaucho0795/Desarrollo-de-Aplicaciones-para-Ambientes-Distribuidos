import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServidorTCP {
    private static final int PUERTO = 12345;
    private static final int CANTIDAD_TRANSACCIONES = 1000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("[SERVIDOR] Escuchando en el puerto " + PUERTO + "...");
            Socket socket = serverSocket.accept();
            System.out.println("[SERVIDOR] Cliente conectado desde: " + socket.getInetAddress());

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // 1. Recepción en Formato JSON
            long bytesJSON = 0;
            for (int i = 0; i < CANTIDAD_TRANSACCIONES; i++) {
                int longitud = dis.readInt();
                byte[] buffer = new byte[longitud];
                dis.readFully(buffer);
                bytesJSON += (4 + longitud);

                String jsonStr = new String(buffer, StandardCharsets.UTF_8);
                ParserMensajes.desdeJSON(jsonStr);
            }
            System.out.println("[SERVIDOR] 1000 Transacciones JSON recibidas y procesadas.");
            System.out.println("[SERVIDOR] Total bytes recibidos (JSON): " + bytesJSON + " bytes.");

            // 2. Recepción en Formato Binario Directo
            long bytesBinario = 0;
            for (int i = 0; i < CANTIDAD_TRANSACCIONES; i++) {
                Transaccion t = ParserMensajes.desdeBinario(dis);
                // 4 (int) + 2 (header UTF) + len(origen) + 8 (double) + 8 (long)
                bytesBinario += 4 + (2 + t.getOrigen().getBytes(StandardCharsets.UTF_8).length) + 8 + 8;
            }
            System.out.println("[SERVIDOR] 1000 Transacciones Binarias recibidas y procesadas.");
            System.out.println("[SERVIDOR] Total bytes recibidos (Binario): " + bytesBinario + " bytes.");

            socket.close();
            System.out.println("[SERVIDOR] Finalizado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}