#!/bin/bash
set -e

# Asegurar acceso de replicación para la red de Docker en pg_hba.conf
echo "host replication postgres all md5" >> "$PGDATA/pg_hba.conf"
echo "host all postgres all md5" >> "$PGDATA/pg_hba.conf"

# Crear tabla inicial de registros
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE TABLE IF NOT EXISTS registros (
        id SERIAL PRIMARY KEY,
        data TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
EOSQL