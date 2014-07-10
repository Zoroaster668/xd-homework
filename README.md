xd-homework
===========

"Homework" assignment to create a Spring XD sink for Elasticsearch.  Current structure includes .IDEA project files for any who care.  To install, drop src/main/resources/es-store.xml into [xd-home]/modules/sink and restart the xd server.
<br>
Options available:<br>
        index: ES index to store data in, default "twitterIndex"<br>
        cluster: ES cluster to connect to, default "Dazzler"<br>
<br>
Stream create/deploy command in XD console:<br>
	stream create --name ticktock-es --definition "time | es-store" --deploy<br>
This would be expected to store various docs in ES with the current time (when it ran).<br>
<br>
Current problem: XD fails when I attempt to create a stream, claiming: <br>
17:52:40,020 ERROR task-scheduler-5 handler.LoggingHandler:145 - org.springframework.messaging.MessageDeliveryException: Dispatcher has no subscribers for chann
el 'singlenode:default,admin,singlenode,hsqldbServer:9393.ticktock-es.0'.<br>
        at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:81)<br>
	(blahblahblah)<br>
Caused by: org.springframework.integration.MessageDispatchingException: Dispatcher has no subscribers<br>
        at org.springframework.integration.dispatcher.UnicastingDispatcher.doDispatch(UnicastingDispatcher.java:107)<br>
        at org.springframework.integration.dispatcher.UnicastingDispatcher.dispatch(UnicastingDispatcher.java:97)<br>
        at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:77)<br>
        ... 52 more<br>
<br>
Not sure why this is yet.<br>