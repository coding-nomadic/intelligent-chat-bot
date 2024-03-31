# Intelligent-Chatbot-Service

This is Backend for recipe server built in springboot, REST, Open AI, Spring Data JPA, MongoDB -

## Features in this application- 

- JWT token support for quick login.
- Regular Username/Password authentication.
- Stores user information in the PostgreSQL database.
- Email verification to confirm during user registration
- Create, view, update recipes, 
- comments, ratings and view ingredients, cuisines
- Stores API data in Cache to minimize network calls.
- Scheduled tasks send email every 14 days to the most liked recipe makers
- Analytics API covered to show case in UI.

## Tools and Technologies
- Java 11
- Spring Boot
- Spring Web MVC
- Swagger UI 
- Spring Data JPA
- Spring Validation
- Apache Maven
- MongoDB Database
- Docker
- CI/CD
- Junit and Mockito for Unit Testing

## High Level Design for the Application - 

![image](https://github.com/coding-nomadic/recipe-service/assets/8009104/94f1fba3-5e7f-492c-a392-d6e7ebd0be4c)

# Authorisation-Service


https://token-service.onrender.com/swagger-ui/index.html#/


## Application APIs are deployed in Render Cloud and UI is deployed in netlify -

- Swagger: https://recipe-service-ixhm.onrender.com/swagger-ui/index.html#/
- Recipe Service deployed here: https://recipe-service-ixhm.onrender.com
- Authentication Service Repo: [https://github.com/coding-nomadic/authorisation-service](https://github.com/coding-nomadic/recipe-service/tree/main/authorisation-service-backend)
- React UI Service Repo: [https://github.com/coding-nomadic/recipe-sharing-client](https://github.com/coding-nomadic/recipe-sharing-service/tree/main/recipe-sharing-client)
- React UI deployed here: https://recipe-himalayan.netlify.app/
