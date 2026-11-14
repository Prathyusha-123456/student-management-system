# Student Management System

A full-stack web application to manage student records, built with **Java 17, Spring Boot, Spring Data JPA, Thymeleaf, Bootstrap and MySQL**.

## Features
- Create, view, update and delete students (CRUD)
- Search by name, email or roll number, with server-side pagination
- Form validation (required fields, email format, past date of birth) and duplicate email / roll number checks
- Relational model: many students belong to one course
- Layered architecture: Controller -> Service -> Repository -> Database
- REST API (`/api/students`) alongside the Thymeleaf UI
- Integration tests with MockMvc
- H2 in-memory DB by default; MySQL via profile

## Tech Stack
Java 17 | Spring Boot 3 | Spring MVC | Spring Data JPA (Hibernate) | Thymeleaf | Bootstrap 5 | MySQL / H2 | Maven | JUnit 5

## Run
Requires JDK 17+ and Maven.

    mvn spring-boot:run

Open http://localhost:8080 (H2 console: /h2-console, JDBC URL `jdbc:h2:mem:smsdb`, user `sa`).

### Use MySQL
Edit `src/main/resources/application-mysql.properties`, then:

    mvn spring-boot:run -Dspring-boot.run.profiles=mysql

## REST API
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/students?keyword=&page=&size= | List / search |
| GET | /api/students/{id} | Get one |
| POST | /api/students | Create |
| PUT | /api/students/{id} | Update |
| DELETE | /api/students/{id} | Delete |

Sample body:

    {"firstName":"Meera","lastName":"S","email":"meera@example.com","rollNumber":"CSE2210",
     "dateOfBirth":"2004-05-20","course":{"id":1}}

## Project Structure
    src/main/java/com/example/sms
      controller/   MVC + REST controllers
      service/      business logic
      repository/   Spring Data JPA repositories
      model/        JPA entities (Student, Course)
    src/main/resources/templates   Thymeleaf pages

## Resume Description (copy-paste)
**Student Management System** | Java, Spring Boot, Spring Data JPA, Thymeleaf, MySQL, Bootstrap
- Built a full-stack CRUD web application to manage student records using Spring Boot with a layered MVC architecture.
- Designed a relational schema (Student - Course, many-to-one) and implemented data access with Spring Data JPA/Hibernate on MySQL.
- Implemented search, pagination, bean validation and duplicate-record checks for data integrity.
- Exposed RESTful APIs for all operations and wrote MockMvc integration tests with JUnit 5.
- Created a responsive UI using Thymeleaf and Bootstrap.

## Ideas to extend
Spring Security login (Admin/Teacher roles), attendance and marks modules, Docker, deployment on Render/Railway, Swagger/OpenAPI docs.
