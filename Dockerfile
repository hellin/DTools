FROM tomcat:8.5.37-jre8

COPY dist/DTools-5.4.2-beta/server/apache-tomcat-7.0.57/webapps/DTools.war /usr/local/tomcat/webapps/DTools.war
COPY config /usr/local/tomcat/config

