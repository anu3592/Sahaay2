FROM tomcat:9.0.85-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/ticketApplication.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
