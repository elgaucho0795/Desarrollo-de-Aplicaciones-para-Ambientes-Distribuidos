# Trabajo Práctico N° 3: Sockets TCP/UDP con API java.net y Servidores Multihilo
**Asignatura:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Alumno:** Héctor Guillermo Gil Caro  
**Docente:** Lic. Gabriel Artaza  

---

## 📝 Cuestionario Teórico

### 1. Capa de Transporte: Explique las diferencias estructurales y operativas entre una comunicación orientada a conexión (TCP) y una sin conexión (UDP) en el contexto del paso de mensajes.

* **TCP (Transmission Control Protocol):**
  * **Estructuralmente:** Requiere el establecimiento previo de un canal lógico mediante el acuerdo de tres vías (*three-way handshake*) antes de intercambiar datos. Utiliza el concepto de flujo continuo de bytes (*Byte Stream*).
  * **Operativamente:** Es un protocolo confiable que garantiza la entrega de paquetes en orden, sin duplicados ni pérdidas (mediante retransmisión de paquetes perdidos y control de flujo y congestión). Presenta una mayor sobrecarga (*overhead*) de red por el control de cabeceras y sincronización.

* **UDP (User Datagram Protocol):**
  * **Estructuralmente:** No establece ni mantiene una conexión previa (sin conexión). Los datos se empaquetan en datagramas independientes (*DatagramSocket* / *DatagramPacket*).
  * **Operativamente:** No garantiza la llegada de los datos, el orden de entrega ni la retransmisión en caso de pérdida. Al eliminar la sobrecarga de control de estado y handshake, logra una latencia considerablemente menor, siendo idóneo para transmisiones en tiempo real o alertas de telemetría.

---

### 2. API java.net: ¿Cuál es la función del método `ServerSocket.accept()`? Justifique por qué es indispensable usar un hilo dedicado por cada cliente aceptado en un servidor TCP.

* **Función de `ServerSocket.accept()`:**  
  Es un método bloqueante que escucha y aguarda peticiones de conexión entrantes de clientes a través del puerto configurado. Cuando llega una solicitud, completa el saludo de tres vías y retorna una instancia individual de la clase `Socket` para comunicarse con ese cliente específico.

* **Justificación del hilo dedicado (Multihilo):**  
  El método `accept()` y las operaciones de lectura/escritura (`readLine()`, `read()`) son bloqueantes por naturaleza. Si el servidor procesara la lógica de transmisión en el hilo principal (*main thread*):
  1. No podría atender a nuevos clientes mientras procesa a uno existente.
  2. Quedaría bloqueado esperando mensajes de un cliente pasivo, deteniendo la atención general del sistema.  
  
  Delegar cada cliente a un hilo independiente (`Thread` / `Runnable`) permite atender múltiples solicitudes en paralelo, logrando un servidor verdaderamente concurrente e independiente.

---

### 3. Manejo de Errores y Resiliencia: ¿Qué sucede si el paquete UDP enviado por el emisor se pierde en la red? ¿Cómo lo detecta el receptor con `setSoTimeout()`?

* **Pérdida de un paquete UDP:**  
  Dado que UDP no posee mecanismos de confirmación de recepción (*ACK*) ni retransmisiones automáticas, si un datagrama se pierde en la red, **el protocolo la ignora y la información se pierde definitivamente**. El emisor no recibe ninguna notificación de error.

* **Detección con `setSoTimeout()`:**  
  Por defecto, la llamada `socket.receive(paquete)` en Java se bloquea indefinidamente hasta recibir datos. Al invocar `socket.setSoTimeout(5000)`, se establece un tiempo máximo de espera de 5 segundos.  
  Si durante ese periodo no llega ningún paquete, la API de Java lanza la excepción controlada `java.net.SocketTimeoutException`. Esto permite que la aplicación receptora detecte la inactividad o pérdida de señal, registre un evento o advertencia en consola y reanude el bucle de lectura sin colgar el hilo del sistema.

---

## 🚀 Instrucciones de Compilación y Ejecución

### Requisitos
* JDK 11 o superior instalado.

### Compilación (Consola / Terminal)
Desde la raíz del proyecto:
```bash
javac -d bin src/ej1_chat_tcp/*.java src/ej2_telemetria_udp/*.java