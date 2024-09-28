#
#
# FROM adoptopenjdk/openjdk8:ubi as base
# WORKDIR /app
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN mkdir -p /root/.m2 \
#    && mkdir /root/.m2/repositoryRUN ./mvnw dependency:resolve \

# COPY src ./src

# COPY settings.xml /root/.m2/
# COPY settings.xml /usr/share/maven/ref/
# FROM base as development
# CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

# FROM base as build
# RUN ./mvnw package -DskipTests -s settings.xml

# FROM adoptopenjdk/openjdk8:ubi as production
# EXPOSE 8080
# COPY --from=build /app/target/springboot-backend-*.jar /springboot-backend.jar
# CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/springboot-backend.jar"]
# Set the working directory inside the container

# FROM adoptopenjdk/openjdk8:ubi
# EXPOSE 8081
# WORKDIR /app
# ARG JAR_FILE=target/springboot-backend-0.0.1-SNAPSHOT.jar
# ARG version=0.8.0
# ADD ${JAR_FILE} /app/app.jar
# #ADD --chmod=644 https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v$version/opentelemetry-javaagent-all.jar .
# #ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent-all.jar"
# COPY db-config.properties /app/etc/application.properties
# COPY src/main/resources/viettelsecurity.jar src/main/resources/viettelsecurity.jar
# ENTRYPOINT ["java","-jar","-Dspring.config.location=/app/etc/application.properties","/app/app.jar"]
# #ENTRYPOINT ["java","-jar","/app/app.jar"]

#Stage 1: Build the application
FROM adoptopenjdk/openjdk8:ubi AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
# COPY src/main/resources/viettelsecurity.jar src/main/resources/viettelsecurity.jar
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw install:install-file -Dfile=/app/src/main/resources/viettelsecurity.jar -DgroupId=com.viettel.security -DartifactId=viettelsecurity -Dversion=1.0.0 -Dpackaging=jar

RUN ./mvnw dependency:resolve
RUN ./mvnw clean package

# Stage 2: Run the application
FROM adoptopenjdk/openjdk8:ubi
WORKDIR /app
COPY --from=build /app/target/springboot-backend-*.jar /app.jar
EXPOSE 8081
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]