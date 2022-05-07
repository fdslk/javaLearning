FROM maven:3.6.3-jdk-8
COPY newrelic app/newrelic
COPY src app/src
COPY pom.xml app/
ENV INSIGHTS_INSERT_KEY=""
RUN mvn -f app/pom.xml clean package
CMD ["java", "-javaagent:app/newrelic/newrelic.jar", "-jar", "app/target/newrelic-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080