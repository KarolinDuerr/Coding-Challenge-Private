FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/MBTI_CodingTask_Merge.jar
ENTRYPOINT ["java", "-jar", "/app/MBTI_CodingTask_Merge.jar"]