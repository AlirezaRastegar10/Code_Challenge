# Spring Boot 3.1.4 User Profile Application
This project demonstrates storing a user with ID-level information using Spring Boot 3.1.4 and JSON Web Tokens (JWT). It includes the following features:

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with AOP and custom annotation
* Customized access denied handling
* Logout mechanism
* Redis caching
* Bean monitoring

## Technologies
* Spring Boot 3.1.4
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* MySql
* Redis
* Mapstruct

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/AlirezaRastegar10/Code_Challenge.git`
* Navigate to the project directory: cd code_challenge
* Run the docker-compose file to run the redis and MySQL container on the appropriate port
* Run the scripts in the db.sql file to create the database and tables along with an admin
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run
* To access the endpoints, you can import the code_challenge.postman_collection.json file in postman
* To see how to call endpoints, you can go to this address: http://localhost:8080/swagger-ui.html

-> The application will be available at http://localhost:8080.