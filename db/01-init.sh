#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
  CREATE DATABASE $APP_DB_NAME;
  GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;
  \connect $APP_DB_NAME $APP_DB_USER
  BEGIN;
    CREATE TABLE IF NOT EXISTS projects (
      project_id INT GENERATED ALWAYS AS IDENTITY,
	    name text NOT NULL,
      PRIMARY KEY(project_id)
	  );

	  CREATE TABLE IF NOT EXISTS items (
	    item_id INT GENERATED ALWAYS AS IDENTITY,
	    project_id INT,
	    name text NOT NULL,
	    PRIMARY KEY(item_id),
	    CONSTRAINT fk_customer
	      FOREIGN KEY(project_id)
	        REFERENCES projects(project_id)
	  );
  COMMIT;
EOSQL