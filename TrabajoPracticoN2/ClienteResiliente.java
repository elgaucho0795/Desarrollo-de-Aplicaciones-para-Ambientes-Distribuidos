import java.net.Socket;
import java.util.Random;

public class ClienteResiliente {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int puerto = 5500;
        
        int maxIntentos = 5;       // Límite de reintentos
        int baseMs = 1000;         // Tiempo base en milisegundos (1 segundo)
        
        int intentosRealizados = 0;
        boolean exito = false;
        
        Random random = new Random();
        
        // Iniciamos el cronómetro para la métrica de tiempo total
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("Iniciando Cliente Resiliente...");

        while (intentosRealizados < maxIntentos && !exito) {
            intentosRealizados++;
            System.out.println("\n--- Intento " + intentosRealizados + " de " + maxIntentos + " ---");
            
            try {
                System.out.println("Conectando al servidor...");
                // Intentamos conectar al servidor
                Socket socket = new Socket(host, puerto);
                
                // Si llegamos a esta línea, la conexión fue exitosa
                exito = true;
                System.out.println("¡Conexión exitosa con el servidor!");
                
                // Cerramos el socket para ser prolijos
                socket.close();
                
            } catch (Exception e) {
                System.out.println("Error de conexión: " + e.getMessage());
                
                // Si falló pero nos quedan intentos, calculamos la espera con JITTER
                if (intentosRealizados < maxIntentos) {
                    // Jitter: componente aleatorio entre 0 y 500 ms
                    int jitter = random.nextInt(501); 
                    
                    // Fórmula: (Base * 2^(intento-1)) + Random(0, 500)
                    long tiempoEsperado = (long) (baseMs * Math.pow(2, intentosRealizados - 1)) + jitter;
                    
                    System.out.println("Aplicando Backoff Exponencial con Jitter...");
                    System.out.println("Esperando " + tiempoEsperado + " ms antes de reintentar.");
                    
                    try {
                        Thread.sleep(tiempoEsperado); // Pausa el hilo por el tiempo calculado
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        // Frenamos el cronómetro
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // EJERCICIO 2: IMPRESIÓN DE MÉTRICAS
        System.out.println("\n==================================");
        System.out.println("      MÉTRICAS DE RESILIENCIA     ");
        System.out.println("==================================");
        System.out.println("Estado final de la petición: " + (exito ? "Éxito" : "Fallo definitivo"));
        System.out.println("Cantidad de intentos realizados: " + intentosRealizados);
        System.out.println("Tiempo total transcurrido: " + tiempoTotal + " ms");
        System.out.println("==================================");
    }
}