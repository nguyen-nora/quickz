#!/bin/sh
# Runs docker container on host network, port 5432
docker run -d --rm --name java-postgres -e POSTGRES_PASSWORD=javapostgres --net host java-postgres
