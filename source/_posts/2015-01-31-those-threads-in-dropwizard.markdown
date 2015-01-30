---
layout: post
title: "Those threads in Dropwizard"
date: 2015-01-31 02:18:19 +0530
comments: true
categories: [java, dropwizard, jetty, concurrency]
---

We use Dropwizard framework for writing web applications at work, as it is nifty, out of the box, and contains all my 
favorite libraries. While this post is not about Dropwizard framework in specific, I just want to share some interesting insights into what I have learnt while configuring threads for Jetty, which comes as a part of Dropwizard. 

So, Jetty is a HTTP server, which is available straight out in Dropwizard. Dropwizard takes a configuration approach to set various module configurations for your application like logging, metrics, server details, database etc. This is you can fine tune your Jetty server settings. 

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

### Results:   

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















