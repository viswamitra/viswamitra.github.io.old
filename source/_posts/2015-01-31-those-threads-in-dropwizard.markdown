---
layout: post
title: "Configuring Jetty threads in Dropwizard"
date: 2015-01-31 02:18:19 +0530
comments: true
categories: [java, dropwizard, jetty, concurrency]
---

We use Dropwizard framework for writing web applications at work, as it is nifty, out of the box, and production ready. While this post is not about Dropwizard framework in specific, I would like to share some interesting insights into what I have learnt while configuring threads for Jetty, the HTTP server, which comes as a part of Dropwizard. Dropwizard takes a configuration based approach to set various module configurations for your application like logging, metrics, server details, database etc. 

This post digs into detail about the thread configuration for Jetty in Dropwizard, and some results and observations from experiments.

<!--more-->

An example server settings configuration file looks like this: 

{% include_code server_config.yml %}

Let us go through some important things in this configuration file, in a little more detail: 

*MinThreads* - The minimum number of threads created at the start of its application.    
*MaxThreads* - The maximum number of threads that can be created by the application. (This will be on an on demand basis. More of this later).   
*AcceptorThreads* - The threads that accept new connections from client. By default this will be set to numberOfCores/2.   
*SelectorThreads* - The threads that look after the activity on a socket. This includes checking the IO activity on the socket. By default this will be set to numberOfCores.    

Let me list out some of the experiments that I did with the thread configurations.
Note: I am running on a machine with 4 cores, and 8GB of RAM.    

## Experiment-1:

* minThread and maxThread set to 8.

{% codeblock  [setting only min and max threads]  %}
server:
  type: default
  maxThreads: 8
  minThreads: 8
{% endcodeblock %}

### Result:   

{% img /images/posts/threads_8.jpg %}

### Observation:
dw-[18..21] are selector threads, and dw[22..23] are acceptor threads. There are other threads that are in waiting state, and dedicated threads allocated for admin port. So, for each connector, the min and max thread rule applies, keeping in bound of the default selector and acceptor thread configuration values (which is dependent on the number of cores your machine has). In this configuration, since there are acceptor threads available, connections are being made and requests are served. 

## Experiment-2:

* Set both MinThread and MaxThread values, and also the acceptorThread and selectorThread values.

{% codeblock  [setting only min and max threads]  %}
server:
  type: default
  maxThreads: 8
  minThreads: 8
  applicationConnectors:
    - type: http
      port: 14041
      acceptorThreads: 8
      selectorThreads: 16
{% endcodeblock %}

### Result:   

{% img /images/posts/thread_blocker.png %}

### Observation: 

So, we see that selectorThreads take the preference, and dw-[18..25] are created first. Since our maxThread upper bound is set to 8, all the created 8 threads are only selector threads, we see that there are no acceptor threads created at all. We will not be able to establish any client connections and make calls, and any attempts to do so will block your client. 

## Experiment-3: 

* setting the minThread and maxThread to really big values.

{% codeblock  [large lower and upper bounds for threads]  %}
server:
  type: default
  maxThreads: 100
  minThreads: 8
  applicationConnectors:
    - type: http
      port: 14041
      acceptorThreads: 50
      selectorThreads: 50
{% endcodeblock %}

### Observation: 

Here, we have 50 acceptor and 50 selector threads created, as the configuration file says. The minThread configuration key is overridden by the acceptor/selector Thread configurations. 

##Summing it up: 

* Selector Threads will be initialized first, and then if your configuration allows for more, acceptor threads will be created. 
* Number of selector threads will be equal to number of cores of your system, and acceptor threads will be (number of cores)/2, unless otherwise specified.
* It is not recommended to hardwire acceptorThreads and selectorThread configuration values, unless you know what you are doing. 
* Rule of thumb is leave the thread configuration to default, and default max thread value is 1024. 
* The right number of acceptor threads is defined by the connection open/close rate. More the rate, more acceptors we want. 
* If the server is busy, (100K or more connections at a time), it is better to use more selectors to even out the connection load amongst selectors, each selector has a limit of 64k connections. 

##References: 

* [Dropwizard](http://dropwizard.io/)
* [Jetty](http://eclipse.org/jetty/)












