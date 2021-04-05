FROM openjdk:11-jdk-slim AS build
COPY . /src
WORKDIR /src
RUN ./gradlew assemble --no-daemon

FROM openjdk:11-jre-slim
RUN mkdir /app
COPY --from=build /src/build/libs/*.jar /app/boot.jar

ENTRYPOINT ["java", "-jar", "/app/boot.jar", "--continuous"]
