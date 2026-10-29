const { Pool } = require('pg');

// Pool exclusivo para operaciones de Escritura (Nodo Primario - Puerto 5432)
const primaryPool = new Pool({
  host: process.env.DB_PRIMARY_HOST || 'localhost',
  port: process.env.DB_PRIMARY_PORT || 5432,
  user: 'postgres',
  password: 'password123',
  database: 'distribuidos_db',
});

// Pool exclusivo para operaciones de Lectura (Nodo Réplica - Puerto 5433)
const replicaPool = new Pool({
  host: process.env.DB_REPLICA_HOST || 'localhost',
  port: process.env.DB_REPLICA_PORT || 5433,
  user: 'postgres',
  password: 'password123',
  database: 'distribuidos_db',
});

module.exports = {
  queryPrimary: (text, params) => primaryPool.query(text, params),
  queryReplica: (text, params) => replicaPool.query(text, params),
};