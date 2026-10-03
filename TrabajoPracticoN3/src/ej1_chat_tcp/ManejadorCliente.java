package ej1_chat_tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorCliente implements Runnable {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String nombreCliente;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            salida.println("Bienvenido al Chat. Ingrese su nombre/Nick:");
            this.nombreCliente = entrada.readLine();
            
            if (this.nombreCliente == null || this.nombreCliente.trim().isEmpty()) {
                this.nombreCliente = "Cliente-" + socket.getPort();
            }

            System.out.println("--> '" + nombreCliente + "' se ha unido al chat.");
            ServidorChat.retransmitirMensaje("[SISTEMA]: " + nombreCliente + " se ha conectado.", this);

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("EXIT") || mensaje.equalsIgnoreCase("SALIR")) {
                    break;
                }
                String mensajeFormateado = nombreCliente + ": " + mensaje;
                System.out.println(mensajeFormateado);
                ServidorChat.retransmitirMensaje(mensajeFormateado, this);
            }
        } catch (IOException e) {
            System.out.println("Conexión interrumpida con el cliente: " + nombreCliente);
        } finally {
            ServidorChat.desconectarCliente(this);
            ServidorChat.retransmitirMensaje("[SISTEMA]: " + nombreCliente + " ha abandonado el chat.", this);
            cerrarConexion();
        }
    }

    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    private void cerrarConexion() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}