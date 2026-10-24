Informe de Trabajo Práctico - Sistema de Gestión de Kiosco

1. Carátula e Identificación

Nombre y Apellido del Estudiante: Héctor Guillermo Gil Caro 

Materia / Cátedra: Desarrollo de Aplicaciones para Ambientes

Lenguajes y Frameworks utilizados:

Backend: Java 17, Spring Boot 4.x (Spring WebMVC, Spring Data JPA, Spring Security)

Base de Datos: H2 Database (En memoria / Persistencia local)

Frontend: HTML5, CSS3 (Tailwind CSS CDN), JavaScript (Vanilla ES6 / Fetch API)

2. Diagrama o Esquema de Arquitectura

El siguiente diagrama en sintaxis Mermaid ilustra la interacción entre la capa cliente (interfaz web en el navegador), el canal de comunicación HTTP/1.1 y la API REST en el servidor de servicios Spring Boot.

sequenceDiagram
    autonumber
    actor Usuario
    participant Cliente as Cliente Web (HTML/JS + Tailwind)
    participant Canal as Canal HTTP/1.1 (CORS enabled)
    participant Controller as ProductoController (Spring REST)
    participant Service as ProductoService
    participant BD as Base de Datos (H2 Database)

    Usuario->>Cliente: Interacción (Crear, Editar, Eliminar, Buscar)
    
    alt Obtener Lista o Buscar Producto
        Cliente->>Canal: GET /productos o GET /productos/{id}
        Canal->>Controller: Solicita datos
        Controller->>Service: Consulta lógica de negocio
        Service->>BD: SELECT query
        BD-->>Service: Retorna datos
        Service-->>Controller: Retorna Entidad / Lista
        Controller-->>Canal: HTTP 200 OK (JSON)
        Canal-->>Cliente: Renderiza productos en la interfaz
    else Guardar / Modificar Producto
        Cliente->>Canal: POST /productos (JSON Payload)
        Canal->>Controller: Procesa solicitud
        Controller->>Service: guardarProducto(producto)
        Service->>BD: INSERT / UPDATE query
        BD-->>Service: Confirma transacción
        Service-->>Controller: Objeto guardado
        Controller-->>Canal: HTTP 200 OK (JSON)
        Canal-->>Cliente: Notificación Toast de éxito
    else Eliminar Producto
        Cliente->>Canal: DELETE /productos/{id}
        Canal->>Controller: Procesa eliminación
        Controller->>Service: eliminarProducto(id)
        Service->>BD: DELETE query
        BD-->>Service: Confirmación

3. Evidencias de Ejecución (Capturas de Pantalla)
   Captura 1: Invocación Exitosa
   <img width="1902" height="962" alt="image" src="https://github.com/user-attachments/assets/b36146b5-d604-4b90-a2d4-f8fdee885688" />


Petición enviada por el cliente y respuesta correcta devuelta por la API REST al listar o guardar un producto.
<img width="1907" height="998" alt="image" src="https://github.com/user-attachments/assets/1207653f-3e3f-4593-ae7d-3d8873f856fc" />
<img width="1917" height="998" alt="image" src="https://github.com/user-attachments/assets/a6332ce1-f422-4b95-aa3f-77968a0e94ac" />


Captura 2: Estructura de Datos

Captura detallada de la carga útil recibida (JSON) inspeccionando la pestaña Network (Red) o Console en las Herramientas de Desarrollador del navegador.
<img width="1911" height="1002" alt="image" src="https://github.com/user-attachments/assets/afeea463-3b0e-4038-9e49-299f17d9eea8" />

Captura 3: Manejo de Errores

Comprobación del comportamiento del sistema frente a una petición errónea (por ejemplo, buscar un ID de producto inexistente como /productos/999 o simular falla en la comunicación).
<img width="1917" height="980" alt="image" src="https://github.com/user-attachments/assets/2365cfe9-c44a-4552-9086-48520f17b521" />

4. Instrucciones de Ejecución

Prerrequisitos

Java Development Kit (JDK): Versión 17 o superior.

Apache Maven: Opcional (se incluye el ejecutable mvnw en el proyecto).

Navegador Web: Chrome, Firefox, Edge o Safari.

Pasos para compilar y levantar el Backend (Servidor)

Abrir una terminal en la raíz del proyecto backend.

Compilar la aplicación y descargar dependencias:

./mvnw clean compile


(En Windows CMD usar mvnw.cmd clean compile)

Levantar la aplicación de Spring Boot:

./mvnw spring-boot:run


El servidor estará escuchando en http://localhost:8080.

(Opcional) Se puede acceder a la consola de la base de datos H2 mediante http://localhost:8080/h2-console con las siguientes credenciales configuradas en application.properties:

JDBC URL: jdbc:h2:~/Kiosco-Proyecto

User: hector

Password: hector

Pasos para ejecutar el Cliente (Frontend)

Descomprimir el archivo del frontend o ubicar el archivo index.html.

Abrir el archivo index.html directamente en cualquier navegador web moderno (no requiere un servidor de Node/Nginx obligatorio, aunque puede utilizarse cualquier servidor estático como Live Server en VS Code).

Asegurarse de que el backend esté ejecutándose en http://localhost:8080 para permitir la comunicación fluida con la API.
        Controller-->>Canal: HTTP 200 OK ("Producto eliminado con exito")
        Canal-->>Cliente: Actualiza vista
    end
