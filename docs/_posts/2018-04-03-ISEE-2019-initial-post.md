---
layout: post
title: "ISEE 2019 -- Basic Prototype"
date: 2019-05-14
---

# Basic Prototype

## Application Name & Logo

![Deadline image]({{site.baseurl}}/images/logocover.png "Application Logo"){:height="50%" width="100%"}

## **Overview**

<p style="font-family:Times;font-size:120%;text-align:justify"> As we have successfully completed our first milestone presentation, now we move on to the next phase in our application development. This phase showcases our team effort in progressing with how we have understood the requirement from the product owner, the problems we have encountered while developing our application.</p>


## **Requirement: Track Expenses**

<ul style="font-family:Times;font-size:120%;text-align:justify">
<li>Each and every expenses must be monitored and categorized into areas so as to exactly know where the money has been spent.</li>
<li>Category field must be a dropdown. It must contain many areas (values) of spending which are generic to everyone.</li>
<li>The user must be given an option to add a category.</li>
<li>The end user must be able to enter the date of expense.</li>
<li>The end user must be given an option to select the mode of payment. I.e., Card Payment or Cash Payment which must also be a dropdown.</li>
<li>The end user must be given a free text box so that he is able to add comments to that particular expense.</li>
<li>Enter Income/Expense and Enter Amount fields must have only numeric keypad as input.</li>
<li>The user must be able to track recurring expenses as few payments are recurring every month. The field should be a checkbox.</li>
</ul>

## Requirement: Settings Page to Manage Profile
<ul style="font-family:Times;font-size:120%;text-align:justify">
  <li>The user must need a pin login for authorization and security purposes.</li>
  <li>The user must be able to set the pin, the first time when he opens the application.</li>
  <li>The user must be able to customize categories.</li>
  <li>The user must need to select a currency.</li>
 </ul>
 
## Requirement: Graphical View Page
 <ul style="font-family:Times;font-size:120%;text-align:justify">
  <li>Filter option must be enabled on category field.</li>
  <li>Start Date and End date must be given to filter the expenses according to the date and be represented in a graphical way.</li>
  <li>The user must filter his/her expenses with respect to the amount.</li>
  <li>Filter option must be enabled to the payment option to view the expenses collectively on the mode of payment.</li>
</ul>

## User Stories

![Deadline image]({{site.baseurl}}/images/Userstories2.png "User Stories"){:height="50%" width="100%"}

## Estimated Time

![Deadline image]({{site.baseurl}}/images/Estimatedtime.png "Estimated Time"){:height="50%" width="50%" align="center"}

## Use Case Diagram

![Deadline image]({{site.baseurl}}/images/Usecase.png "Use Case"){:height="50%" width="100%"}

## Gathering Requirements and Analysis

<p style="font-family:Times;font-size:120%;text-align:justify">Requirements being the first and important step towards a software development. We basically categorized them in to three parts as to how we have progressed.</p>

<ul style="font-family:Times;font-size:120%;text-align:justify">
  <li>Requirement Input: We held weekly meetings with the product owner and the stakeholders to understand what needs to be built from their perspective. All the requirements are ambiguously taken and various parameters pertaining the requirements are agreed.</li>
 <li>Requirement Analysis: Based on the discussed requirement, we have seen the feasibility from a technical perspective and understood if there are any technical limitation due to which the particular requirement could not implemented.</li>
 <li>Requirement Documentation: We have documented the discussed requirements. </li>
</ul>

## Wrong Assumptions
<ul style="font-family:Times;font-size:120%;text-align:justify">
 <li>We mistook the application as a multi user interface. On further discussions with the stakeholders, we understood that the application is a single-user interface.</li>
 <li>We assumed, we had to set the default currency instead of setting the currency.</li>
</ul>


## System Design Implementation

<p style="font-family:Times;font-size:120%;text-align:justify">We have designed the following as part of our application development.</p>
<ul style="font-family:Times;font-size:120%;text-align:justify">
  <li>Use Case Diagram</li>
  <li>Activity Diagram</li>
  <li>Class Diagram</li>
 </ul>
      
## Class Diagram

![Deadline image]({{site.baseurl}}/images/classdiagram.png "Class Diagram"){:height="50%" width="100%"}

![Deadline image]({{site.baseurl}}/images/C1.png "C1"){:height="50%" width="100%"}

![Deadline image]({{site.baseurl}}/images/C2.png "C2"){:height="50%" width="100%"}

## Activity Diagram

### Input and View Page

This diagram contains the flow of process from user entering the pin and sequentially to other processes.

![Deadline image]({{site.baseurl}}/images/Activity2.png "User Stories"){:height="50%" width="100%"}

### Settings Page

This diagrams contains the flow pof process in which the user can change the pin and customise the categories and also set the currency type.

![Deadline image]({{site.baseurl}}/images/ActivityDiagram2.png "User Stories"){:height="50%" width="100%"}

## Development Strategy

<ul style="font-family:Times;font-size:120%;text-align:justify">
<li>Requirements and user stories - to be 120% sure on the requirement.</li>
<li>Design, Class diagram - while designing it was difficult to identify the exact ones, so we decided not to worry much and go ahead with the coding. We updated the diagram once the coding is 90% completed.</li>
<li>Queries and Roadblocks - How to get it clarified as quick as possible.</li>
<li>Coding - Concentrated on the functionalities rather than UI</li>
<li>How to work in parallel – management on individuals activity was tracked in Trello. We assigned user stories among individual members.</li>
<li>How to merge code - In every page, each and every one worked on different functionalities and integrated the code.</li>
<li>Software comparability - Initial checks - Android Studio Issues - For couple of us couldn‘t connect and run the code in the emulator.</li>
 </ul>

![Deadline image]({{site.baseurl}}/images/Trello2.png "Trello"){:height="50%" width="100%"}

## Working Prototype

<p style="font-family:Times;font-size:120%;text-align:justify"> Click below link to download APK file.</p>
<p style="font-family:Times;font-size:120%;text-align:justify"><a href="https://github.com/DBSE-teaching/isee2019-Sky-Net/blob/Application/APK/app-debug.apk?raw=true">ExSpendables.apk</a></p>

<p style="font-family:Times;font-size:120%;text-align:justify">Authentication Screen &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HomePage &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Date field </p>
![Deadline image]({{site.baseurl}}/images/App1.png "Img 1, Img 2, Img 3"){:height="50%" width="100%"}

<p style="font-family:Times;font-size:120%;text-align:justify">Settings Page &&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Currency Selection &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Transaction Details </p>
![Deadline image]({{site.baseurl}}/images/App2.png "Img 4, Img 5, Img 6"){:height="50%" width="100%"}
