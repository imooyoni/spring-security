# Spring Security Module Project

This project is a Spring Boot application designed to serve as a modular Spring Security implementation. It provides a foundation for building secure, scalable, and maintainable authentication and authorization systems.

## Table of Contents
- [Technologies](#technologies)
- [Features](#features)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Technologies

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- H2 Database (for development)
- Log4j2
- Swagger (SpringDoc OpenAPI)
- JWT (JSON Web Tokens)
- Apache POI
- Gradle

## Features

- RESTful API with Spring Web
- Database integration with Spring Data JPA
- Security implementation with Spring Security
- JWT-based authentication
- Logging with Log4j2
- API documentation with Swagger
- Excel file handling with Apache POI
- Mustache templating engine support

## Getting Started

To get started with this project, make sure you have the following prerequisites:

- JDK 17
- Gradle

Clone the repository and build the project:

```bash
git clone [repository-url]
cd [project-directory]
./gradlew build
```

## Configuration

The project uses YAML configuration files. Make sure to set up the following files:

- `application.yaml`: Main configuration file
- `application-common.yaml`: Common configuration settings
- `application-local.yaml`: Local development settings

These files are ignored by Git for security reasons. Make sure to create them based on your environment needs.

## Usage

Run the application using:

```bash
./gradlew bootRun
```

The application will start on the default port 8080 (unless configured otherwise).

## API Documentation

API documentation is available through Swagger UI. Once the application is running, you can access it at:

```
http://localhost:8080/swagger-ui.html
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

[Specify your license here]
