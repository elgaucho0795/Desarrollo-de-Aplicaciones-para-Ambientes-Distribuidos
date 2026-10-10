import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClienteRMI {
    private static final String HOST = "localhost";
    private static final int PUERTO = 1099;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("[CLIENTE] Conectando al registro RMI en " + HOST + ":" + PUERTO + "...");
            Registry registry = LocateRegistry.getRegistry(HOST, PUERTO);

            // Obtención transparente del Stub remoto
            ServicioRemoto servicio = (ServicioRemoto) registry.lookup("ServicioComputo");
            System.out.println("[CLIENTE] Conexión establecida exitosamente con el objeto remoto.");

            boolean salir = false;
            while (!salir) {
                System.out.println("\n================ MENU INTERACTIVO RMI ================");
                System.out.println("1. Procesamiento Matemático (Estadísticas)");
                System.out.println("2. Validación de CUIT / DNI");
                System.out.println("3. Filtrado de Cadenas por Patrón");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");

                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        System.out.print("Ingrese números separados por espacio (ej: 10.5 20.0 5.2 14.8): ");
                        String[] entrada = scanner.nextLine().split("\\s+");
                        List<Double> numeros = new ArrayList<>();
                        for (String str : entrada) {
                            try { numeros.add(Double.parseDouble(str)); } catch (NumberFormatException ignored) {}
                        }
                        if (numeros.isEmpty()) {
                            System.out.println("[!] Debe ingresar al menos un número válido.");
                        } else {
                            ResultadoEstadistico res = servicio.calcularEstadisticas(numeros);
                            System.out.println("\n--- RESULTADO REMOTO ---");
                            System.out.println(res);
                        }
                        break;

                    case "2":
                        System.out.print("Ingrese el CUIT a validar (ej: 20381234567 o 20-38123456-7): ");
                        String cuit = scanner.nextLine();
                        boolean esValido = servicio.validarCuit(cuit);
                        System.out.println("\n--- RESULTADO REMOTO ---");
                        System.out.println("El CUIT " + cuit + " es: " + (esValido ? "VÁLIDO" : "INVÁLIDO"));
                        break;

                    case "3":
                        System.out.print("Ingrese palabras separadas por coma: ");
                        String[] palabras = scanner.nextLine().split(",");
                        List<List<String>> lista = new ArrayList<>();
                        List<String> cadenas = new ArrayList<>();
                        for (String p : palabras) cadenas.add(p.trim());

                        System.out.print("Ingrese el patrón de búsqueda: ");
                        String patron = scanner.nextLine();

                        List<String> filtradas = servicio.filtrarCadenas(cadenas, patron);
                        System.out.println("\n--- RESULTADO REMOTO ---");
                        System.out.println("Cadenas coincidentes: " + filtradas);
                        break;

                    case "4":
                        salir = true;
                        System.out.println("Saliendo del cliente...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            }

        } catch (RemoteException e) {
            System.err.println("[CLIENTE ERROR] Fallo de comunicación o interrupción del servidor remoto.");
            System.err.println("Detalle de la excepción: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[CLIENTE ERROR] Ocurrió un error inesperado:");
            e.printStackTrace();
        }
    }
}