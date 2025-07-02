
```markdown
# TFM Core Service

This is the core backend service for the InvierteIO platform. It is built with Java 21, Spring Boot, and Maven, and provides RESTful APIs for project and subproject management, file uploads (local and AWS S3), and more.

## Features

- Project and subproject management (CRUD)
- File upload support (local and AWS S3)
- Domain-driven design with JPA entities and domain models
- Reactive programming with Project Reactor
- Unit, integration, and mutation testing
- CI/CD with GitHub Actions, CodeQL, PIT, and SonarCloud

## Requirements

- Java 21
- Maven 3.9+
- Docker (for containerization)
- AWS credentials (for S3 and ECS integration in production)

## Getting Started

### Local Development

1. Clone the repository:
   ```sh
   git clone https://github.com/InvierteIO/tfm-core-srv.git
   cd tfm-core-srv
   ```

2. Configure environment variables in `application-dev.properties`:
   ```
   app.upload-dir=uploads
   ```

3. Run the application:
   ```sh
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

### Production (AWS S3)

Set the following properties in your production configuration:
```
aws.s3.bucket-name=your-bucket
aws.s3.region=us-east-1
```

### Docker

Build and run the Docker image:
```sh
docker build -t tfm-core-srv .
docker run -d --name tfm-core-srv-app -p 8081:8081 tfm-core-srv
```

## Testing

Run all tests (unit, integration, mutation):
```sh
mvn verify
```

## CI/CD

- GitHub Actions workflow: `.github/workflows/continuous-integration.yml`
- Includes unit/integration tests, mutation testing, CodeQL, SonarCloud, and Slack notifications.

## API

The service exposes REST endpoints for managing projects, subprojects, and file uploads. See the OpenAPI/Swagger documentation (if enabled) at `/swagger-ui.html`.

## License

MIT License. See `LICENSE` file for details.



## Estado del código

![CI](https://github.com/InvierteIO/tfm-core-srv/actions/workflows/continuous-integration.yml/badge.svg)
![CD](https://github.com/InvierteIO/tfm-core-srv/actions/workflows/continuous-deployment.yml/badge.svg)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=InvierteIO_tfm-core-srv)](https://sonarcloud.io/summary/new_code?id=InvierteIO_tfm-core-srv)