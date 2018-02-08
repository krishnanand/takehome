## Table of Contents

- [Introduction](#introduction)
- [Deployment](#deployment)
- [API Endpoints](#api-endpoints)


### Introduction ###

This is a restful web application that exposes a few endpoints. The project needs the following softwares to be installed.

* Java 8
* Apache Maven 3.x installed.

### Deployment ###

There are two ways to deploy the web application.

1) As this is a Maven project, it can be imported in an IDE such as Eclipse or IntelliJ as a Maven Project.

* Create a Run configuration -> Java Application. Once done, select App as a main-class. 
 The application will be deployed to an embedded TOMCAT container 

* Build and compile the project from command line. Navigate to the project root using the command line, and execute the following command mvn spring-boot:run. You will need maven plugin for that.

The RESTful services can be invoked after either steps is performed. 

**IMPORTANT**: these two instructions are mutually exclusive.

### API Endpoints ##
Following are the list of endpoints supported the restful application

#### GET /takehome/rest/helloword ####

Returns "Hello World" as string response.


**Response**

A success response will include:

● a status code of 200 Created


The sample response is given below

````
Hello World
````

#### GET /takehome/rest/frequentwords ####

Returns a json array of the most frequent words in sorted alphabetical order.

**Request**

| Name | Type | Description| Required |
| :---         |     :---     |          :--- | :---   |
| paragraph  | string   | paragraph content  body  | Yes |

The sample request is given below
```
{
    "paragraph": "we had everything before us, we had nothing before us, we were all going direct to Heaven, we were all going direct the other way— in short, the period was so far like the present period, that some of its noisiest authorities insisted on its being received, for good or for evil, in the superlative degree of comparison only."
}
```

**Request**

The request body is shown below
| Name | Type | Description|
| :---         |     :---     |  
| conent | string   | paragraph |

The example of a successful response is given below

````
[
 {"But": 9}
]
````

**Response**

A successful response will be a json array.

● a status code of 200 OK

● Response body properties

Each json object consists of the following

| Name | Type | Description|
| :---         |     :---     |          :--- |
| \<word in sorted order\>  | string   | word occurring in the paragraph |
| \<number of occurrences of a word\>   | int    |number of times words occurred in a string|

The example of a successful response is given below

````
[
 {"But": 9}
]
````

In case of an error, the response payload will contain the following attribute.

The error response is defined as follows.

● HTTP status code 400 Bad Request

but the response body contains error messages

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
| errors  | array   | array of errors |
| code   | integer    | Http status code  |
| message | string | user friendly error message|

1) #### The request body is either missing, or has not content by the name of `paragraph`.   ####

In this case, 400 error is returned. The sample response 

````
{  
  "errors":[  
    {  
      "code":400,
      "message":"No paragraph content found"
    }
  ]
}
````

#### GET /takehome/rest/fibonacci?n=10 ####

The implementation returns an array of fibocci sequene.

The request body payload is defined below.

The sample request is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| n  | request param   | number used to determine how many numbers are added to fibonacci sequence |

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
|| array   | array of fibonacci sequences |
