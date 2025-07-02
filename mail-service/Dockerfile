# Stage 1: Build common-dto
FROM maven:3.9.6-eclipse-temurin-21 AS common-dto-builder

WORKDIR /build
COPY ../common-dto /build/common-dto
RUN mvn -f common-dto/pom.xml clean install

# Stage 2: Build main Spring Boot app
FROM maven:3.9.6-eclipse-temurin-21 AS app-builder

WORKDIR /app
COPY . /app

# Copy installed common-dto from first stage into this stage's .m2 repo
COPY --from=common-dto-builder /root/.m2 /root/.m2

# Now build the Spring Boot app
RUN mvn clean package -DskipTests

# Stage 3: Create lightweight runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=app-builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]