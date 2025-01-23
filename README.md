# Author Finder Application

A Spring Boot application that provides an API to search for authors and their works using the OpenLibrary API, with local database caching.

## Features

- Search authors by name
- Retrieve author's works by ID
- Automatic caching of API results in MySQL database
- RESTful API endpoints

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository:
```bash
git clone [your-repository-url]
cd author-finder
```

2. Configure MySQL:
- Create a MySQL database (optional, application will create it automatically)
- Update database credentials in `src/main/resources/application.properties` if needed

3. Build the application:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Search Authors
```
GET /api/authors/search?name={name}
```
Search for authors by name. Returns a list of authors matching the search criteria.

Example:
```bash
curl "http://localhost:8080/api/authors/search?name=tolkien"
```

Response:
```json
[
  {
    "id": "OL23919A",
    "name": "J.R.R. Tolkien"
  }
]
```

### Get Author's Works
```
GET /api/authors/{authorId}/works
```
Retrieve all works by a specific author.

Example:
```bash
curl "http://localhost:8080/api/authors/OL23919A/works"
```

Response:
```json
[
  {
    "id": "OL27448W",
    "title": "The Hobbit",
    "author": {
      "id": "OL23919A",
      "name": "J.R.R. Tolkien"
    }
  }
]
```

## Database Schema

The application uses two main tables:

### authors
- `id` (VARCHAR(50)) - Primary Key
- `name` (VARCHAR(255))

### works
- `id` (VARCHAR(50)) - Primary Key
- `title` (VARCHAR(255))
- `author_id` (VARCHAR(50)) - Foreign Key to authors.id

## Technology Stack

- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Liquibase
- Maven

## AWS Elastic Beanstalk Deployment

For deploying to AWS Elastic Beanstalk, follow these steps:

1. Create an Elastic Beanstalk environment:
   - Platform: Java
   - Java version: Corretto 17
   - Environment type: Load balanced

2. Configure RDS:
   - Engine: MySQL 8.0
   - Instance class: db.t3.micro (for development)
   - Multi-AZ: No (for development)

3. Update environment variables in Elastic Beanstalk:
   ```
   SPRING_DATASOURCE_URL=jdbc:mysql://<RDS-ENDPOINT>:3306/author_finder
   SPRING_DATASOURCE_USERNAME=<DB-USERNAME>
   SPRING_DATASOURCE_PASSWORD=<DB-PASSWORD>
   ```

4. Deploy using AWS CLI:
   ```bash
   aws elasticbeanstalk create-application-version \
     --application-name author-finder \
     --version-label v1 \
     --source-bundle S3Bucket=<bucket>,S3Key=author-finder.jar
   
   aws elasticbeanstalk update-environment \
     --environment-name author-finder-env \
     --version-label v1
   ```

## CI/CD Pipeline (Bitbucket)

Create a `bitbucket-pipelines.yml` file in your repository:

```yaml
pipelines:
  branches:
    master:
      - step:
          name: Build and Test
          image: maven:3.8.4-openjdk-17
          caches:
            - maven
          script:
            - mvn clean install
          artifacts:
            - target/*.jar
      - step:
          name: Deploy to AWS
          deployment: production
          script:
            - pipe: atlassian/aws-elasticbeanstalk-deploy:1.1.0
              variables:
                AWS_ACCESS_KEY_ID: $AWS_ACCESS_KEY_ID
                AWS_SECRET_ACCESS_KEY: $AWS_SECRET_ACCESS_KEY
                AWS_DEFAULT_REGION: $AWS_DEFAULT_REGION
                APPLICATION_NAME: 'author-finder'
                ENVIRONMENT_NAME: 'author-finder-env'
                ZIP_FILE: 'target/author-finder.jar'
                VERSION_LABEL: 'author-finder-${BITBUCKET_BUILD_NUMBER}'
                WAIT: 'true'
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
