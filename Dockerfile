FROM maven:3.8.7 AS build
COPY . .
RUN mvn -s /etc/secrets/mvnsettings.xml clean package -DskipTests

FROM amazoncorretto:17
COPY --from=build /target/dmc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD java -jar  app.jar
