#Project 100 Code Challenge

This application is built using Maven for dependency management. It is designed to run on Apache Tomcat 7 with Java 7.

##Assumptions

* There is a unique constraint on the listeners' URL/TYPE combination
* Listeners are expecting a POST request with the data packet as the JSON formatted body
* No stack traces should be exposed to clients, but all should be logged

##Running

To compile the application:
* Install Maven and run `mvn clean package` from the base directory *(note: you may see exceptions during compile. These are test cases and are normal)*
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

The following data flow diagrams illustrate the three main use cases described in the problem.

####Retrieving Packets
![Get Packet Flow](/documentation/diagrams/get_packet_flow.png)

####Registering Listeners
![Get Packet Flow](/documentation/diagrams/register_listener_flow.png)

####Receive Data Packet
![Get Packet Flow](/documentation/diagrams/receive_packet_flow.png)

###Database Schema

![DB Schema](/documentation/diagrams/db_schema.png)

###Data Format

The data packet is composed of the following JSON structure:
```
{
  "position":{
     "long":"100",
     "lat":"100"
  },
  "type":"CUSTOMER"
}
```

The responses from the server always take the following JSON form:
```
{
  "status":"SUCCESS",
  "response": [ list of data packets ],
  "exception": "exception description"
}
```

The following values are valid types:
* `CUSTOMER`
* `VEHICLE`
* `SUPPORT_TECHNICIANS`

##Future Improvements

Presently, this application supports only one running instance at a time. Consequently, horizontal scalability is
impossible. To improve this, I would recommend implementing an asynchronous message passing design using something like
JMS (probably Amazon's SQS or similar). The following diagram illustrates how this could be accomplished:

The code uses a java `enum` to enumerate the types of data packets. This is nice for code cleanliness, but can cause
problems if the types of data packets changes with frequency. Any change to this enum requires a recompile and a
redeploy. If the data packet types change regularly, it might make sense to replace the `enum` with a `string` and
instead manage the types exclusively from the `types` database table.

The database currently uses the `types` table to describe the types available. Other tables reference types by a
non-enforcing foreign key. This reduces database footprint, but increases the complexity of queries and inserts. It
might be worth it to switch to using strings in the tables for the types if database footprint is less of a concern than
query or insert speed.

The app keeps a record of every data packet that was ever sent through it. This has the benefit of good auditing, but
the obvious disadvantage of potentially taking up tons of database space. If required, there are two ways of addressing
this:

1. Set up a cron job (probably through spring) to periodically cull old records in the database
2. Set up a database trigger to automatically cull old records on insert
