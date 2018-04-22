#!/bin/bash
docker run --name jdbc-sqlserver \
 -e 'ACCEPT_EULA=Y' \
 -e 'SA_PASSWORD=Cent-Quick-Space-Bath-8' \
 -p 1433:1433 \
 -d microsoft/mssql-server-linux:latest
