# README

This is the repository template used for student repositories in LAPR Projets.

## Java source files

Java source and test files are located in folder src.

## Maven file

Pom.xml file controls the project build.

# Notes
In this file, DO NOT EDIT the following elements:

* groupID
* artifactID
* version
* properties

Beside, students can only add dependencies to the specified section of this file.

## Eclipse files

The following files are solely used by Eclipse IDE:

* .classpath
* .project

## IntelliJ Idea IDE files

The following folder is solely used by Intellij Idea IDE :

* .idea

# How was the .gitignore file generated?
.gitignore file was generated based on https://www.gitignore.io/ with the following keywords:

  - Java
  - Maven
  - Eclipse
  - NetBeans
  - Intellij

# Who do I talk to?
In case you have any problem, please email Nuno Bettencourt (nmb@isep.ipp.pt).

# How do I use Maven?

## How to run unit tests?

Execute the "test" goals.

`$ mvn test`

## How to generate the javadoc for source code?

Execute the "javadoc:javadoc" goal.

`$ mvn javadoc:javadoc`

This generates the source code javadoc in folder "target/site/apidocs/index.html".

## How to generate the javadoc for test cases code?

Execute the "javadoc:test-javadoc" goal.

`$ mvn javadoc:test-javadoc`

This generates the test cases javadoc in folder "target/site/testapidocs/index.html".

## How to generate Jacoco's Code Coverage Report?

Execute the "jacoco:report" goal.

`$ mvn test jacoco:report`

This generates a jacoco code coverage report in folder "target/site/jacoco/index.html".

## How to generate PIT Mutation Code Coverage?

Execute the "org.pitest:pitest-maven:mutationCoverage" goal.

`$ mvn test org.pitest:pitest-maven:mutationCoverage`

This generates a PIT Mutation coverage report in folder "target/pit-reports/YYYYMMDDHHMI".

## How to combine different maven goals in one step?

You can combine different maven goals in the same command. For example, to locally run your project just like on jenkins, use:

`$ mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage`

## How to perform a faster pit mutation analysis?

Do not clean build => remove "clean"

Reuse the previous report => add "-Dsonar.pitest.mode=reuseReport"

Use more threads to perform the analysis. The number is dependent on each computer CPU => add "-Dthreads=4"

Temporarily remove timestamps from reports.

Example:

`$ mvn test jacoco:report org.pitest:pitest-maven:mutationCoverage -DhistoryInputFile=target/fasterPitMutationTesting-history.txt -DhistoryOutputFile=target/fasterPitMutationTesting-history.txt -Dsonar.pitest.mode=reuseReport -Dthreads=4 -DtimestampedReports=false`

## Where do I configure my database connection?

Each group should configure their database connection on file:

* src/main/resources/application.properties

# Oracle repository

If you get the following error:

```
[ERROR] Failed to execute goal on project
bike-sharing: Could not resolve dependencies for project
lapr3:bike-sharing:jar:1.0-SNAPSHOT:
Failed to collect dependencies at
com.oracle.jdbc:ojdbc7:jar:12.1.0.2:
Failed to read artifact descriptor for
com.oracle.jdbc:ojdbc7:jar:12.1.0.2:
Could not transfer artifact
com.oracle.jdbc:ojdbc7:pom:12.1.0.2
from/to maven.oracle.com (https://maven.oracle.com):
Not authorized , ReasonPhrase:Authorization Required.
-> [Help 1]
```

Follow these steps:

https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides

You do not need to set a proxy.

You can use existing dummy Oracle credentials available at http://bugmenot.com.


# LAPR3 Project 2020-2021 Report

## Constitution of the Working Group ###

The working group consists of students identified in the following table:

| Student Nr.  | Student's name   |
|--------------|------------------|
| **1190447**  | Bruno Silva      |
| **1190523**  | Diogo Domingues  |
| **1190633**  | Gonçalo Jordão   |
| **1190699**  | João Osório      |
| **1190995**  | Ricardo Mesquita |
| **1191045**  | Rui Soares       |

## Abstract

The aim of this report is to is to make you better understand our approach to developing the requested application. The main goal of our project is to develop a product that supports the delivery of pharmaceutical products. In this case, it will mostly be used to deliver self-injecting SARS-CoV-2 vaccines to clients. This service should allow the management of clients, products, orders, deliveries, etc. We, as a group, were able to develop a program that fulfills the request that were made to us and by reaching our goals this program.

## Introduction

This report was carried out in Laboratory Project 3, belonging to the computer engineering course of Instituto Superior de Engenharia do Porto (ISEP).
In this document, we present the information related to the development of LAPR3 project whose main goal was to develop an application that supports the delivery of pharmaceutical products. In this case, it will mostly be used to deliver self-injecting SARS-CoV-2 vaccines to clients. This service should allow the management of clients, products, orders, deliveries, etc.
This report provides the information about the problem we founded, our work organization, our solution to the problem and a conclusion about the project. This document will pay attention to how our final application works and the methodologies we adopted to solve the problems founded over these weeks of hard working.

## Problem Statement

We were challenged to develop an application that supports pharmacies management and delivery of pharmaceutical products.
The first requirements requested to us were those:

- A pharmacy company needs a solution for managing its products, clients, orders and deliveries.
- Clients must register to make online orders.
- A fee is charged for home deliveries.
- For each purchase, an invoice must be issued and sent by e-mail to the client.
- Clients earn credits when using the home delivery system.
- Deliveries are performed by couriers using an electric scooter.
- On every courier run, more than one order may be delivered. Yet, there’s a maximum payload that the courier can carry.
- The pharmacy has a parking space where electric scooters can be charged.

New requirements were requested every week with the addition of a new sprint.

- ### Sprint 1:

- An administrator should be capable of adding, removing or updating the list of electric scooters.
- Each electric scooter is identified by a unique number or QR Code1 in the system. The pharmacy park for electric scooters has a limited capacity.
- Clients must register themselves on the application. Every registration is free of charge but requires the client’s address.
- Each client must have and address with a geographical location using Decimal Degrees (DD)^2. For example, for the “Trindade Metro Station” it
corresponds to 41.152699, -8.609267 DD.
- A delivery run can be performed for one or more orders from one or different clients.
- The application should be able to estimate the amount of energy required to perform a delivery run. In order to reduce costs, the application should suggest the route that spends as little energy as possible, according to predefined physics models that can be applied. Each delivery run should spend as little energy as possible and you should implement best known algorithms for this task.
- Electric scooters charge the battery when are correctly docked to a parking space. Furthermore, when an electric scooter is parked, the courier receives an e-mail notification stating that the vehicle is correctly locked and received an estimate time for full charge. Unfortunately, the embedded system on each parking slot has scarce resources therefore you should use low-level implementation for this.
- Persistence for the system is mandatory, so you should be able to deliver a system that uses a relational database for persistency.
- When an order cannot be entirely fulfilled, the system should notify the client, and remove the item that does not have enough stock. An invoice/receipts should be issued for every order.
- The system should allow updating stocks when items are received at the pharmacy.
- Clients earn credits from each online order.

---

- The act of parking the vehicles must be simulated, because we are not yet developing the physical part of the system or interacting with real vehicles. For the time being, each park only has one parking slot.
- To simulate the act of parking a vehicle in a slot, a file named “lock_[datetime].data” must be placed under a (configurable) folder that can be read by your application. The name of this file should have the datetime replaced by “yyyy_month_day_hour_minute_second” (e.g., “lock_2020_12_32_12_34_54.data”) where year should always have four digits and the remaining elements should always have two digits. For each one of these files, another flag file named “lock_[datetime].data.flag” must be created, and the application should only take into account the “*.data” file when the flag/file exists. Both files should be disposed by the system after correct file handling.
- The simulation for a not-well parked vehicle is performed by placing a file where necessary information is missing from that file.
- The output should also be written to a file. The resulting file should be named “estimate_[datetime].data” and a flag
“estimate_[datetime].data.flag”. This file should be handled by your Java application in order to send the notification.
- Appropriate data structure should be used for this application. This application is composed by C and Assembly modules compiled using a Makefile. The C component is solely responsible for interacting with the file system, while everything else should be handles by Assembly modules.
- For the time being, the park has an unlimited charging capacity and only one charging space.
- There can multiple couriers working for the pharmacy, and multiple vehicles available. Delivery runs should take into account the weight of the delivery (which may change at each point), the courier and the scooter characteristics. A directed graph is required because there are one-way streets.

- ### Sprint 2:

- The company is expanding its branches and now has multiple pharmacies in their network. The administrator should be capable of managing these pharmacies registries.
- Furthermore, each park may have multiple parking spaces. Some have charging capabilities while others do not.
- Because some deliveries were taking a long time, the pharmacy decided to acquire some drones for deliveries. The drones can carry multiple orders at one time, if those do not exceed the maximum payload. An administrator should be capable of adding, removing or updating the list of drones. In order to automatically charge the drones, drone parks have been installed on some pharmacies. These parks also support drone charging on some spaces.
- Each client is notified when each delivery run starts.
- Clients can use their credits to "pay” for the online delivery.
- When there is no stock for an item, the pharmacy should automatically verify if any of the nearby pharmacies have that item available and back-order it. Delivery for this order can only start when all the items are in the same pharmacy. An automatic transfer note must be issued by the providing pharmacy and a delivery note should be issued by the receiving pharmacy.

---

- For the time being, drones take off and land vertically, to and from an altitude of 150 meters and that there are no physical barriers/restrictions (e.g., buildings, mountains, etc.) between two possible travelling points.
- You should now take into consideration elevation and road condition for your land deliveries. For air deliveries, you should take into consideration that drones always park (10 meters) from the ground.
- When a delivery drone finishes the last delivery, it should return to the starting park. If that park is out-of-reach because of battery charge, then the drone should choose a nearby park to charge in order to reach the starting park.
- In order to reduced wasted memory, your C and Assembly application should handle data using dynamic memory allocation.


- ### Sprint 3:

- The company wishes to simulate the running costs of each delivery approach (land vs. air), in order to choose which is the most profitable to use while delivering orders as fast as possible and providing the best service for their clients. As a result, the pharmacy administrator wants to understand which is the most energy efficient type of delivery (land/air), given some specific orders, initial and final points, you should compare the land/air deliveries.
- When performing an online order, the order should be forwarded to the pharmacy that is nearer to the client’s location.
- Restrictions have been applied to each park, and now each park has a limited power capacity that is shared by all parking spaces on that park.
- Therefore, when a new vehicle is parked, the charging capacity is evenly split between both devices, which alters the estimates of devices that were currently being charged. These changes should activate notifications.

---

- If a drone cannot perform a whole run on a charge, it may land on a nearby pharmacy park that has an available charging spot to perform a mid-run recharge. Furthermore, couriers might also use mid-parks for recharging.
- It has been noted that winds have a great impact on air as well as land deliveries. You should now take into account wind velocity and direction on your routes.

## Work Organization

Over these weeks, we worked as a united group and we tried to divide the tasks by all team members in an equivalent way. Whenever a new sprint started, all members of the group got together in order to decide the user stories to develop and distribute the tasks.
After that, each one worked in his Use Case, doing the following topics:

- Analysis (Use Case Diagram and System Sequence Diagram and if necessary, possible changes to the Domain Model);
- Design (Class Diagram and Sequence Diagram);
- Implementation (Implementing Test Cases and Code);
- Review (Reviewing the implementation).

We uploaded the work with regular commits on “BitBucket”, where we had a repository dedicated to this project.

As a group, we often had contact through “Microsoft Teams”, in class, and outside of it we resort to “Discord” so that we could help each other. Doing a general analysis, we consider that our group was a very supportive group and the proof of that is the fact that each element was always ready to help another who had difficulties in a specific point.

## Proposed Solution

Technologies used to solve the problem:
• IntelliJ – IDE used to work on the code;
• Visual Studio Code - IDE used to work with Assembly and C.
• Oracle SQL Developer - integrated development environment that simplifies the development and management of Oracle Database
• Atom – Application used to make Diagrams (Class Diagrams, Sequence
Diagrams, etc.) by using PlantUML, a component that allows to make this type
of diagrams;
• Jira Software – used to organize the tasks and to make the work updated;
• Bitbucket - used to update the work with regular commits;
• Jenkins - an open source automation server which enables developers around the world to reliably build, test, and deploy their software
• SonarQube - an open-source platform developed by SonarSource for continuous inspection of code quality to perform automatic reviews with static analysis of code to detect bugs, code smells, and security vulnerabilities on 20+ programming languages

#### Use Cases created based on the User Stories

| UC  | Description                                                                          |                     
|:----|:-------------------------------------------------------------------------------------|
| UC1 | [Create Pharmacy](docs/UC1/UC1_CreatePharmacy.md)                                      |
| UC2 | [User Registration](docs/UC2/UC2_UserRegistration.md)                                  |
| UC3 | [Perform Order](docs/UC3/UC3_PerformOrder.md)                                          |
| UC5 | [Product Delivery](docs/UC5/UC5_ProductDelivery.md)                                    |
| UC6 | [Add Scooter](docs/UC6/UC6_AddScooter.md)                                              |
| UC7 | [Remove Scooter](docs/UC7/UC7_RemoveScooter.md)                                        |
| UC8 | [Update Scooter](docs/UC8/UC8_UpdateScooter.md)                                        |
| UC9 | [Courier Registration](docs/UC9/UC9_CourierRegistration.md)                            |
| UC10 | [Remove Courier](docs/UC10/UC10_RemoveCourier.md)                                     |
| UC11 | [Notify Client](docs/UC11/UC11_NotifyClient.md)                                       |
| UC12 | [Send Email to Courier](docs/UC12/UC12_SendEmailToCourier.md)                            |
| UC13 | [Add Product](docs/UC13/UC13_AddProduct.md)                                           |
| UC14 | [Remove Product](docs/UC14/UC14_RemoveProduct.md)                                     |
| UC15 | [Update Product](docs/UC15/UC15_UpdateProduct.md)                                     |
| UC16 | [Parking the Scooter](docs/UC16/UC16_ParkingTheScooter.md)                            |
| UC17 | [Start Delivery by Scooter](docs/UC17/UC17_StartDeliveryByScooter.md)                 |
| UC18 | [Create Scooters/Drones Park](docs/UC18/UC18_CreateScootersDronesPark.md)             |
| UC19 | [Search Product Nearby Pharmacies](docs/UC19/UC19_SearchProductNearbyPharmacies.md)   |
| UC21 | [Add Drone](docs/UC21/UC21_AddDrone.md)                                               |
| UC22 | [Notify Client Delivery](docs/UC22/UC22_NotifyClientDelivery.md)                      |
| UC24 | [Update Drone](docs/UC24/UC24_UpdateDrone.md)                                         |
| UC25 | [Finish Delivery By Scooter](docs/UC25/UC25_FinishDeliveryByScooter.md)               |
| UC27 | [Remove Drone](docs/UC27/UC27_RemoveDrone.md)                                         |
| UC28 | [Parking the Drone](docs/UC28/UC28_ParkingTheDrone.md)                                |

### Notes:

- When creating a pharmacy, in addition to the pharmacy data, it will also be requested data for the immediate creation of a scooter park for that pharmacy.

---

- In relation to the credits earned by the clients while performing an order, we considered for each euro spent that the client would earn 1 credit and then each credit would the equivalent to 10 cents.

---

For the calculations related to physics, we considered as fixed values:
- The weight of Courier: 80 kg;
- Average Speed Scooter: 30 km/h
- Scooter and Drone Efficiency: 100 %
- Air Density: 1.275 kg/m^3
- Drag Coefficient Scooter: 1.1
- Front Area of the Scooter: 0.3 m^2
- Gravitational acceleration: 10 m/s^2
- Resistance coefficient: 0.04
- Drag Coefficient Drone: 0.3
- Front Area of the Drone: 0.4 m^2
- Average Speed Drone: 50 km/h

---

- The lock file must have the following format:
Example
Id:
1
Designation:
Scooter
Current Battery:
59
Battery power:
350
Park maximum power:
4000
Number of vehicles charging:
1

This vehicle must be assignes to a delivery, so we can grant access to the courier's email in order to notify him via email.

## Conclusion

To conclude our report, we focused on explain our application, presenting the Analysis and the Design of each User Storie created. We start with an abstract so that the reader can understand what we will be focused in our report. Then we made an introduction, explained the problem statement and the proposed solution to solve the related problem.
We believe that we were able to achieve great results and ended up creating
an amazing application that will for sure help managing the products, clients, orders and deliveries associated to the existing pharmacies.
