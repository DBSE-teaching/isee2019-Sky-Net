---
layout: page
title: "ISEE 2019 -- Beta Prototype"
date: 2019-06-25
---

# Beta Prototype

## Overview

<p style="font-family:Times;font-size:110%;text-align:justify">We have reached the beginning of the end of our application development. All the user stories were implemented into working prototypes. Thus, we have reached a stage where the application needs to be tested for the users to have an effortless and unparalleled experience. We have used different testing methods and these are explained in detail in this blog.
</p>

## General Test Process

<p style="font-family:Times;font-size:110%;text-align:justify">As far as our testing is concerned, we have followed Condition Coverage Technique and Branch Coverage Technique for the White Box testing and for Black Box testing, we have followed the equivalence partitioning technique.</p>

## Testing Techniques

<p style="font-family:Times;font-size:110%;text-align:justify">Major testing techniques which are available at our disposal are the following: </p>
  
<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Unit Testing</b> - Testing is carried out by the developer after the completion of a module or functionality.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>System Testing</b> - Testing is carried out by a test analyst only for the module or functionality</p></li> 
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>System Integration Testing</b>- Testing is carried out by a test analyst after all the modules are integrated.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Acceptance Testing</b> - Testing is carried out by a user for the entire functionality of the application or module.</p></li></ul>
  
  
<p style="font-family:Times;font-size:110%;text-align:justify">Here, unit testing is classified under White box testing and other testing techniques(system testing, system integration testing, acceptance testing)are classified under black box testing.</p>
  
  
## White box Testing

![Deadline image]({{site.baseurl}}/images/whiteboxflow.PNG "Img 1"){:height="80%" width="80%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify">This method of testing is from the developers' point of view. It enables the developer to check the flow of control of the line of codes through various loops and code structures. For the developers to use this method, they can follow the below two simple steps-</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">First to get the complete working knowledge of the written code. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">To select the most suitable coverage technique, that is applicable to the written code.</p></li></ul>


### White Box Testing Techniques  

<p style="font-family:Times;font-size:110%;text-align:justify">There exist many coverage techniques for the developers to scrutinize blocks of written codes. They are namely -</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Statement Coverage</b> - Tests each statement at least once. The main idea is to cover all executable statements inside the given block of code and check them with test cases. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Function coverage</b> -Tests each function at least once.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Edge coverage</b> -Tests every edge in the control flow graph in the program at least once</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Branch coverage</b> -Tests every control statement in the program at least once.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Condition coverage</b> - Tests every boolean sub-expression whether evaluated to both true and false.</p></li></ul>
  
<p style="font-family:Times;font-size:110%;text-align:justify">The following classes were tested using White Box Testing : </p>

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Field validation while saving transactions</b> </p>

![Deadline image]({{site.baseurl}}/images/WBCValidation.PNG "Img 1"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b> Field Validation while filtering transactions </b></p>

![Deadline image]({{site.baseurl}}/images/DateRangeWBC.PNG "Img 2"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Prompting user to authenticate PIN</b> </p>

![Deadline image]({{site.baseurl}}/images/onlogintest.PNG "Img 4"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>When user selects currency unit</b> </p>

![Deadline image]({{site.baseurl}}/images/onsetcurrencytest.PNG "Img 5"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Send transaction details via E-mail</b> </p>

![Deadline image]({{site.baseurl}}/images/sendmailtest.PNG "Img 6"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Save Currency Unit</b> </p>

![Deadline image]({{site.baseurl}}/images/AddCurrencyWBC.PNG "Img 7"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Delete transactions</b> </p>

![Deadline image]({{site.baseurl}}/images/DeleteTransactions.PNG "Img 8"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Display Icons and Categories in Transactions page</b> </p>

![Deadline image]({{site.baseurl}}/images/GetIconWBC.PNG "Img 9"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify"> <b>Summary of Classes - Functions that are tested by White Box Testing</b></p>

![Deadline image]({{site.baseurl}}/images/WBCtestcases.PNG "Img 10"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify">The methods which are of lower significance and less interdependency were not tested. </p>


## Black Box Testing

<p style="font-family:Times;font-size:110%;text-align:justify">Black Box Testing is a testing technique in which the functionality of the application is tested without knowing the internal code or the implementation structure. The tests can be functional or non-functional, but mostly functional. </p>

<p style="font-family:Times;font-size:110%;text-align:justify">Figure 1 </p>

![Deadline image]({{site.baseurl}}/images/Blackbox.svg "Img 1"){:height="80%" width="100%" align="centre"}

### Types of Black Box testing

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Functional Testing</b> - Usually performed by Test Analysts to check the functionality of the application based on the requirements received. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Non-Functional Testing</b> - This testing focuses on the performance, usability, and scalability of the application.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Regression Testing</b> - Testing done immediately after making any changes to the source code whether to know if all the earlier working functionalities are in place and has no impact on any of the functionalities done earlier.</p></li></ul>


### Black box testing techniques 

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Equivalence Partitioning</b> - It is a test design technique that involves dividing input valid and invalid partitions and selecting particular values from each partition as test data. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Boundary Value Analysis</b> - It is a test design technique that involves the determination of boundaries for input values and selecting values that are at the boundaries and just inside/outside of the boundaries as test data.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Cause-Effect Graphing</b> - It is a test design technique that involves identifying the case and effects producing a Cause-Effect Graph.</p></li></ul>
  
### Test Scenario  
  
![Deadline image]({{site.baseurl}}/images/Testscenario.PNG "Test Scenario"){:height="80%" width="100%" align="centre"}

### Test Cases

![Deadline image]({{site.baseurl}}/images/Testcase1.PNG "Test Case 1"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/Testcase2.PNG "Test Case 2"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/Testcase3.PNG "Test Case 3"){:height="80%" width="100%" align="centre"} 

![Deadline image]({{site.baseurl}}/images/Testcase4.PNG "Test Case 4"){:height="80%" width="100%" align="centre"} 

![Deadline image]({{site.baseurl}}/images/Testcase5.PNG "Test Case 5"){:height="80%" width="100%" align="centre"} 

![Deadline image]({{site.baseurl}}/images/Testcase6.PNG "Test Case 6"){:height="80%" width="100%" align="centre"} 

![Deadline image]({{site.baseurl}}/images/Testcase7.PNG "Test Case 7"){:height="80%" width="100%" align="centre"} 

### Status & Defect

![Deadline image]({{site.baseurl}}/images/Statusanddefects.PNG "Status & Defects"){:height="80%" width="100%" align="centre"}

## Summary of Changes

![Deadline image]({{site.baseurl}}/images/Beta2.PNG "Img 1"){:height="50%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/Beta1.PNG "Img 2"){:height="50%" width="100%" align="centre"}

### Navigation Drawer

<p style="font-family:Times;font-size:110%;text-align:justify">The navigation drawer is added and incorporated the new changes with the existing code to provide the user with the ease of navigation.</p>

### View Summary and Graph Summary

<p style="font-family:Times;font-size:110%;text-align:justify">The existing layout is modified and now displaying all the filters in the layout, so that the user is not restricted with only few available options and can filter the transactions and generate charts with all the possible combinations.</p>

### Expense and Income Layout

<p style="font-family:Times;font-size:110%;text-align:justify">The income layout is removed and expense layout is configured for both income and expense transactions.</p>

### Cosmetic Changes

<p style="font-family:Times;font-size:110%;text-align:justify">The category icon is added along with the category list in the transactions layout and the calendar button is replaced with the icon. </p>

  

<p style="font-family:Times;font-size:160%;text-align:justify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thanks for visiting our Blog!!</p>

### Resources 

<p style="font-family:Times;font-size:110%;text-align:justify"> Figure 1 : <a href="https://en.wikipedia.org/wiki/Black-box_testing#/media/File:Blackbox.svg" style="color: rgb(0,0,255)">https://en.wikipedia.org/wiki/Black_box#/media/File:Blackbox.svg</a></p>







