# About

This is a User Profile Management application which provides rest CRUD api's to manage user profiles of an organisation.
Application is built as a micro service which can deployed on any cloud platform or as an on-prem application.

## 1. Tools and Technologies Used
* Spring Boot — 2.0.5.RELEASE  
* JDK — 1.8 or later
* Spring Framework — 5.0.8 RELEASE
* Hibernate — 5.2.17.Final
* Mongo DB - 4.0.4
* Maven — 3.3.9
* Swagger — 2+
* springfox-swagger2 — 2.8.0
* springfox-swagger-ui — 2.8.0
* IDE — Eclipse

## 2. Prerequisite

1. Install MongoDB
2. Create user in mongodb to be used for this project. Follow the commands below to achieve the same
   * Run mongodb command line -> mongo
   * use admin
   * db.getSiblingDB("admin").createUser({ user: "user1", pwd: "password", roles: [ "root" ] }) - Please note the user name and password if the above ones are not used. The respective username and password to be mentioned in the application.properties before building and running this application.   

## 3. Setup and Execution

1. Clone the project
   ```
   git clone
   ```
2. Change the user name and password of mongodb in src/main/resource/application.properties
   This is how the file looks like
   ```
   spring.data.mongodb.authentication-database=admin
   spring.data.mongodb.database=admin
   ##### This needs to be changed based on the user created in MongoDB
   spring.data.mongodb.username=user1
   ##### This needs to be changed based on the password set for the user created in MongoDB
   spring.data.mongodb.password=password
   spring.data.mongodb.port=27017
   spring.data.mongodb.host=localhost

   logging.level.root = INFO

3. Go to base directory of the project to build and run the application
   ```
   $ mvn clean
   $ mvn package -Dmaven.test.skip=true
   ```
   If the build is successful. Executable jar file - crm-app-0.0.1-SNAPSHOT.jar should be create in target folder. Run the below command to start the application
   ```
   $ java -jar target/crm-app-0.0.1-SNAPSHOT.jar
   ```
   Application starts up, to confirm the same look for the text "Tomcat started on port(s): 8080 (http)" on the console.

4. This application is integrated with Swagger UI, all the API documentation can be found by accessing the URL - http://localhost:8080/swagger-ui.html

5. Please refer Swagger-API.json file for API documentation if
