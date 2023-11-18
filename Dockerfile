FROM tomcat:9.0.50-jdk8-openjdk-slim
COPY ./target/spring-mybatis-example.war /usr/local/tomcat/webapps/ROOT.war
