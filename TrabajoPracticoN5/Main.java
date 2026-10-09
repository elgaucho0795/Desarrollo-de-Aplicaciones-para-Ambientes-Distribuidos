public class Main {
    private static final int TOTAL_MENSAJES = 500;
    private static final int NUM_SENSORES = 5;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== INICIANDO MODELO DE ACTORES (TP5) ===");

        // 1. Crear (Spawn): Instanciar los actores y sus buzones
        ActorProcesador procesador = new ActorProcesador();
        Thread hiloProcesador = new Thread(procesador, "Hilo-ActorProcesador");
        hiloProcesador.start();

        // Crear e iniciar 5 hilos de Sensores que enviarán 100 mensajes cada uno (total 500)
        int mensajesPorSensor = TOTAL_MENSAJES / NUM_SENSORES;
        Thread[] hilosSensores = new Thread[NUM_SENSORES];

        for (int i = 0; i < NUM_SENSORES; i++) {
            ActorSensor sensor = new ActorSensor("Sensor-" + (i + 1), procesador, mensajesPorSensor);
            hilosSensores[i] = new Thread(sensor, "Hilo-Sensor-" + (i + 1));
            
            // 2. Enviar (Send): Disparar la generación concurrente de mensajes
            hilosSensores[i].start();
        }

        // Esperar a que todos los sensores terminen de enviar sus mensajes
        for (Thread hiloSensor : hilosSensores) {
            hiloSensor.join();
        }

        // Notificar al procesador que detenga la recepción y procese los remanentes
        procesador.detener();
        hiloProcesador.join();

        System.out.println("=== FIN DE LA EJECUCIÓN ===");
    }
}