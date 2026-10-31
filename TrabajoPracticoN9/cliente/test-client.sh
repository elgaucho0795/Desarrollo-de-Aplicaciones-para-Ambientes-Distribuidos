#!/bin/bash

echo "=== 1. PRUEBA DE LOGIN (Emisión de JWT) ==="
# Nota: Guarda la respuesta o copia el token que aparece aquí
curl -k -X POST https://localhost:3443/api/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'

echo -e "\n\n=== 2. PRUEBA DE ACCESO PROTEGIDO (Con Token Válido) ==="
echo "Reemplaza <TU_TOKEN> por el token generado arriba:"
# curl -k -H "Authorization: Bearer <TU_TOKEN>" https://localhost:3443/api/recurso-general

echo -e "\n\n=== 3. PRUEBA DE ACCESO RECHAZADO (Sin Token - 401) ==="
curl -k -i https://localhost:3443/api/recurso-general

echo -e "\n\n=== 4. PRUEBA DE TAMPERING (Token Alterado) ==="
# curl -k -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.FALSOPAYLOAD.firma_invalida" https://localhost:3443/api/recurso-general