# NC-UNC-2021 - Movie Library


## Overview
The project has been redesigned using a non-relational MongoDB database. The following changes were made: 
- Annotations in models are changed according to MongoDB documentation. 
- Repository interfaces with MongoRepository implementation were created
- The logic of interaction with repositories has been moved to the service level. CRUD operations are performed using MongoTemplate.