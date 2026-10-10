import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorRMI {
    private static final int PUERTO = 1099;
    private static final String NOMBRE_SERVICIO = "ServicioComputo";

    public static void main(String[] args) {
        try {
            // Crear el registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(PUERTO);

            // Instanciar y registrar el servicio remoto
            ServicioRemoto servicio = new ServicioRemotoImpl();
            registry.rebind(NOMBRE_SERVICIO, servicio);

            System.out.println("==================================================");
            System.out.println("[SERVIDOR RMI] Registro RMI iniciado en puerto " + PUERTO);
            System.out.println("[SERVIDOR RMI] Servicio publicado como: '" + NOMBRE_SERVICIO + "'");
            System.out.println("[SERVIDOR RMI] Esperando invocaciones del cliente...");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("[SERVIDOR ERROR] Excepción al iniciar el servidor:");
            e.printStackTrace();
        }
    }
}