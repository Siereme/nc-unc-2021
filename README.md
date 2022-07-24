# NC-UNC-2021 - Movie Library

## Introduction
This module extends the web application from the previous branch.

### What's new
- Hibernate library has been added. The models are marked with javax persistence annotations, interaction with the database is carried out using entity manager.
- Added the ability to export a database snapshot in json file format and import data into the database from a json file.
- Added an implementation of the Converter interface to solve the problem of mapping entities passed from JSP forms to the controller.