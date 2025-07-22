# Use official Tomcat image
FROM tomcat:9-jdk17

# Remove default webapps (optional but clean)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR into Tomcat webapps
COPY target/ticketApplication.war /usr/local/tomcat/webapps/ROOT.war
