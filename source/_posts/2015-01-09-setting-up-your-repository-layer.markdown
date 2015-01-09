---
layout: post
title: "Setting up your Repository Layer"
date: 2015-01-09 02:28:38 +0530
comments: true
categories: jpa, java, enterprise
---
When writing a three tier enterprise application, you would always have to write a DAO or a Repository layer, which would interface with your transactional entities. Specifically in java enterprise world, when you are using ORMs, you would have to write repositories in JPA specification or use Hibernate directly. Many libraries have extensive support for both, but it is considered a general good practice to use JPA, as it is a specification and is vendor independent. After working on some similar enterprise applications, let me give you a brief gist of how I organize my repositories, so that the domain specific transactional methods are separated out of the ORM specific transactional methods. 

First let us set up a ORM independent repository interface, which defines the crud actions that we want our repositories to have.

{% include_code GenericRepositoryIface.java %}

As we can see, this interface has the basic crud methods of our entities. Adding generics makes it.. generic :). 

Now, let us have a concrete implementation of this interface, which will implement these methods. I'm going to use JPA specification here. 

{% include_code GenericRepositoryImpl.java %}

This class takes an EntityManager(which can be either Provided at the start of the application, or injected) and implements the methods from the interface. 

Moving on, let us actually come to the domain specific repositories that actually deal with the domain Entities. As with the previous design, let us use interfaces to define the required methods, and have concrete implementations separately.

Let us have an AccountRepositoryInterface, which will find an account from the database, given necessary account details.

{% include_code AccountRepositoryIface.java %}

This interface contains one method - findAccount which searches based on orderItemId and accountId. Finally, let us have a concrete implementation of 
this interface. 

{% include_code AccountRepositoryImpl.java %}

As you can see, we have neatly abstracted interfaces and concrete implementations, in both the domain specific repositories as well as crud based repositories. This kind of alignment will help writing focused domain repositories without being concerned about the internal crud methods, since they are abstracted out.















