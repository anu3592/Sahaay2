# Use official Tomcat 9 base image
FROM tomcat:9.0.85-jdk17

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to webapps
COPY target/ticketApplication.war /usr/local/tomcat/webapps/ticketApplication.war

# Expose port (Render auto detects it)
EXPOSE 8080
