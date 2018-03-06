# logopicker
This application was generated using JHipster 4.14.0, you can find documentation and help at [http://www.jhipster.tech/documentation-archive/v4.14.0](http://www.jhipster.tech/documentation-archive/v4.14.0).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Development

To start your application in the dev profile, simply run:

    ./mvnw


For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].



## Building for production

To optimize the logopicker application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war


Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test
### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling) and can be run with:

    ./mvnw gatling:execute

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw verify -Pprod dockerfile:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[JHipster Homepage and latest documentation]: http://www.jhipster.tech
[JHipster 4.14.0 archive]: http://www.jhipster.tech/documentation-archive/v4.14.0
[Doing microservices with JHipster]: http://www.jhipster.tech/documentation-archive/v4.14.0/microservices-architecture/
[Using JHipster in development]: http://www.jhipster.tech/documentation-archive/v4.14.0/development/
[Service Discovery and Configuration with the JHipster-Registry]: http://www.jhipster.tech/documentation-archive/v4.14.0/microservices-architecture/#jhipster-registry
[Using Docker and Docker-Compose]: http://www.jhipster.tech/documentation-archive/v4.14.0/docker-compose
[Using JHipster in production]: http://www.jhipster.tech/documentation-archive/v4.14.0/production/
[Running tests page]: http://www.jhipster.tech/documentation-archive/v4.14.0/running-tests/
[Setting up Continuous Integration]: http://www.jhipster.tech/documentation-archive/v4.14.0/setting-up-ci/

[Gatling]: http://gatling.io/

## Deployment

### Configure

* Set zone to whatever you want in `src/main/resources/config/application.yml`
* Set the same zone in `frontend/src/app/app.service.ts`

### Start frontend

```bash
ng server --open
```

We can use [pagereboot][http://pagereboot.com/pagereboot.php?url=http%3A%2F%2Flocalhost%3A4200&sec=5] to reboot the app.
And [pagereboot][

### Local

```bash
docker-compose -f src/main/docker/mysql.yml up -d
./mvnw -Pprod
```

### Heroku

```bash
./deploy_heroku.sh
```

### Heroku docker

```bash
./mvnw verify -Pprod dockerfile:build
heroku container:login
docker tag logopicker:latest registry.heroku.com/logopicker/web
docker push registry.heroku.com/logopicker/web
```

To switch between the two stacks:
```bash
heroku stack:set heroku-16
```

Scale down:

```bash
heroku ps:scale web=0
```

### Google Cloud Platform

### Amazon Beanstalk

### Microsoft Azure

https://docs.microsoft.com/en-us/azure/app-service/app-service-web-tutorial-java-mysql

Create the database

```bash
az group create --name logopicker --location "Canada East"
az mysql server create --name logopicker --resource-group logopicker --location "Canada East" --admin-user some_user --admin-password some_password
az mysql server firewall-rule create --name allIPs --server logopicker --resource-group logopicker --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
mysql -u some_password@logopicker -h logopicker.mysql.database.azure.com -P 3306 -p

```

```sql
CREATE DATABASE logopicker;
quit
```

Create the app

```bash
az appservice plan create --name logopicker --resource-group logopicker --sku FREE
az webapp create --name logopicker --resource-group logopicker --plan logopicker
az webapp config set --name logopicker --resource-group logopicker --java-version 1.8 --java-container Tomcat --java-container-version 9.0
az webapp config appsettings set --settings SPRING_DATASOURCE_URL="jdbc:mysql://logopicker.mysql.database.azure.com:3306/logopicker?verifyServerCertificate=true&useSSL=true&requireSSL=false" --resource-group logopicker --name logopicker
az webapp config appsettings set --settings SPRING_DATASOURCE_USERNAME=some_user@logopicker --resource-group logopicker --name logopicker
az webapp config appsettings set --settings SPRING_DATASOURCE_PASSWORD=some_password --resource-group logopicker --name logopicker
az webapp config appsettings set --settings SPRING_PROFILES_ACTIVE="prod,azure" --resource-group logopicker --name logopicker
```

Create a Docker registry

```bash
az acr create --admin-enabled --name logopicker --resource-group logopicker --sku Basic
export DOCKER_REGISTRY=$(az acr show --name logopicker --query loginServer --output tsv)
export DOCKER_USER=logopicker
export DOCKER_PASSWORD=$(az acr credential show --name logopicker --query passwords[0].value --output tsv)
 ```
 
Deploy to registry

```bash
docker tag logopicker $DOCKER_REGISTRY/logopicker
docker login $DOCKER_REGISTRY -u $DOCKER_USER -p $DOCKER_PASSWORD
docker push $DOCKER_REGISTRY/logopicker
az acr repository list --name logopicker # see it deployed
```

Deploy the app

```bash
az webapp config container set --name logopicker --resource-group logopicker \
    --docker-custom-image-name ${DOCKER_REGISTRY}/logopicker:latest \
    --docker-registry-server-url https://${DOCKER_REGISTRY} \
    --docker-registry-server-password ${DOCKER_PASSWORD} \
    --docker-registry-server-user ${DOCKER_USER}
az webapp config appsettings set --settings PORT=8080 --name logopicker --resource-group logopicker
az webapp restart --name logopicker --resource-group logopicker
az webapp show --name logopicker --resource-group logopicker --query hostNames[0] --out tsv
```



```bash
az webapp deployment list-publishing-profiles --name logopicker --resource-group logopicker --query "[?publishMethod=='FTP'].{URL:publishUrl, Username:userName,Password:userPWD}" --output json
ftp waws-prod-yq1-003.ftp.azurewebsites.windows.net <<EOF
logopicker\$logopicker
the_password
cd /site/wwwroot/webapps
mdelete -i ROOT/*
rmdir ROOT/
put target/logopicker-0.0.1-SNAPSHOT.war ROOT.war
quit
EOF
```

See logs

```bash
az webapp browse --name logopicker --resource-group logopicker
az webapp log tail --name logopicker --resource-group logopicker
````

### Microsoft Azure with Docker

```bash
az acr create --admin-enabled --name logopicker --resource-group logopicker --sku Basic
```

### Cloud Foundry

https://docs.spring.io/spring-boot/docs/current/reference/html/cloud-deployment.html
https://pivotal.io/fr/platform/pcf-tutorials/getting-started-with-pivotal-cloud-foundry/deploy-the-sample-app

http://www.jhipster.tech/cloudfoundry/

    brew tap cloudfoundry/tap
    brew install cf-cli 
    
    cf restage logopicker # redeploy app
    
In case you have more than 1 sql provider in your pom :
org.springframework.cloud.CloudException: No unique service matching interface javax.sql.DataSource found. Expected 1, found 2


### Troubleshooting

* [JHipster Registry](https://henri-jhipster-registry.herokuapp.com)

```bash
# could be admin:admin@ is authentication was there
curl -H 'Content-Type: application/json' -H "Accept: application/json" https://henri-jhipster-registry.herokuapp.com/api/eureka/applications
curl -H 'Content-Type: application/json' -H "Accept: application/json" https://henri-jhipster-registry.herokuapp.com/eureka/apps/LOGOPICKER
curl http://localhost:8080/api/logos/current
```

Database cleanup:

```bash
docker exec -ti docker_logopicker-mysql_1 mysql
```

```sql
use logopicker
drop table logo;
drop table jhi_persistent_audit_evt_data;
drop table jhi_persistent_audit_event;
drop table databasechangelog;
drop table databasechangeloglock;
show tables;
```

Env: [http://localhost:8080/management/env](http://localhost:8080/management/env)
