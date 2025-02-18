FROM openjdk:17-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw clean package
CMD ["java", "-jar", "target/author-finder.jar"]
