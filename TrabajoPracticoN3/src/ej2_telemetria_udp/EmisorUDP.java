package ej2_telemetria_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EmisorUDP {
    private static final String HOST = "localhost";
    private static final int PUERTO = 6000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress direccionServidor = InetAddress.getByName(HOST);
            int contador = 1;

            System.out.println("=== Emisor UDP de Telemetría iniciado ===");

            while (true) {
                String mensaje = "ALERTA_TELEMETRIA #" + contador + " - Estado: OK";
                byte[] buffer = mensaje.getBytes();

                DatagramPacket paquete = new DatagramPacket(
                        buffer,
                        buffer.length,
                        direccionServidor,
                        PUERTO
                );

                socket.send(paquete);
                System.out.println("Enviado: " + mensaje);

                contador++;
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            System.err.println("Error en el Emisor UDP: " + e.getMessage());
        }
    }
}