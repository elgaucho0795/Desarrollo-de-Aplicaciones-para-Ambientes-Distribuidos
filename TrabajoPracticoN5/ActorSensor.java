import java.util.Random;

public class ActorSensor implements Runnable {
    private final String idSensor;
    private final ActorProcesador procesador;
    private final int cantidadMensajes;

    public ActorSensor(String idSensor, ActorProcesador procesador, int cantidadMensajes) {
        this.idSensor = idSensor;
        this.procesador = procesador;
        this.cantidadMensajes = cantidadMensajes;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < cantidadMensajes; i++) {
            double valorSimulado = 10.0 + (random.nextDouble() * 30.0); // Valores entre 10.0 y 40.0
            MensajeLectura mensaje = new MensajeLectura(idSensor, valorSimulado, System.currentTimeMillis());
            
            // Operación Enviar (Send) asincrónica
            procesador.send(mensaje);
        }
    }
}