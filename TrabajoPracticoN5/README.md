# Trabajo Práctico N° 5: Concurrencia y Sincronización con el Modelo de Actores

**Materia:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Docente:** Lic. Gabriel Artaza  
**Alumno:** Hector Guillermo Gil Caro 

---

## 1. Descripción
Este proyecto implementa el **Modelo de Actores** para resolver problemas de concurrencia y sincronización mediante el paso de mensajes asincrónicos, evitando completamente el uso de memoria compartida, bloques `synchronized` y cerrojos (`ReentrantLock`).

---

## 2. Diagrama de Flujo e Interacción

```text
[ ActorSensor 1 ] --(Send: MensajeLectura)--> \
[ ActorSensor 2 ] --(Send: MensajeLectura)-->  \
[ ActorSensor 3 ] --(Send: MensajeLectura)----> [ Mailbox FIFO ] ---> [ ActorProcesador ]
[ ActorSensor 4 ] --(Send: MensajeLectura)-->  /                       (State Change)
[ ActorSensor 5 ] --(Send: MensajeLectura)--> /