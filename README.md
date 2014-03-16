Project 100 Code Challenge
===========

This application is built using Maven for dependency management. It is designed to run on Apache Tomcat 7 with Java 7.

Running
-------

To compile the application:
* Install Maven and run `mvn clean package` from the base directory
* This will create a .war file in the `/api/target` directory
* Copy the war file to the `/webapps` directory of a Tomcat 7 instance
* Start tomcat

Usage
-----

The following endpoints are available:
* POST `/data`
** posts a new data packet to the server with the data packet as the request body.
* GET `/packets/{number}`
** retrieves up to 10 of the most recent data packets.
* POST `/registerEndpoint/{type}`
** registers the URL provided as the post body to the type specified in the request.