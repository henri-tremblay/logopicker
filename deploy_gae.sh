#!/usr/bin/env bash

# to exit in case of error
set -e
# to see what's going on
set -v

version="0.0.1-SNAPSHOT"

echo "Deploying to GAE"

# Build the release if not there
if [ ! -f "target/logopicker-${version}.war" ]; then
    ./mvnw clean -Pprod -DskipTests
fi

./mvnw -Pprod appengine:deploy

echo "Deployment done"
