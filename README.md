# Waracle Cake Shop

## Application Summary:
This project exposes APIs for cake entity. To run this project you'll need docker installed in your system. You can follow the steps here, https://docs.docker.com/desktop/windows/install/. These steps may differ based on your OS. 
## Steps to run this project:
- Download/checkout this project in your system
- Extract it (if downloaded)
- Go to the root folder for this project and run command in the terminal: _docker-compose up_
- The required images will be automatically downloaded and containers will be created by [docker](https://docs.docker.com/) and you should have your application running
- You can access the application by accessing endpoint, http://localhost:8080/
- You can see all the APIs in this project here, http://localhost:8080/swagger-ui/#/cake-controller
- In order to access both these endpoints you'll need to authenticate through your google account as this project used OAuth2 for authentication
- This application is registered in the [Google API console](https://console.developers.google.com/)

## Technical Details
This project runs 2 containers: one for mysql and other for running the cake service. You don't need to installe any other software in order to run this project. Just ensure port 8080 is not blocked by any other process. One point to note is, there are some cakes that are auto created in the system when server boots up.

## API details
- Root of the server (/) lists all the cakes currently in the system in the JSON format.
- POST request on */cakes* endpoint allows you to create a new cake in the system. More details here, [swagger](http://localhost:8080/swagger-ui/#/cake-controller)
- GET request on */cakes* endpoint allows you to download all cakes in a JSON file. More details here, [swagger](http://localhost:8080/swagger-ui/#/cake-controller)
- PUT request on */cakes* endpoint allows you to change the details of a cake. More details here, [swagger](http://localhost:8080/swagger-ui/#/cake-controller)
- DELETE request on */cakes* endpoint allows you to delete a cake. More details here, [swagger](http://localhost:8080/swagger-ui/#/cake-controller)
