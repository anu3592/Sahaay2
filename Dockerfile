FROM tomcat:9.0.85-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# WAR file ko ROOT ke naam se deploy karo
COPY target/ticketApplication.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
