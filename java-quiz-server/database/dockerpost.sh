#!/bin/sh
docker ps
read -p "id: " id
docker exec -it $id psql -U postgres -a -f init.sql
