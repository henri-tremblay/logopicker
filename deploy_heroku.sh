#!/usr/bin/env bash

# to exit in case of error
set -e
# to see what's going on
set -v

version="0.0.1-SNAPSHOT"

echo "Deploying to Heroku"

# Build the release if not there
if [ ! -f "target/hardbacon-${version}.war" ]; then
    ./mvnw clean package -Pprod  -DskipTests
fi

# Deploy
heroku deploy:jar --jar target/hardbacon-${version}.war

echo "Deployment done"
