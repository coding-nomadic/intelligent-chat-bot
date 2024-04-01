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
- OpenAI APIs
- Apache Maven
- MongoDB Database
- Docker
- CI/CD
- Junit and Mockito for Unit Testing

## High Level Design for the Application - 

![Screenshot 2024-03-31 at 8 32 01 PM](https://github.com/coding-nomadic/intelligent-chat-bot/assets/8009104/7db46d01-b8e9-4492-9726-ccf89af3e6b0)


# Authorisation-Service

https://token-service.onrender.com/swagger-ui/index.html#/


## Application APIs are deployed in Render Cloud and UI is deployed in netlify -

- Swagger: https://recipe-service-ixhm.onrender.com/swagger-ui/index.html#/
- Recipe Service deployed here: https://recipe-service-ixhm.onrender.com
- Authentication Service Repo: [https://github.com/coding-nomadic/authorisation-service](https://github.com/coding-nomadic/recipe-service/tree/main/authorisation-service-backend)
- React UI Service Repo: [https://github.com/coding-nomadic/recipe-sharing-client](https://github.com/coding-nomadic/recipe-sharing-service/tree/main/recipe-sharing-client)
- React UI deployed here: https://recipe-himalayan.netlify.app/
