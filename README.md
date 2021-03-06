# logopicker

This is a little application showing how to deploy on different clouds.
It requires a MySQL database and nothing else.

When launching the homepage, you should see the logo of the cloud where it was deployed.

This application was generated using JHipster 5.1.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.1.0](https://www.jhipster.tech/documentation-archive/v5.1.0).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Yarn][]: We use Yarn to manage Node dependencies.
   Depending on your system, you can install Yarn either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    yarn install

We use yarn scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    yarn start

[Yarn][] is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `yarn update` and `yarn install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `yarn help update`.

The `yarn run` command will list all of the scripts available to run for this project.

### Service workers

Service workers are commented by default, to enable them please uncomment the following code.

* The service worker registering script in index.html

```html
<script>
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker
        .register('./service-worker.js')
        .then(function() { console.log('Service Worker Registered'); });
    }
</script>
```

Note: workbox creates the respective service worker and dynamically generate the `service-worker.js`

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    yarn add --exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    yarn add --dev --exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Note: there are still few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the logopicker application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    yarn test

For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw verify -Pprod dockerfile:build dockerfile:tag@version dockerfile:tag@commit

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

## Deployment

We assume that you already have an account on the different clouds. 
So the setup starts when you need to install a local client.
We have used a commmand line client as much as possible to perform the deployment.

### Local

#### Old school

1. Launch the mysql DB with docker: `docker-compose -f src/main/docker/mysql.yml up -d`
2. Launch the app: `./mvnw -Pprod`

#### Docker

1. Create the docker image: `./mvnw verify -Pprod dockerfile:build -DskipTests`
2. Launch the docker environment: `docker-compose -f src/main/docker/app.yml up -d`

#### Kubernetes

#### Installation

You need Kubernetes in local. The easiest is Minicube or Docker Edge which has built-in Kubernetes.

You can deploy your apps by running:

1. Create the docker image: `./mvnw verify -Pprod dockerfile:build -DskipTests`
2. Deploy on kubernetes: `kubectl apply -f src/main/kubernetes`

#### Useful commands

* Use these commands to find your application IP address: `kubectl get svc logopicker`
* See deployed pods: `kubectl get pods`
* See deployed services `kubectl get pods`

### Heroku

#### For all

##### Installation

1. Install the heroku command line client: `brew install heroku`
2. Create the app on heroku: `heroku create logopicker` (and provision your favorite db)
3. Configure the datasource for Heroku (`src/main/resources/config/application-heroku.yml`)
4. Set meaningful JVM options: `heroky config:set JAVA_OPTS=-Xmx256m`

#### Old school

##### Installation

1. Install the deploy plugin: `heroku plugins:install heroku-cli-deploy`
2. Add a Procfile (`Procfile`)

##### Deployment

1. Build for production `./mvnw clean verify -Pprod -DskipTests`
2. Deploy to heroku: `heroku deploy:jar --jar target/*.war`

#### Docker

##### Installation

1. Set Heroku environment variables: `heroku config:set JDBC_DATABASE_USERNAME=user JDBC_DATABASE_PASSWORD=password JDBC_DATABASE_URL=jdbc:mysql://blablabla.rds.amazonaws.com:3306/xxx?useSSL=false SPRING_PROFILES_ACTIVE: prod,heroku`
2. The dockerfile needs to be heroku compliant (CMD mandatory)

##### Deployment

1. Construct the docker image: `./mvnw verify -Pprod dockerfile:build`
2. Tag it for the heroku registry: `docker tag logopicker:latest registry.heroku.com/logopicker/web`
3. Push it there: `docker push registry.heroku.com/logopicker/web`
4. Release it to production: `heroku container:release web`

#### Kubernetes

N/A

#### Useful commands

* See the environment variables: `heroku config`
* See the logs: `heroku logs`
* To connect to the server: `heroku ps:exec`
* To install java debugging tools: `heroku plugins:install heroku-cli-java`
* To scale to more dynos: `heroku ps:scale web=2`
* To open your app in a browser: `heroku open`

### Oracle

#### For all

TDB

#### Old school

TDB

#### Docker

TDB

#### Kubernetes

N/A

### Azure

#### For all

##### Installation

1. Install the azure client: `brew install azure-cli`
2. Login: `az login`

#### Old school

TDB

#### Docker

TDB

#### Kubernetes

TDB

### AWS BeanStalk

#### For all

##### Installation

We assume here that you already have an AWS account.

1. Install the BeanStalk client: `brew install aws-elasticbeanstalk`

#### Old school

##### Installation

1. Configure the deployment (`.elasticbeanstalk/config.yml`): `eb init`
2. Create an instance (without load balancing): `eb create -s`
3. Add options including database options (`.ebextensions/options.config`)
4. Configure the database URL (`src/main/resources/config/application-aws.yml`)
5. Add a `Procfile` (`.elasticbeanstalk/Procfile`)
6. Use the Maven assembly plugin to create a nice zip that will be sent to your server (`src/main/assembly/zip.xml`)
 
 ##### Deployment
 
 1. `eb deploy`
 
 Note: The theory is that the database should be created using the configuration. It never worked for me.
 
#### Useful commands

* Open the BeanStalk console in a browser: `eb console`
* Terminate the environment: `eb terminate environment logopicker-dev`
* Run a docker deployment locally: `eb local run`
* Connect to the server: `eb ssh`
* See the logs: `eb logs`

#### Docker

TDB

#### Kubernetes

TDB

### CloudFoundry

#### For all

##### Installation

1. Install the CloudFoundry command line client: `brew tap cloudfoundry/tap; brew install cf-cli` 
2. Login to pivotal: `cf login -a https://api.run.pivotal.io`
3. Create the DB: `cf create-service cleardb spark logopicker-db`
4. Add link to the eureka server: `src/main/resources/config/application-cloudfoundry.yml`
5. Add maven dependencies for cloud connection

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-localconfig-connector</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-cloudfoundry-connector</artifactId>
        </dependency>
```

#### Old school

##### Installation

1. Create a `manifest.yml`

##### Deployment

1. Build for production `./mvnw clean verify -Pprod -DskipTests`
2. Deploy: `cf push -p target/*.war`

#### Docker

##### Installation

1. Enable docker: `cf enable-feature-flag diego_docker`

##### Deployment

1. Build for production `./mvnw clean verify -Pprod -DskipTests`
2. Deploy: `cf push --docker-image -p target/*.war`

#### Kubernetes

TDB

#### Useful commands

* Recent logs: `cf logs logopicker --recent`
* Tail logs: `cf logs logopicker`
* See available plan for a marketplace product: `cf marketplace -s cleardb`
* Know the existing users: `cf org-users tremblay.pro`
* Know the users per spaces: `cf space-users tremblay.pro development`

### Google Cloud Platform

#### For all

##### Installation

TDB

#### Old school

TDB

#### Docker

TDB

#### Kubernetes

TDB

#### Useful commands

TDB

### Troubleshooting

#### Query the server

```bash
SERVER="https://localhost:8080"
curl -v -sb -H "Content-Type: application/json" -H "${token%?}" $SERVER//api/logos/current
```

#### Database cleanup

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

#### See environment

[http://localhost:8080/management/env](http://localhost:8080/management/env)

## Useful links

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 5.1.0 archive]: https://www.jhipster.tech/documentation-archive/v5.1.0

[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v5.1.0/development/
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v5.1.0/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v5.1.0/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v5.1.0/running-tests/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v5.1.0/setting-up-ci/

[Node.js]: https://nodejs.org/
[Yarn]: https://yarnpkg.org/
[Webpack]: https://webpack.github.io/
[Angular CLI]: https://cli.angular.io/
[BrowserSync]: http://www.browsersync.io/
[Jest]: https://facebook.github.io/jest/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Leaflet]: http://leafletjs.com/
[DefinitelyTyped]: http://definitelytyped.org/

# Old Deployment that should be deleted or merged above

## Deployment

### Google Cloud Platform

Google app engine from spring boot :

    gcloud config set project my-project-id
    gcloud components install app-engine-java

2 approches : 

#### War classique
https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/appengine-standard-java8/springboot-appengine-standard
* necessite des adaptations par rapport a l app de base spring boot
  * packaging war
  * ajouter app engine plugin
  * commenter et exclure des dependances / plugins
  * src/main/webapp/WEB-INF/appengine-web.xml
* probleme avec des services loaders, avec Ehcache et JSR 107 Caching Provider

#### Jar executable qui lancera l application
https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/helloworld-springboot
* necessite des adaptations par rapport a l app de base spring boot
  * ajouter app engine plugin
  * src/main/appengine/app.yaml
* important de bien regler les variables d environnement et la taille du container app engine
* port 8080 !!! ou sinon 502 Bad Gateway du Nninx de GAE
Documentation pour app.yaml
https://cloud.google.com/appengine/docs/flexible/java/configuring-your-app-with-app-yaml#resource-settings

#### Connect to Google Cloud Sql
https://cloud.google.com/appengine/docs/standard/java/cloud-sql/using-cloud-sql-mysq 

https://cloud.google.com/community/tutorials/run-spring-petclinic-on-app-engine-cloudsql


Il suffit d'utiliser en production une url un peu special :
spring.datasource.url=jdbc:mysql://google/petclinic?cloudSqlInstance=INSTANCE_CONNECTION_NAME&socketFactory=com.google.cloud.sql.mysql.SocketFactory

Attention à bien activer aussi l'accès au SQL Api :

    [INFO] GCLOUD: Caused by: java.lang.RuntimeException: The Google Cloud SQL API is not enabled for project [crafty-run-196704]. Please use the Google Developers Console to enable it: https://console.cloud.google.com/apis/api/sqladmin/overview?project=PROJECT_ID

Ne pas oublier de rajouter dans les dependances : 

    <dependency>
        <groupId>com.google.cloud.sql</groupId>
        <!-- If using MySQL 6.x driver, use mysql-socket-factory-connector-j-6 instead -->
        <artifactId>mysql-socket-factory</artifactId>
        <version>1.0.5</version>
    </dependency>

### Microsoft Azure

https://docs.microsoft.com/en-us/azure/app-service/app-service-web-tutorial-java-mysql
https://docs.microsoft.com/en-us/java/azure/spring-framework/deploy-containerized-spring-boot-java-app-with-maven-plugin

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
mvn azure-webapp:deploy
```

### Microsoft Azure executable Jar

https://blogs.msdn.microsoft.com/azureossds/2015/12/28/running-java-jar-file-to-serve-web-requests-on-azure-app-service-web-apps/

### AWS Beanstalk

Configure

```bash
eb init
```

Run local

```bash
eb local run
```

Deploy

```bash
eb create -s
```

Terminate

```bash
eb terminate --all
```
