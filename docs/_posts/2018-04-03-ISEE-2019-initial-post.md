---
layout: post
title: "ISEE 2019 -- Basic Prototype"
date: 2019-05-14
---

# Basic Prototype

# Application Name & Logo

# Overview

As we have succesfully completed our first milestone presentation, now we move on to the next phase in our application development.
This phase showcases our team effort in progressing with how we have understood the requirement from the product owner, the problems we have encountered while developing our application.

# Requirements

## Requirement: Track Expenses
<ul class ="bigger">
  <li>Each and every expenses must be monitored and categorized into areas so as to exactly know where the money has been spent.</li>
  <li>Category field must be a dropdown. It must contain many areas (values) of spending which are generic to everyone. </li>
  <li>The user must be given an option to add a category.</li>
  <li>The end user must be able to enter the date of expense.</li>
  <li>The end user must be given an option to select the mode of payment. I.e., Card Payment or Cash Payment which must also be a dropdown.</li>
  <li>The end user must be given a free text box so that he is able to add comments to that particular expense.</li>
  <li>Enter Income/Expense and Enter Amount fields must have only numeric keypad as input.</li>
  <li>The user must be able to track recurring expenses as few payments are recurring every month. The field should be a checkbox.</li>
</ul>

## Requirement: Settings Page to Manage Profile
<ul>
  <li>The user must need a pin login for authorization and security purposes.</li>
  <li>The user must be able to set the pin, the first time when he opens the application.</li>
  <li>The user must be able to customize categories.</li>
  <li>The user must need to select a currency.</li>
 </ul>
 
## Requirement: Graphical View Page
 <ul>
  <li>Filter option must be enabled on category field.</li>
  <li>Start Date and End date must be given to filter the expenses according to the date and be represented in a graphical way.</li>
  <li>The user must filter his/her expenses with respect to the amount.</li>
  <li>Filter option must be enabled to the payment option to view the expenses collectively on the mode of payment.</li>
</ul>

# User Stories

![Deadline image]({{site.baseurl}}/images/Userstories2.png "User Stories"){:height="50%" width="100%"}

# Estimated Time

![Deadline image]({{site.baseurl}}/images/EstimatedTime.png "Estimated Time"){:height="50%" width="100%"}

# Use Case Diagram

![Deadline image]({{site.baseurl}}/images/Usecase.png "User Stories"){:height="50%" width="100%"}

# Gathering Requirements and Analysis

Requirements being the first and important step towards a software development. We basically categorized them in to three parts as to how we have progressed.

<ul>
  <li>Requirement Input: We held weekly meetings with the product owner and the stakeholders to understand what needs to be built from their perspective. All the requirements are ambiguously taken and various parameters pertaining the requirements are agreed.</li>
 <li>Requirement Analysis: Based on the discussed requirement, we have seen the feasibility from a technical perspective and understood if there are any technical limitation due to which the particular requirement could not implemented.</li>
 <li>Requirement Documentation: We have documented the discussed requirements. </li>
</ul>

# Wrong Assumptions
<ul>
 <li>We mistook the application as a multi user interface. On further discussions with the stakeholders, we understood that the application is a single-user interface.</li>
 <li>We assumed, we had to set the default currency instead of setting the currency.</li>
</ul>


## System Design Implementation

We have designed the following as part of our application development.
      ---> Use Case Diagram
      ---> Activity Diagram
      ---> Class Diagram
      ---> Behavioural Diagram



### Activity Diagram

#### Input and View Page

![Deadline image]({{site.baseurl}}/images/ActivityDiagram1.png "User Stories"){:height="50%" width="100%"}

#### Settings Page

![Deadline image]({{site.baseurl}}/images/ActivityDiagram2.png "User Stories"){:height="50%" width="100%"}

### Class Diagram

![Deadline image]({{site.baseurl}}/images/Classdiagram.png "User Stories"){:height="50%" width="100%"}

### Behavioural Diagram

![Deadline image]({{site.baseurl}}/images/Behaviouraldiagram.png "User Stories"){:height="50%" width="100%"}
