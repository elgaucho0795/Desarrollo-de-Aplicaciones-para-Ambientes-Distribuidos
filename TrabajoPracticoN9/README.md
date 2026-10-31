# Trabajo Práctico Nº 9: Seguridad en Aplicaciones Distribuidas (JWT y TLS/SSL)

## 1. Carátula e Identificación
* **Estudiante:** Héctor Guillermo Gil Caro
* **Asignatura:** Desarrollo de Aplicaciones para Ambientes Distribuidos
* **Stack Tecnológico:** 
  * **Backend:** Node.js, Express
  * **Seguridad y Cifrado:** OpenSSL (Certificados SSL/TLS autofirmados), Módulo nativo `https`
  * **Autenticación:** JSON Web Tokens (`jsonwebtoken`)
  * **Herramientas de Pruebas:** `curl` (en entorno Git Bash)

---

## 2. Instrucciones de Ejecución

Sigue estos pasos para levantar el servidor y ejecutar las pruebas de seguridad:

1. **Generar los certificados SSL locales (dentro de `servidor/certs/`):**
   ```bash
   openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout server.key -out server.crt -subj "//C=AR\ST=Salta\L=Salta\O=Desarrollo\CN=localhost"
   ```

2. **Instalar las dependencias del servidor:**
   ```bash
   cd servidor
   npm install
   ```

3. **Iniciar el servidor HTTPS:**
   ```bash
   npm start
   ```
   El servidor se iniciará en `https://localhost:3443`.

   ![Inicio del servidor HTTPS](<img/Iniciar-el-servidor-HTTPS.png>)

4. **Ejecutar las pruebas en otra terminal:**
   Usa los comandos `curl` detallados en las evidencias de abajo para validar la autenticación, el acceso por HTTPS y el rechazo por manipulación (tampering).

## 3. Capturas de Pantalla (Evidencias de Software Funcionando)

Captura 1: Emisión de JWT
Descripción: Petición POST exitosa al endpoint /login enviando las credenciales de administrador, obteniendo como respuesta el token JWT firmado digitalmente con sus claims correspondientes (sub, role, exp).   
Comando de prueba:
curl -k -X POST https://localhost:3443/api/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
![Captura 1: Emisión de JWT](<img/Captura 1 Emisión de JWT.png>)

Captura 2: Acceso Autorizado por HTTPS
Descripción: Respuesta exitosa (HTTP 200 OK) al consultar un recurso protegido enviando un token JWT válido a través del encabezado Authorization: Bearer sobre un canal seguro HTTPS.  
Comando de prueba:
curl -k -H "Authorization: Bearer <TU_TOKEN>" https://localhost:3443/api/recurso-general
![Captura 2: Acceso Autorizado por HTTPS](<img/Captura 2 Acceso Autorizado por HTTPS.png>)

Captura 3: Rechazo por Falta de Token
Descripción: Respuesta de error HTTP 401 Unauthorized generada por el middleware de seguridad al intentar acceder al recurso protegido sin proporcionar credenciales ni token.   
Comando de prueba:
curl -k -i https://localhost:3443/api/recurso-general
![Captura 3: Rechazo por Falta de Token](<img/Captura 3 Rechazo por Falta de Token.png>)

Captura 4: Detección de Tampering (Alteración de Payload)
Descripción: Rechazo de la solicitud de acceso (401 Unauthorized) al detectar que el token enviado fue alterado manualmente en su estructura o firma, evidenciando la protección contra ataques de manipulación de payloads.   
Comando de prueba:
curl -k -H "Authorization: Bearer <TOKEN_ALTERADO>" https://localhost:3443/api/recurso-general
![Captura 4: Detección de Tampering](<img/Captura 4 Detección de Tampering (Alteración de Payload).png>)
