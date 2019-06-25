---
layout: page
title: "ISEE 2019 -- Beta Prototype"
date: 2019-06-25
---

# Beta Prototype

## Overview

<p style="font-family:Times;font-size:110%;text-align:justify">We have reached the beginning of the end of the development of our application. Most of the user stories were implemented into working prototypes. Thus we have reached a stage where the application needs to be tested for the users to have an unparalleled and effortless experience. For this purpose, we have made use of the two existing testing methods, namely Black box testing and White box testing.  In this blog, we have explained these methods in detail.
</p>

## General Test Process

<p style="font-family:Times;font-size:110%;text-align:justify">As far as our testing is concerned, we have followed the Condition Coverage Technique and Branch Coverage Technique for the White Box technique. Whereas, we have followed the equivalence partitioning technique for Black Box testing.</p>

## Testing Techniques

<p style="font-family:Times;font-size:110%;text-align:justify">Major testing techniques which are available at our disposal are the following: </p>
  
<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Unit Testing</b> - Testing is carried out by the developer after the completion of a module or functionality.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>System Testing</b> - Testing is carried out by a test analyst only for the module or functionality</p></li> 
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>System Integration Testing</b>- Testing is carried out by a test analyst after all the modules are integrated.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Acceptance Testing</b> - Testing is carried out by a user for the entire functionality of the application or module.</p></li></ul>
  
  
<p style="font-family:Times;font-size:110%;text-align:justify">Here unit testing can be classified under White box testing and other testing techniques(system testing, system integration testing, acceptance testing)can be classified under black box testing.</p>
  
  
## White box Testing

![Deadline image]({{site.baseurl}}/images/whiteboxflow.PNG "Img 1"){:height="80%" width="100%" align="centre"}

<p style="font-family:Times;font-size:110%;text-align:justify">This method of testing is from the developers' point of view. It enables him to check the flow of control of the line of codes through various loops and code structures. For the developers to use this method, they can follow these two simple steps-</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">First to get the complete working knowledge of the written code. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify">To select the most suitable coverage technique, applicable to the written code.</p></li></ul>


### White Box Testing Techniques  

<p style="font-family:Times;font-size:110%;text-align:justify">There exist many coverage techniques for the developers to scrutinize blocks of written codes. They are namely -</p>

<ul>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Statement Coverage</b> - Tests each statement at least once. The main idea is to cover all. executable statements inside the given block of code and check them with test cases. </p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Function coverage</b> -Tests each function at least once.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Edge coverage</b> -Test every edge in the control flow graph in the program at least once</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Branch coverage</b> -Test every control statement in the program at least once.</p></li>
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Condition coverage</b> - Test every Boolean sub-expression whether evaluated to both true and false.</p></li></ul>
  
<p style="font-family:Times;font-size:110%;text-align:justify">The following classes were tested using White Box Testing : </p>

![Deadline image]({{site.baseurl}}/images/WBCValidation.PNG "Img 1"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/DateRangeWBC.PNG "Img 2"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/onlogintest.PNG "Img 4"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/onsetcurrencytest.PNG "Img 5"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/sendmailtest.PNG "Img 6"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/AddCurrencyWBC.PNG "Img 7"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/DeleteTransactions.PNG "Img 8"){:height="80%" width="100%" align="centre"}

![Deadline image]({{site.baseurl}}/images/GetIconWBC.PNG "Img 9"){:height="80%" width="100%" align="centre"}

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
  <li><p style="font-family:Times;font-size:110%;text-align:justify"><b>Boundary Value Analysis</b> - It is a test design technique that involves the determination of boundaries for input values and selecting values that are at the boundaries and just inside/ outside of the boundaries as test data.</p></li>
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
  

<p style="font-family:Times;font-size:160%;text-align:justify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thanks for visiting our Blog!!</p>

### Resources 

<p style="font-family:Times;font-size:110%;text-align:justify"> Figure 1 <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller#/media/File:MVC-Process.svg" style="color: rgb(0,0,255)">https://en.wikipedia.org/wiki/Black_box#/media/File:Blackbox.svg</a></p>







