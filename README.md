<h1 align="center" style="color: #4285F4"> Internship App API </h1>

## <span style="color: #4285F4"> Project Description

This repository contains the backend implementation for the ***Student Internship App***, developed using Spring Boot. The backend provides RESTful APIs to support the application's core functionalities.

## <span style="color: #4285F4"> Getting Started

1. **Backend (Java with Spring Boot)**:
   - Ensure you have Java and Maven installed.
   - Clone the repository: `git clone https://github.com/NextStep-RE/NextStepAPI.git`
3. **Database (MySQL):**
   - Navigate to the `docker` folder in the project.
   - Run the following command to start the MySQL database container using Docker Compose `docker compose -f internship-db.yaml up -d`
   - The database schema and initial data are managed using **Liquibase**, which automatically applies changesets on application startup.

## <span style="color: #4285F4"> Technologies Used
- **<i>Java with Spring Boot</i>**: The backend of the application is developed in Java, using the Spring Boot framework to ensure rapid and efficient development.
- **<i>MySQL</i>**: The application's database is managed by MySQL, ensuring efficient data storage and access.
- **<i>Docker</i>**: Used to create and manage the MySQL database container, ensuring consistency across environments.
- **<i>Liquibase</i>**: A database migration tool used to manage schema changes and version control for the database.

