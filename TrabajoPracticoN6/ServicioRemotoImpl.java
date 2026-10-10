import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioRemotoImpl extends UnicastRemoteObject implements ServicioRemoto {

    public ServicioRemotoImpl() throws RemoteException {
        super();
    }

    @Override
    public ResultadoEstadistico calcularEstadisticas(List<Double> numeros) throws RemoteException {
        System.out.println("[SERVIDOR] Ejecutando: calcularEstadisticas con " + numeros.size() + " elementos.");
        if (numeros == null || numeros.isEmpty()) {
            return new ResultadoEstadistico(0, 0, 0, 0);
        }

        double suma = 0;
        double max = Collections.max(numeros);
        double min = Collections.min(numeros);

        for (double num : numeros) {
            suma += num;
        }
        double promedio = suma / numeros.size();

        double sumaVarianzas = 0;
        for (double num : numeros) {
            sumaVarianzas += Math.pow(num - promedio, 2);
        }
        double desviacion = Math.sqrt(sumaVarianzas / numeros.size());

        return new ResultadoEstadistico(promedio, max, min, desviacion);
    }

    @Override
    public boolean validarCuit(String cuit) throws RemoteException {
        System.out.println("[SERVIDOR] Ejecutando: validarCuit para -> " + cuit);
        if (cuit == null) return false;
        
        // Formato esperado: 11 dígitos numéricos (ej. 20123456789 o 20-12345678-9)
        String limpio = cuit.replaceAll("[^0-9]", "");
        if (limpio.length() != 11) return false;

        int[] multiplicadores = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;
        for (int i = 0; i < 10; i++) {
            suma += Character.getNumericValue(limpio.charAt(i)) * multiplicadores[i];
        }

        int resto = suma % 11;
        int verificadorCalculado = 11 - resto;
        if (verificadorCalculado == 11) verificadorCalculado = 0;
        if (verificadorCalculado == 10) verificadorCalculado = 9;

        int verificadorIngresado = Character.getNumericValue(limpio.charAt(10));
        return verificadorCalculado == verificadorIngresado;
    }

    @Override
    public List<String> filtrarCadenas(List<String> cadenas, String patron) throws RemoteException {
        System.out.println("[SERVIDOR] Ejecutando: filtrarCadenas con patrón -> '" + patron + "'");
        List<String> resultado = new ArrayList<>();
        if (cadenas == null || patron == null) return resultado;

        for (String cadena : cadenas) {
            if (cadena.toLowerCase().contains(patron.toLowerCase())) {
                resultado.add(cadena);
            }
        }
        return resultado;
    }
}