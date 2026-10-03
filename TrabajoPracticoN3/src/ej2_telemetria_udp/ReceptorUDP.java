package ej2_telemetria_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class ReceptorUDP {
    private static final int PUERTO = 6000;
    private static final int TIMEOUT_MS = 5000; // 5 segundos

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            // Configuración del tiempo límite de espera (Timeout)
            socket.setSoTimeout(TIMEOUT_MS);
            System.out.println("=== Receptor UDP iniciado en el puerto " + PUERTO + " (Timeout: 5s) ===");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                try {
                    // Espera datagramas (bloqueante hasta el timeout)
                    socket.receive(paquete);
                    String mensajeRecibido = new String(paquete.getData(), 0, paquete.getLength());
                    System.out.println("Recibido de [" + paquete.getAddress() + ":" + paquete.getPort() + "]: " + mensajeRecibido);

                } catch (SocketTimeoutException e) {
                    // Captura del timeout si pasan 5 segundos sin recibir nada
                    System.out.println("⚠️ ADVERTENCIA: No se han recibido alertas/telemetría en los últimos " 
                            + (TIMEOUT_MS / 1000) + " segundos. Reintentando escucha...");
                }
            }
        } catch (Exception e) {
            System.err.println("Error crítico en el Receptor UDP: " + e.getMessage());
        }
    }
}