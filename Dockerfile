FROM openjdk:17

WORKDIR /app

ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=$PROFILE
RUN echo $SPRING_PROFILES_ACTIVE

#todo 버전관리
COPY ./build/libs/woodada_be-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
