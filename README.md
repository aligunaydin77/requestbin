# HSBC UnattendedTest - Request Bin

Developers often need to troubleshoot the HTTP requests that a client makes against an endpoint.
This project aims to provide a tool that allows for the creation of a "bin": a temporary url,
which a client can send HTTP requests to, so that they get recorded for logging purposes.

* create new bin: POST /bin - returns the bin id.
* record requests: /bin/{id}
* inspect requests: GET /bin/{id}/inspect - retrieve the list of requests made against this "bin."

Complete the following classes to allow the creation, recording and inspection of HTTP requests:
* src/main/java/com/hsbc/unattendedtest/bin/model/Bin.java
* src/main/java/com/hsbc/unattendedtest/bin/model/Request.java
* src/main/java/com/hsbc/unattendedtest/bin/BinController.java
* src/test/java/com/hsbc/unattendedtest/bin/BinControllerTest

Feel free to extend the current solution in any way that you see fit.

## Assessment Objectives
* working software
* clean/readable code

## Assessment Dependencies
* Please use java JDK 1.8 and Maven.
* Feel free to introduce any other dependencies you wish.
