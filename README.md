# algaworks-algafood-api

AlgaFood API is a project of an IFood inspired food delivery service designed by Algaworks. This API allows you to create and manage everything restaurant-related regarding the delivery business. Create a customer base, set and update products, payment methods, managers and keep track of orders - all this designed with the best practices for Rest APIs. 

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

## How to use

This application has been deployed and is available online at: 
> https://algaworks-algafood-api.herokuapp.com/

You can find the full **OpenApi 3.0 documentation** on this link:
> [Documentation](https://algaworks-algafood-api.herokuapp.com/swagger-ui/index.html)

The full Postman Collection is available in case you want to easily test the API: 

> [Postman Collection - heroku](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR%20Heroku.postman_collection.json)



Using the collection:

This API is protected with the OAUth 2.0 protocol. To be authenticated, follow these steps:
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

- Prepare your IDE by setting up the JDK for the project (Java 11)
- Wait for Maven to install all dependencies
- Set the profile to 'dev' on the application.properties file
- Click the run button on AlgaworksAlgafoodApiApplication

The full Postman Collection is available in case you want to easily test the API:

> [Postman Collection - local](https://github.com/GHBAlbuquerque/algaworks-algafood-api/blob/main/src/main/resources/postman/%5BAlgaWorks%5D%20AlgaFood%20-%20ESR.postman_collection.json)

### Using docker

To do this, you must have docker installed on your machine. If you don't, head over to https://docs.docker.com/desktop/windows/install/ and install the desktop application.

- Set the profile to 'dev' on the application.properties file
- 



## Features

- Responsive design with 2 breaks (min-width: 640px and 1024 px);
- Classy, sober and minimalist design;
- HTML manipulation with JQuery on Form validation, Content filter, responsive navbar and sliders.


## Links

- Repository: https://github.com/GHBAlbuquerque/Website_SnowFree
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