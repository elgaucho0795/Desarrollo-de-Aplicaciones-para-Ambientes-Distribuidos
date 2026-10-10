import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioRemoto extends Remote {
    
    // 1. Procesamiento Matemático
    ResultadoEstadistico calcularEstadisticas(List<Double> numeros) throws RemoteException;

    // 2. Validación de Criterios (Validación de CUIT/DNI argentino)
    boolean validarCuit(String cuit) throws RemoteException;

    // 3. Filtro de Contenido
    List<String> filtrarCadenas(List<String> cadenas, String patron) throws RemoteException;
}