import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProcesador implements Runnable {
    // Buzón de mensajes FIFO asincrónico (Mailbox)
    private final BlockingQueue<MensajeLectura> mailbox = new LinkedBlockingQueue<>();
    
    // Estado interno privado (NO expuesto al exterior)
    private int contadorMensajes = 0;
    private double sumaValores = 0.0;
    private boolean activo = true;

    // Enviar (Send): Operación elemental asincrónica para colar mensajes en el mailbox
    public void send(MensajeLectura mensaje) {
        mailbox.offer(mensaje);
    }

    // Detener de forma limpia el procesamiento
    public void detener() {
        this.activo = false;
    }

    @Override
    public void run() {
        while (activo || !mailbox.isEmpty()) {
            try {
                // Tomar el siguiente mensaje del buzón (FIFO)
                MensajeLectura mensaje = mailbox.poll();
                if (mensaje != null) {
                    // Designar (State Change): Actualizar el estado interno
                    contadorMensajes++;
                    sumaValores += mensaje.getValor();
                    double promedio = sumaValores / contadorMensajes;

                    System.out.printf("[Procesador] Msg #%d recibido de %s | Valor: %.2f | Promedio Acumulado: %.2f%n",
                            contadorMensajes, mensaje.getIdSensor(), mensaje.getValor(), promedio);
                } else {
                    Thread.sleep(10); // Pausa breve si el buzón está momentáneamente vacío
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n=== RESULTADO FINAL DEL PROCESADOR ===");
        System.out.println("Total de mensajes procesados: " + contadorMensajes);
        System.out.printf("Suma acumulada: %.2f%n", sumaValores);
        System.out.printf("Promedio histórico final: %.2f%n", (contadorMensajes > 0 ? sumaValores / contadorMensajes : 0.0));
    }
}