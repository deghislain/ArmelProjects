Project Description: RestBufferMessageService is a small RESTful service that provide messaging
service. Users can send and receive messages using the web client. Messages sent by users are stored in a XML file.

The receiver receive the new messages that have not been received in the previous receive call. 
If the checkbox purge is checked, the service will delete all messages of the receiver.

The following technologies were used: java, eclipse, tomcat as web runtime, Jax-RS, maven runtime.

To access the services, deploy the application on tomcat, then go here: http://localhost:8080/RestBufferMessageService.