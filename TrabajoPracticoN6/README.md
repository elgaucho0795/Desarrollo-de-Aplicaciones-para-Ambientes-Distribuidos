# Trabajo Práctico N° 6: Middleware y Objetos Remotos (Java RMI)

**Materia:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Docente:** Lic. Gabriel Artaza  
**Alumno:** Hector Guillermo Gil Caro 

---
1. Abstracción del Middleware (Sockets puros vs. Java RMI / gRPC)
Sockets Puros: Exigen al desarrollador gestionar manualmente la conexión de red, empaquetado/desempaquetado de bytes, diseño del protocolo de mensajes y resolución de direcciones IP/puertos a bajo nivel.

Java RMI / gRPC: Abstrae todo el nivel de transporte. El programador realiza la llamada como si fuera un método local de un objeto convencional.

Complejidades que abstrae el middleware:

Serialización y deserialización de objetos (Marshalling/Unmarshalling).

Gestión de conexiones de sockets, concurrencia de hilos y ciclo de vida de la red.

Transparencia de localización mediante el registro de nombres (RMI Registry / gRPC Name Resolver).

2. Ciclo de Vida y Stub / Skeleton
Stub (Proxy en el cliente): Objeto local en el lado del cliente que implementa la misma interfaz remota. Su función principal es interceptar la llamada al método local, realizar el marshalling (convertir los parámetros y la firma del método en un flujo de bytes) y enviarlo por la red al servidor.

Skeleton / Dispatcher (Servidor): Componente receptor en el lado del servidor que recibe el paquete de bytes, realiza el unmarshalling (reconstruye los objetos originales), invoca la implementación real de la lógica de negocio y finalmente empaca el valor de retorno para enviárselo de vuelta al Stub.

3. Manejo de Fallos Parciales
¿Qué ocurre si el servidor se interrumpe abruptamente? El cliente no recibe la respuesta esperada y la pila de red detecta el cierre inesperado del socket o timeout.

Excepciones específicas: En Java RMI se genera principalmente java.rmi.RemoteException (o sus subclases como ConnectException o UnmarshalException). En gRPC se elevan excepciones StatusRuntimeException (con códigos como UNAVAILABLE o DEADLINE_EXCEEDED).

Gestión en un entorno productivo:

Políticas de Reintento (Retries con Backoff Exponencial): Intentar nuevamente la llamada en intervalos crecientes si la operación es idempotente.

Patrón Circuit Breaker: Abrir el circuito ante fallos repetidos para evitar saturar el cliente y fallar rápidamente con un mensaje amigable.

Manejo explícito de Timeouts: Establecer tiempos máximos de espera en las peticiones remotas para no congelar la ejecución de la interfaz del cliente.
## 1. Instrucciones de Compilación y Ejecución

### Requisitos:
- Java JDK 8 o superior instalado.

### Pasos:
1. Abrir la terminal en la carpeta `TP6/`.
2. Compilar todos los archivos `.java`:
   ```bash
   javac *.java