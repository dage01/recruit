FROM openjdk:11
ARG JAR_FILE=2/target/recruit-0.0.1-SNAPSHOT.jar
COPY /target/recruit-0.0.1-SNAPSHOT.jar recruit.jar
ENTRYPOINT ["java","-jar","/recruit.jar"]

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
