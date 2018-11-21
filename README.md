
# `studies` — Studies

This project is an application skeleton of typical [Spring Boot][sboot] backend application. 

## Getting Started

To get you started you can simply clone the `studies` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `studies` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `studies`

Clone the `studies` repository using git:

```bash
git clone https://github.com/systelab/studies.git
cd studies
```

### Install Dependencies

In order to install the dependencies and generate the Uber jar you must run:

```bash
mvn clean install
```

### Run

To launch the server, simply run with java -jar the generated jar file.

```bash
cd target
java -jar studies-1.0.jar
```

or run:

```bash
mvn spring-boot:run
```

## API

You will find the swagger UI at http://localhost:8080/swagger-ui.html

First generate a token by login as user Systelab and password Systelab. After that authorize Swagger by copying the bearer.

## Docker

### Build docker image

There is an Automated Build Task in Docker Cloud in order to build the Docker Image. 
This task, triggers a new build with every git push to your source code repository to create a 'latest' image.
There is another build rule to trigger a new tag and create a 'version-x.y.z' image

You can always manually create the image with the following command:

```bash
docker build -t systelab/studies . 
```

### Run the container

```bash
docker run -p 8080:8080 systelab/studies
```

The app will be available at http://localhost:8080/swagger-ui.html




[git]: https://git-scm.com/
[sboot]: https://projects.spring.io/spring-boot/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[jwt]: https://jwt.io/
[cors]: https://en.wikipedia.org/wiki/Cross-origin_resource_sharing
[swagger]: https://swagger.io/
[allure]: https://docs.qameta.io/allure/
[junit]: https://junit.org/junit5/


