#!/bin/bash
set +e

docker-compose down

docker rmi kickster/coreapi-artifact
mvn clean package -Dmaven.test.skip=true

docker-compose up -d
docker-compose logs --follow artifactapi

set -e
