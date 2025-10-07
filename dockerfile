FROM maven:3.9.9-amazoncorretto-21-al2023 AS build-graphql

LABEL maintainer="Logic Flare <logicflare3@gmail.com>"

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-al2023-jdk

WORKDIR /app

COPY --from=build-graphql /app/target/*.jar graphql.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "graphql.jar"]
