# baseimage jdk17
FROM openjdk:17-alpine

# Set working directory inside the container
WORKDIR /home/backspace

# The application's jar file
ARG JAR_FILE=./build/libs/authorization-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY ${JAR_FILE} /home/backspace/authorization.jar

# Set environment variables
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8080"
ENV TZ=Asia/Seoul

# Make port 8080 available to the world outside this container
EXPOSE 8080

#jar 파일 실행
ENTRYPOINT ["java", "-jar", "/home/backspace/authorization.jar"]