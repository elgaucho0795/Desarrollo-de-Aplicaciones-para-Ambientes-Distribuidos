const express = require('express');
const { queryPrimary, queryReplica } = require('./db');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;

// Endpoint de Escritura (Dirigido estrictamente al Nodo Primario)
app.post('/api/registros', async (req, res) => {
  const { data } = req.body;
  if (!data) {
    return res.status(400).json({ error: 'El campo data es obligatorio.' });
  }

  try {
    const query = 'INSERT INTO registros (data) VALUES ($1) RETURNING *';
    const result = await queryPrimary(query, [data]);
    res.status(201).json({
      message: 'Escritura exitosa en el Nodo Primario (5432)',
      registro: result.rows[0],
    });
  } catch (error) {
    console.error('Error al escribir en el nodo primario:', error.message);
    res.status(500).json({
      error: 'Error interno o nodo primario no disponible (Falla de escritura / Failover requerido).',
      detalle: error.message,
    });
  }
});

// Endpoint de Lectura (Canalizado hacia el Nodo Réplica de solo lectura)
app.get('/api/registros', async (req, res) => {
  try {
    const query = 'SELECT * FROM registros ORDER BY created_at DESC';
    const result = await queryReplica(query);
    res.status(200).json({
      origen: 'Nodo Réplica de Solo Lectura (5433)',
      total: result.rowCount,
      registros: result.rows,
    });
  } catch (error) {
    console.error('Error detallado al leer desde el nodo réplica:', error.message);
    res.status(500).json({
      error: 'Error al consultar el nodo de réplica.',
      detalle: error.message,
    });
  }
});

app.listen(PORT, () => {
  console.log(`Backend de aplicación distribuida ejecutándose en el puerto ${PORT}`);
});