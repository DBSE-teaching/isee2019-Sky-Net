---
layout: post
title: "ISEE 2019 -- Advanced Prototype"
date: 2019-06-04
---

# Advanced Prototype

## Overview

<p style="font-family:Times;font-size:110%;text-align:justify">After our successfull implementation of the Basic Prototype, we now have move on to the next phase in our application developement. In this phase, we have received advanced requirements from the Product Owner. The given set of requirements are a must have and also this includes some alterations and changes to be done from the previously implemented prototype. We have adapted ourselves to dynamic requirements and modifying the code accordingly.</p>

## Design Pattern

<p style="font-family:Times;font-size:110%;text-align:justify">Design patterns  for software development was first proposed by “Gang of Four” in their book “Design Patterns: Elements of Reusable Object-Oriented Software” . Patterns were majorly classified into 3 broad categories.</p>
  
<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Creational Pattern</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Behavioral Pattern</p></li> 
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Structural Pattern</p></li></ul>
  
<p style="font-family:Times;font-size:110%;text-align:justify">Many patterns were later formulated by developers based on the patterns proposed by the “Gang of Four”.These patterns provides solution in solving recurring issues related to common software development problems.We have used majorly two design patterns throughout our development. They are Composite Design Pattern and Model View Controller.</p>

### Composite Design Pattern

<p style="font-family:Times;font-size:110%;text-align:justify">This pattern comes under the structural design  pattern as this forms a group of objects into a  tree like structure. This ensures us an effective way of using the the created objects of various classes.</p>

### Model View Controller

<p style="font-family:Times;font-size:110%;text-align:justify">This is by far the most used design pattern in software development. It is developed as an evolution  to the design patterns proposed by the “Gang of Four”.It is often referred an architectural pattern. As the name suggests it involves three components,which are namely Model,View and Controller.</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Model</b> : This consist of the data and set of rules for the application to work on.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>View</b> : This often referred to as the center part of the model.This pattern aids the user in viewing graphs ,tables and form view.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Controller</b> : This interprets the user  commands to the model and aids in performing desired action.</p></li></ul>



## Implementation & User Interface

### Coding Conventions

### Context of Use

![Deadline image]({{site.baseurl}}/images/Personas.png "Personas"){:height="50%" width="100%"}

### Design Solution

<p style="font-family:Times;font-size:110%;text-align:justify"> The below diagram depicts the Epic 4 User Story 2 from the given set of requirements. The sequence of screen shows where the user navigates to the Settings screen from the Home Screen. On landing in to the Setting screen, there is a button "Maximum Budget" which is used to set a Threshold value to particular Category. There is a push notification enabled for every amount entered in to the Category for which a threshold value is set. </p>

![Deadline image]({{site.baseurl}}/images/Epic4_Story2.png "Epic4 Story 2"){:height="50%" width="100%"}

<p style="font-family:Times;font-size:110%;text-align:justify"> The below diagram depicts the Epic 4 User Story 3 from the given set of requirements. The sequence of screen shows where the user navigates to the View Summary screen from the Home Screen. On landing in to the View Summary screen, the user gets to enter a date range for which the transaction saved during that period will be displayed. Individual records can be deleted by selecting the check box and clicking on the delete button. </p>

![Deadline image]({{site.baseurl}}/images/Epic4_Story3.png "Epic4 Story 3"){:height="50%" width="100%"}


## Summary of Changes

<p style="font-family:Times;font-size:110%;text-align:justify">The following are the changes that are added or modified as part of our advanced prototype.</p>

<ul>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Application User Interface: The look and feel of the application have been changed, with few alignments modified for all the fields throughout the application.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Authentication/Log in: Our basic prototype had the user to set the pin mandatory and every time the user logs in. But, now in the Advanced Prototype, the user is given the option to enable or disable pin in the Pin Settings.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Maximum Budget: A new button has been incorporated in the Home Screen to set a threshold value for individual categories.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Push Notifications: When the user enters an amount for a particular category and the maximum budget for that category has been set, the user will receive a pushdown notification for every transaction that occurs to that category.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">View Summary: Overview or history of a transaction is now displayed in a tabular format. It is filtered on the basis of Date Range. The user is given an option to delete the transaction recorded.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Graph Summary:  For all the transactions recorded, there is a graphical representation implemented on Date and Category filter basis.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify">Email: The user is now able to send an email with the saved transaction details.</p></li></ul>

### Before:
![Deadline image]({{site.baseurl}}/images/olduserstories.png "Old User Stories"){:height="50%" width="100%"}

### After:
![Deadline image]({{site.baseurl}}/images/newuserstories.png "New User Stories"){:height="50%" width="100%"}

## Working Prototype
