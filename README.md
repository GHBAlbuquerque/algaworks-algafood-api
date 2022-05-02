# algaworks-algafood-api

AlgaFood API is a project of an IFood inspired food delivery service designed by Algaworks. This API allows you to create and manage everything restaurant-related regarding the delivery business. Create a customer base, set and update products, payment methods, managers and keep track of orders - all this designed with the best practices for Rest APIs. 

[Versão em Português](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/README-PT.md)

## Technology

Here are the technologies used in this project.

* Java 11
* Spring Framework
* MySql Database
* Maven

## Services Used

* Github
* Heroku
* Postman
* Amazon S3
* SendGrid

## Class Diagram
![Diagrama de Classes](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/auxiliar/diagrama-de-classes-de-dominio.jpg)

*property of Algaworks.

## How to use

This application has been deployed and requests can be made using the following url:
> https://algaworks-algafood-api.herokuapp.com/

You can find the full **OpenApi 3.0 documentation** on this link:
> [Documentation](https://algaworks-algafood-api.herokuapp.com/swagger-ui/index.html)

The full **Postman Collection** is available in case you want to easily test the API: 

> [Postman Collection - heroku](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR%20Heroku.postman_collection.json)

------------------

Using the collection:

First off, import the json file on Postman. 

The collection is ready to use, though this API is protected with the OAUth 2.0 protocol. To be authenticated, follow these steps:

* Click on the parent folder ([AlgaWorks] AlgaFood - ESR Heroku)
* Go to "Authorization"
* OAuth 2.0 grant type will be already selected and set up. Scroll down and click on **Get New Access Token**
* Proceed and use token
* All endpoints have been configured to inherit auth from parent folder.

User credentials are obtained from users registered on the database. To get new authenticated users, use the api to persist new data. 


- Groups and Permissions to use endpoints apply.



## Getting started - Running Locally

Download or clone this project and run on an IDE (preferably IntelliJ).

Running locally requires you to have JDK 11 or greater installed and a local MySQL Database on port 3306.

1. Prepare your IDE by setting up the JDK for the project (Java 11)
2. Wait for Maven to install all dependencies
3. Set the profile to 'dev' on the application.properties file
4. Click the run button on AlgaworksAlgafoodApiApplication

The full Postman Collection is available in case you want to easily test the API:

> [Postman Collection - local](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR.postman_collection.json)

### Using docker

To do this, you must have docker installed on your machine. If you don't, head over to https://docs.docker.com/desktop/windows/install/ and install the desktop application.

1. Pull the application image from [Docker Hub](https://hub.docker.com/layers/ghbalbuquerque/algafood-api-ghba/latest/images/sha256:d700da54759acd6a91e99899e8c76343d78bfa4a86dc4f4786b7baa67c5fb7c4)
2. On the project root folder, use the terminal to run the command 'docker-compose up'
3. Wait for the images to be built and the containers to run
4. To stop the containers, use the terminal to run the command 'docker-compose down'

You can use the local Postman Collection to easily test the API. Just remove port (:8080) from the url variables :

> [Postman Collection - local](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR.postman_collection.json)

If you wish to generate a docker image of your own, this file might help you with some basic commands
> [Docker Aux file](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/auxiliar/docker_commands.txt)


## Features

- Best Design Principles of Rest APIs
- Hateoas
- SOLID
- DDD
- Exception handling
- File upload and download with AWS SDK and S3 Bucket
- Automatic e-mail sending with Spring Email and SendGrid
- Documentation (OpenApi)
- Authentication and Security with OAuth 2.0
- Use of Docker


## Links

- Repository: https://github.com/GHBAlbuquerque/algaworks-algafood-api
- Algaworks repository: https://github.com/algaworks/curso-especialista-spring-rest
- Algaworks Website: https://www.algaworks.com/
- Curso Especialista Spring Rest: https://cafe.algaworks.com/esr-lista-de-espera/
- In case of sensitive bugs like security vulnerabilities, please contact
ghb.albuquerque@gmail.com directly instead of using issue tracker. We value your effort
to improve the security and privacy of this project!


## Versioning

1.0.0.0


## Authors

* **Giovanna Albuquerque**: @GHBAlbuquerque (https://github.com/GHBAlbuquerque)
* **Algaworks**: @AlgaWorks (https://github.com/algaworks)

Please follow github and join us!
Thanks to visiting me and good coding!
