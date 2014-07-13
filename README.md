xd-homework
===========

"Homework" assignment to create a Spring XD sink for Elasticsearch.
<br>

Available Options
=================
<ul>
        <li>index: ES index to store data in, default "twitterIndex"</li>
        <li>cluster: ES cluster to connect to, default "Dazzler"</li>
</ul>
<br>

Setup
=====
<ol>
    <li>Set up Spring XD according to its documentation.</li>
    <li>Download/install ElasticSearch</li>
    <li>Drop /src/main/resources/es-store.xml into $XD_HOME/xd/modules/sink.</li>
    <li>Start the xd shell and attempt to create a stream using the new sink.</li>
</ol>
<br>

Current Status
==============
Having a problem.  I've been attempting to create a stream using this command in the console:<br>
	stream create --name ticktock-es --definition "time | es-store" --deploy<br>
This would be expected to store various docs in ES with the current time (when it ran).<br>
<br>
Current problem: XD fails when I attempt to create a stream, claiming this in the console output: <br>
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
...and this in the xd runtime's logfile:<br>
20:27:33,380 ERROR Stream Deployer org.springframework.xd.dirt.server.StreamDeploymentListener:378 - Exception caught while handling event<br>
java.lang.IllegalStateException: Container 8e57fbd8-b770-420a-b3ab-26b28c80ab3a experienced the following error deploying module es-store-1 of type sink: java.lang.IllegalArgumentException: Cannot instantiate 'IntegrationConfigurationInitializer': org.springframework.integration.jmx.config.JmxIntegrationConfigurationInitializer<br>
    at org.springframework.xd.dirt.server.ModuleDeploymentWriter.validateResults(ModuleDeploymentWriter.java:543)<br>
    at org.springframework.xd.dirt.server.StreamDeploymentListener.deployStream(StreamDeploymentListener.java:191)<br>
    at org.springframework.xd.dirt.server.StreamDeploymentListener.onChildAdded(StreamDeploymentListener.java:138)<br>
    at org.springframework.xd.dirt.server.StreamDeploymentListener.access$100(StreamDeploymentListener.java:58)<br>
    at org.springframework.xd.dirt.server.StreamDeploymentListener$EventHandler.call(StreamDeploymentListener.java:370)<br>
    at org.springframework.xd.dirt.server.StreamDeploymentListener$EventHandler.call(StreamDeploymentListener.java:339)<br>
    at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:334)<br>
    at java.util.concurrent.FutureTask.run(FutureTask.java:166)<br>
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1110)<br>
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:603)<br>
    at java.lang.Thread.run(Thread.java:722)<br>
<br>
Not sure why this is yet.<br>

Remaining To-Do's
=================
<ul>
    <li>Sign up for a twitter account to get API credentials to test using the twitterstream/search sources.</li>
    <li>Set that up with a search term, say, "Spring XD".
    <li>Complete testing etc.</li>
    <li>Kibana dashboard with some metrics, such as other words commonly appearing in tweets with SpringXD, some predictive analysis of whether the tweets were supportive or critical (by analyzing other words in the tweet using ES indexing features), or the top pages being linked to in tweets that mention the search term.
    <li>Performance enhancements: currently this sink just attempts to insert each document singly, which is generally inefficient.  Bulk updates would be better; perhaps by using a processor to collect some number of documents, then pass them to a bulk insertion sink all at once.  Depending on the frequency of observed retweets or other repeated content it might be useful to maintain some sort of matching or hash mechanism to connect those, which could result in better performance if we end up storing less data.
</ul>