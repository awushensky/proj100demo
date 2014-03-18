#Project 100 Code Challenge

This application is built using Maven for dependency management. It is designed to run on Apache Tomcat 7 with Java 7.

##Assumptions

* There is a unique constraint on the listeners' URL/TYPE combination
* Listeners are expecting a POST request with the data packet as the JSON formatted body
* No stack traces should be exposed to clients, but all should be logged

##Running

To compile the application:
* Install Maven and run `mvn clean package` from the base directory
* This will create a .war file in the `/api/target` directory
* Copy the war file to the `/webapps` directory of a Tomcat 7 instance
* Start tomcat

##Usage

The following endpoints are available:
* POST `/data` - posts a new data packet to the server with the data packet as the request body.
* GET `/packets/{number}` - retrieves up to 10 of the most recent data packets.
* POST `/registerEndpoint/{type}` - registers the URL provided as the post body to the type specified in the request.

##Design

###Data Flow Diagrams

###Database Schema


##Future Improvements

Presently, this application supports only one running instance at a time. Consequently, horizontal scalability is
impossible. To improve this, I would recommend implementing an asynchronous message passing design using something like
JMS (probably Amazon's SQS or similar). The following diagram illustrates how this could be accomplished:

The code uses a java `enum` to enumerate the types of data packets. This is nice for code cleanliness, but can cause
problems if the types of data packets changes with frequency. Any change to this enum requires a recompile and a
redeploy. If the data packet types change regularly, it might make sense to replace the `enum` with a `string` and
instead manage the types exclusively from the `types` database table.

The app keeps a record of every data packet that was ever sent through it. This has the benefit of good auditing, but
the obvious disadvantage of potentially taking up tons of database space. If required, there are two ways of addressing
this:

1. Set up a cron job (probably through spring) to periodically cull old records in the database
2. Set up a database trigger to automatically cull old records on insert
