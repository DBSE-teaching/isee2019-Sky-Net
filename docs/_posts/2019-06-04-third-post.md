---
layout: post
title: "ISEE 2019 -- Advanced Prototype"
date: 2019-06-04
---

# Advanced Prototype

## Overview

<p style="font-family:Times;font-size:110%;text-align:justify">After our successful implementation of the Basic Prototype, we have moved on to the next phase in our application development. In this phase, we have received further requirements from the Product Owner. The given set of requirements are a must have and also this includes some alterations to be done over the previously implemented prototype. We have adapted ourselves to these dynamic requirements and modified the code accordingly.</p>

## Design Pattern

<p style="font-family:Times;font-size:110%;text-align:justify">Design patterns  for software development was first proposed by <b>“Gang of Four” </b>in their book <b>“Design Patterns: Elements of Reusable Object-Oriented Software”</b> . Patterns were majorly classified into 3 broad categories.</p>
  
<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Creational Pattern</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Behavioral Pattern</p></li> 
  <li><p style="font-family:Times;font-size:110%;text-align:justify">Structural Pattern</p></li></ul>
  
<p style="font-family:Times;font-size:110%;text-align:justify">Many patterns were later formulated by developers based on the patterns proposed by the “Gang of Four”.These patterns provide a solution for solving recurring issues related to common software development problems. We have used majorly two design patterns throughout our development. They are the Composite Design Pattern and Model-View- Controller.</p>

### Composite Design Pattern

<p style="font-family:Times;font-size:110%;text-align:justify">This pattern comes under the structural design  pattern as this forms a group of objects into a  tree like structure. This ensures us an effective way of using the created objects of various classes.</p>

![Deadline image]({{site.baseurl}}/images/cdp.png "Composite Design Pattern"){:height="50%" width="50%" align="centre"} 

### Model View Controller

<p style="font-family:Times;font-size:110%;text-align:justify">This is by far the most used design pattern in software development. It is developed as an evolution  to the design patterns proposed by the “Gang of Four”.It is often referred an architectural pattern. As the name suggests it involves three components,which are namely Model,View and Controller.</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Model</b> : This consist of the data and set of rules for the application to work on.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>View</b> : This often referred to as the center part of the model.This pattern aids the user in viewing graphs ,tables and form view.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Controller</b> : This interprets the user  commands to the model and aids in performing desired action.</p></li></ul>

![Deadline image]({{site.baseurl}}/images/mvc.png "Model View Controller"){:height="50%" width="50%" ali"centre"}

### Reasons  For Choosing  Model-View-controller
<p style="font-family:Times;font-size:110%;text-align:justify">The application involves an end user interacting through input actions such as clicks,selection, etc.to process the backend data and the keeping track of money control. Purpose the application is more aligned with the MVC pattern where the model forms the backend data,  forms the user actions and view forms the visual representations 

For eg, on click of submit button(controller) in transaction page the data entered by the user in various fields( view) the data gets saved to the database table TRANSACTIONS(model).</p>

![Deadline image]({{site.baseurl}}/images/mvcprocess.png "MVC-PROCESS"){:height="50%" width="50%" align="centre"} 



## Implementation & User Interface

### Coding Conventions

<p style="font-family:Times;font-size:110%;text-align:justify">Coding convention helps both the developer and other third party organisations to have better understanding and improved readability of the available codes.These are also termed as Naming Conventions.We have used standard Java coding conventions for this project the are listed as below:</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>CamelCaseNotation</b> -This convention is used in naming  functions,variables,class names.</p></li></ul>
  
![Deadline image]({{site.baseurl}}/images/camelcase.png "Camel Case"){:height="50%" width="50%"}

<ul>
   <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>lower_case Notation</b> - This convention is used in all the imports,and naming XML pages.</p></li></ul>
   
![Deadline image]({{site.baseurl}}/images/lowercase.png "Lower Case"){:height="15%" width="50%"}

<ul>
   <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Own conventions</b> - we used “_” in naming XML pages and Android.id of the XML pages.This convention is utilised when the element name is longer</p></li></ul>
    
![Deadline image]({{site.baseurl}}/images/ownconvention.png "Own Convention"){:height="30%" width="50%"}

### Enforcing Coding Conventions 

<p style="font-family:Times;font-size:110%;text-align:justify">The defined conventions were documented and a detailed report containing usage of the conventions was circulated and explained in detail among team members. This made sure that the whole project had a unified naming convention. Additionally, the conventions were ensured to be implemented while proofread while performing stash and commit operations.</p>

### Context of Use

<p style="font-family:Times;font-size:110%;text-align:justify">This process is to identify the target audience of our application. Two personas were created based on different demographics ranging from geographic location, age, sex, marital status, income. This process enables us to find the wants and needs of the users.The below images are from two different users perspective on why exactly they need a money tracker application.</p>

![Deadline image]({{site.baseurl}}/images/Personas.png "Personas"){:height="50%" width="100%"}

### Design Solution

<p style="font-family:Times;font-size:110%;text-align:justify">Our design solution is to provide the user with all the essential and required functionalities with simple and basic user interface</p>

<p style="font-family:Times;font-size:110%;text-align:justify"><b>Navigation and User Interface:</b>
- The basic user interface makes user life easy to easily navigate across screens. The back button is provided in all the screens, which allows the user to navigate to the previous form without any hassles</p>

<p style="font-family:Times;font-size:110%;text-align:justify"><b>User Security:</b>
- The user security is provided with an option to enable or disable pin. So, whenever the app is opened, the pin verification is done</p>

<p style="font-family:Times;font-size:110%;text-align:justify"><b>User errors handling:</b>
- The error messages are displayed to highlight the user with the errors. The mandatory fields are highlighted, to let the user know about the required details which needs to be entered</p>

<p style="font-family:Times;font-size:110%;text-align:justify"><b>Acknowledging the user:</b>
- The acknowledgement messages are displayed to let the user know that, the details entered are saved or modified or deleted</p>


<p style="font-family:Times;font-size:110%;text-align:justify">The below diagram depicts the Epic 4 User Story 2 from the given set of requirements. The sequence of screen shows where the user navigates to the 'Settings' screen from the Home Screen. After navigating to the 'Settings' screen, there is a button "Maximum Budget" which is used to set a threshold value to particular category. There is a push notification enabled for every amount entered in to the category for which a threshold value is set.</p>
 

![Deadline image]({{site.baseurl}}/images/Epic4_Story2.png "Epic4 Story 2"){:height="50%" width="100%"}

<p style="font-family:Times;font-size:110%;text-align:justify"> The below diagram depicts the Epic 4 User Story 3 from the given set of requirements. The sequence of screen shows where the user navigates to the 'View Summary' screen from the Home Screen. On landing in to the 'View Summary' screen, the user gets to enter a date range for which the transaction saved during that period will be displayed. Individual records can be deleted by selecting the check box and clicking on the delete button. </p>

![Deadline image]({{site.baseurl}}/images/Epic4_Story3.png "Epic4 Story 3"){:height="50%" width="100%"}


## Summary of Changes

<p style="font-family:Times;font-size:110%;text-align:justify">The following are the changes that are added or modified as part of our advanced prototype.</p>

<ul>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Application User Interface</b>: The look and feel of the application have been changed, with few alignments modified for all the fields throughout the application.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Authentication/Log in</b>: Our basic prototype had the user to set the pin mandatory and every time the user logs in. But, now in the Advanced Prototype, the user is given the option to enable or disable pin in the Pin Settings.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Maximum Budget</b>: A new button has been incorporated in the Home Screen to set a threshold value for individual categories.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Push Notifications</b>: When the user enters an amount for a particular category and the maximum budget for that category has been set, the user will receive a pushdown notification for every transaction that occurs to that category.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>View Summary</b>: Overview or history of a transaction is now displayed in a tabular format. It is filtered on the basis of Date Range. The user is given an option to delete the transaction recorded.</p></li>
<li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Graph Summary</b>:  For all the transactions recorded, there is a graphical representation implemented on Date and Category filter basis.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Email</b>: The user is now able to send an email with the saved transaction details.</p></li></ul>
  
### User Story - Basic prototype 

![Deadline image]({{site.baseurl}}/images/olduserstories.png "Old User Stories"){:height="50%" width="100%"}

### User Story - Advanced prototype

![Deadline image]({{site.baseurl}}/images/newuserstories.png "New User Stories"){:height="50%" width="100%"}

### Updated Class diagram

![Deadline image]({{site.baseurl}}/images/classdiagramnew.png "class diagram new"){:height="50%" width="100%"}



## Working Prototype

<p style="font-family:Times;font-size:110%;text-align:justify"> Click below link to download APK file.</p>
<p style="font-family:Times;font-size:110%;text-align:justify"><a href="https://github.com/DBSE-teaching/isee2019-Sky-Net/blob/Application/AdvancedPrototype/AdvancedPrototype.apk?raw=true" style="color: rgb(0,0,255)">ExSpendables.apk</a></p>

<p style="font-family:Times;font-size:120%;text-align:justify"> &nbsp;&nbsp;&nbsp;Home Screen &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enable/Disable Pin &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date Selection &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Category dropdown </p>
![Deadline image]({{site.baseurl}}/images/AdvancedPrototype1.png "Img 1, Img 2, Img 3, Img 4"){:height="50%" width="100%"}

<p style="font-family:Times;font-size:120%;text-align:justify"> &nbsp;&nbsp;&nbsp;Category Icon &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View Summary filter &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; View Graph by Category &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Pie Chart </p>
![Deadline image]({{site.baseurl}}/images/AdvancedPrototype2.png "Img 5, Img 6, Img 7, Img 8"){:height="50%" width="100%"}

<p style="font-family:Times;font-size:160%;text-align:justify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thanks for visiting our Blog!!</p>

### Resourses 

<p style="font-family:Times;font-size:110%;text-align:justify"><a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller#/media/File:MVC-Process.svg" style="color: rgb(0,0,255)">https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller#/media/File:MVC-Process.svg</a></p>


