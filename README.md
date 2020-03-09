# SimpleBlogPLatform
A backend solution to a simple blog platfrom written using Java Spring Boot.
# Instructions
To run the app, Java installation is required. The app can be run using any IDE for Java, but IntelliJ IDEA is preffered due to the fact that it comes with the required frameworks and libraries.
The original database used for the app is Postgresql, but it can be changed to a simple MySql database by changing the data source in "application.properties".
Json objects are returned upon request.
If there are no tables in the database, the app generates them on it's own.
A database seed is provided on boot.

Running the app:
  - Run the app inside the IDE
  - Use a tool such as Postman to test

Using the app:
  - The app consumes and produces Json objects
  - The default api address is localhost:8081/
  - To GET all the posts in DB, use api/posts
  - To GET a single post, use api/posts/yourSlugHere (refers to the post requested by slug)
  - To POST a new post, use api/posts
  - To DELETE a post, use api/posts/yourSlugHere
  - To PUT(update) a post, use api/posts/yourslughere
  - To GET all tags in the DB, use api/tags
  - To filter posts by tag, use api/posts/?tag=yourTagHere

### Tech
- Java
- Spring Boot
- ORM
- Postgresql
- MySql

### Installation
The app requires [Spring Boot](https://spring.io/projects/spring-boot) to run.
[IntelliJ IDEA]

(https://www.jetbrains.com/idea/) makes it easier to import dependencies required.

Install the dependencies and start the server.