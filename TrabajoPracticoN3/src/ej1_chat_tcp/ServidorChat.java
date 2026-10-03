package ej1_chat_tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServidorChat {
    private static final int PUERTO = 5000;
    private static Set<ManejadorCliente> clientes = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("=== Servidor TCP de Chat iniciado en el puerto " + PUERTO + " ===");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Nueva conexión entrante desde: " + socketCliente.getInetAddress().getHostAddress());

                ManejadorCliente cliente = new ManejadorCliente(socketCliente);
                clientes.add(cliente);

                Thread hiloCliente = new Thread(cliente);
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    public static void retransmitirMensaje(String mensaje, ManejadorCliente emisor) {
        synchronized (clientes) {
            for (ManejadorCliente cliente : clientes) {
                if (cliente != emisor) {
                    cliente.enviarMensaje(mensaje);
                }
            }
        }
    }

    public static void desconectarCliente(ManejadorCliente cliente) {
        clientes.remove(cliente);
        System.out.println("Un cliente se ha desconectado. Clientes activos: " + clientes.size());
    }
}