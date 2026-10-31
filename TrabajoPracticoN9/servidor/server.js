const fs = require('fs');
const https = require('https');
const express = require('express');
const jwt = require('jsonwebtoken');

const app = express();
app.use(express.json());

// Clave secreta para firmar los JWT (en entorno real va en variables de entorno)
const JWT_SECRET = 'secreto_super_seguro_tp9';

// ==========================================
// 1. ENDPOINT DE AUTENTICACIÓN (/login)
// ==========================================
app.post('/api/login', (req, res) => {
    const { username, password } = req.body;

    // Validación básica de credenciales de prueba
    if (username === 'admin' && password === 'admin123') {
        const payload = {
            sub: username,
            role: 'ADMIN' // Rol con permisos avanzados
        };
        // Token expira en 1 hora
        const token = jwt.sign(payload, JWT_SECRET, { expiresIn: '1h' });
        return res.json({ message: 'Autenticación exitosa', token });
    }

    if (username === 'user' && password === 'user123') {
        const payload = {
            sub: username,
            role: 'USER' // Rol estándar
        };
        const token = jwt.sign(payload, JWT_SECRET, { expiresIn: '1h' });
        return res.json({ message: 'Autenticación exitosa', token });
    }

    res.status(401).json({ error: 'Credenciales inválidas' });
});

// ==========================================
// 2. MIDDLEWARE DE AUTORIZACIÓN Y JWT
// ==========================================
const verificarToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    
    if (!authHeader) {
        return res.status(401).json({ error: 'Token no proporcionado (Unauthorized)' });
    }

    const token = authHeader.split(' ')[1]; // Formato: "Bearer <token>"

    if (!token) {
        return res.status(401).json({ error: 'Formato de token inválido' });
    }

    jwt.verify(token, JWT_SECRET, (err, decoded) => {
        if (err) {
            // Distingue si expiró o si sufrió tampering (firma inválida)
            if (err.name === 'TokenExpiredError') {
                return res.status(401).json({ error: 'El token ha expirado' });
            }
            return res.status(401).json({ error: 'Token inválido o alterado (Tampering detectado)' });
        }

        req.user = decoded; // Guardamos los datos del usuario en la request
        next();
    });
};

// ==========================================
// 3. RUTAS PROTEGIDAS
// ==========================================
// Ruta abierta a cualquier usuario autenticado
app.get('/api/recurso-general', verificarToken, (req, res) => {
    res.json({
        message: 'Acceso autorizado al recurso general',
        usuario: req.user.sub,
        rol: req.user.role
    });
});

// Ruta protegida exclusiva para administradores (Control de Acceso / 403)
app.get('/api/recurso-admin', verificarToken, (req, res) => {
    if (req.user.role !== 'ADMIN') {
        return res.status(403).json({ error: 'Acceso prohibido: Se requiere rol de Administrador (Forbidden)' });
    }
    res.json({
        message: '¡Bienvenido al panel de administración exclusivo!',
        usuario: req.user.sub
    });
});

// ==========================================
// 4. LEVANTAMIENTO DEL SERVIDOR HTTPS
// ==========================================
const PORT = 3443; // Puerto estándar seguro para desarrollo HTTPS

try {
    const sslOptions = {
        key: fs.readFileSync('./certs/server.key'),
        cert: fs.readFileSync('./certs/server.crt')
    };

    https.createServer(sslOptions, app).listen(PORT, () => {
        console.log(`Servidor seguro HTTPS corriendo en https://localhost:${PORT}`);
    });
} catch (error) {
    console.error('Error al cargar los certificados SSL. Asegúrate de estar ejecutando desde la carpeta servidor/.', error.message);
}