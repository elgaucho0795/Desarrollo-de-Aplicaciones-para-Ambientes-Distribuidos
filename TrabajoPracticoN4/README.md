# Trabajo Práctico N° 4: Representación de Datos y Paso de Mensajes

**Materia:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Docente:** Lic. Gabriel Artaza  
**Alumno:** Hector  

---

## 1. Descripción
Este proyecto evalúa el desempeño y el overhead en la transmisión de datos a través de una red local TCP mediante dos técnicas de serialización:
- **Texto (JSON):** Serialización estructurada legible por humanos.
- **Binario Directo:** Empaquetado explícito de primitivos mediante `DataOutputStream`.

---

## 2. Reporte y Tabla Comparativa de Resultados

Se realizaron pruebas transmitiendo una ráfaga de **1000 transacciones** consecutivas.

| Formato | Tamaño Carga Útil (Bytes) | Tiempo de Envío / Procesamiento (ms) |
| :--- | :---: | :---: |
| **JSON** | [72893] bytes | [223,70] ms |
| **Binario Directo** | [27000] bytes | [46,36] ms |

### Conclusión y Análisis del Overhead
1. **Tamaño de Carga Útil:** El formato **Binario** reduce significativamente la cantidad de bytes transmitidos por la red en comparación con JSON, eliminando nombres de claves, comillas y delimitadores de texto.
2. **Tiempo de Ejecución:** El tiempo de serialización y transmisión en formato Binario es sensiblemente menor, reduciendo la latencia general de la aplicación.

---

## 3. Evidencias de Funcionamiento

A continuación se adjuntan las capturas de pantalla de la ejecución del Servidor y Cliente TCP:
