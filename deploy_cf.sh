#!/usr/bin/env bash

# to exit in case of error
set -e
# to see what's going on
set -v

version="0.0.1-SNAPSHOT"

echo "Deploying to Heroku"

# Build the release if not there
if [ ! -f "target/logopicker-${version}.war" ]; then
    ./mvnw clean package -Pprod  -DskipTests
fi

./mvnw clean verify -DskipTests=true -B -Pprod &&

# Deploy
cf push -f ./deploy/cloudfoundry/manifest.yml -t 120 -p target/*.war

echo "Deployment done"
