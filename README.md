# NC-UNC-2021 - Movie Library


## Introduction
This module contains tests for an existing Spring Boot web application.

## Technologies
When writing tests, the following tools were used:
- Junit 5
- Mockito

## Overview
For the isolated execution of tests, the h2 database was connected in the test profile. To test repositories, during application startup, the database is filled using a script from data.sql, then the operation of each repository method is checked in the test class and the data obtained is compared with data from the database. To test controllers without running an http server, the MockMvc tool was used, it allows you to emulate sending http requests. To test the repository methods called from the controller, the Mockito framework was used, it allows you to create stubs of dependent objects, defining their behavior in advance.
