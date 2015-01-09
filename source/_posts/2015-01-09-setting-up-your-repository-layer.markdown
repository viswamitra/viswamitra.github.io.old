---
layout: post
title: "Setting up your Repository Layer"
date: 2015-01-09 02:28:38 +0530
comments: true
categories: jpa, java, enterprise
---
When writing a three tier enterprise application, you would always have to write a DAO or a Repository layer, which would interface with your transactional entities. Specifically in java enterprise world, when you are using ORMs, you would have to write repositories in JPA specification or use Hibernate directly. Many libraries have extensive support for both, but it is considered a general good practice to use JPA, as it is a specification and is vendor independent. After working on some similar enterprise applications, let me give you a brief gist of how I organize my repositories, so that the domain specific transactional methods are separated out of the ORM specific transactional methods. 

First let us set up a ORM independent repository interface, which defines the crud actions that we want our repositories to have.

{% gist 9186572abe4a370c06d6 GenericRepositoryIface.java %}


