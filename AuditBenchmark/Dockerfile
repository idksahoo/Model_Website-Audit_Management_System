FROM openjdk:8-jdk-alpine
ADD target/benchmark.jar benchmark.jar
ENTRYPOINT ["java","-jar", "/benchmark.jar"]