# Trabajo Práctico N° 2: Resiliencia en Ambientes Distribuidos
**Alumno:** Héctor Guillermo Gil Caro

## Análisis Teórico (Ejercicio 3)

### 1. Problema del "Thundering Herd" (Efecto Estampida)
Si un servidor se cae o se satura y rechaza temporalmente a sus clientes, y todos esos clientes están programados para reintentar la conexión exactamente al mismo tiempo y con intervalos fijos (ej: todos esperan exactamente 2 segundos), se generará una "estampida". En el momento en que el servidor intente levantarse, recibirá una avalancha de peticiones sincronizadas de todos los clientes al mismo tiempo, lo que volverá a saturar sus recursos (CPU/Memoria/Red) y lo volverá a tumbar. 
**Solución:** Al agregar el componente aleatorio (**Jitter**) a la fórmula, "desincronizamos" a los clientes. Algunos reintentarán a los 2.1 seg, otros a los 2.4 seg, distribuyendo la carga a lo largo del tiempo y dándole un respiro al servidor para que procese las peticiones de a poco.

### 2. Fallo Transitorio vs. Fallo Permanente
* **Fallo Transitorio:** Es un error temporal que se soluciona por sí solo al cabo de un breve tiempo. En estos casos, tiene sentido utilizar estrategias de reintento (como el Backoff Exponencial). 
  * *Ejemplo:* Una pérdida de paquetes de red momentánea por inestabilidad del Wi-Fi o un pico temporal de uso de CPU en el servidor que lo hizo demorar en responder.
* **Fallo Permanente:** Es un error que no se va a solucionar por más que el cliente espere y reintente mil veces, ya que requiere intervención humana o corrección del código.
  * *Ejemplo:* Intentar conectarse a una base de datos con una contraseña incorrecta (Error de Autenticación) o apuntar a una dirección IP de un servidor que fue dado de baja definitivamente.
 
  * Captura de pantalla
  * <img width="1425" height="751" alt="image" src="https://github.com/user-attachments/assets/28923b7e-5e85-4b79-81d0-08f352d951a4" />
<img width="697" height="292" alt="image" src="https://github.com/user-attachments/assets/96acbfb7-eaeb-4ea6-b3bc-234ae77633bd" />
