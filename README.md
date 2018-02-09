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
    "paragraph": "we had everything before us."
}
```

**Request**

The request body is shown below

| Name | Type | Description|
| :---         |     :---     |       :---     |  
| content | string   | paragraph containing the words to be counted |

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


1) #### The request body is either missing, or has no content associated with paragraph. ####

In case of an error, the response payload will have the following characeristics.

The error response is defined as follows.

● HTTP status code 400 (Bad Request)

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
| errors  | array   | array of errors |
| code   | integer    | Http status code  |
| message | string | user friendly error message|

 The sample response 

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

#### GET /takehome/rest/fibonacci ####

The implementation returns an array of fibocci sequences.

The sample request is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| n  | request parameter (integer)  | number used to determine how many numbers are added to fibonacci sequence |

**Response**

● a status code of 200 OK
● Response Body Properties
| Name | Type | Description |
| :---         |     :---      | :--- |
|| array   | array of fibonacci sequences |

For `n = 10`, the sample output response would be

`[0,1,1,2,3,5,8,13,21,34]`

#### GET /takehome/rest/deadlock ####

The function checks if the deadlock has occurred after specified period of time.


The sample request is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| timeInSeconds  | request parameter (integer)  | Time after which the deadlock is to be checked |

**Response**

● a status code of 200 OK

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
| deadlock   | boolean | If true, then it suggests that the deadlock has occurred and vice versa|

The sample response is given below

```
  {'deadlock': true}
```

### POST /takehome/person/add

This creates an entity of type `Person` in the database.

The sample request body is given below

| Name | Type | Description | Required|
| :---         |     :---      | :--- |:--- |
| firstName | string  | person's first name  |Yes|
| secondName | string  | person's last name  |Yes|

The example of request body is given below

```
{
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**

The response payload returns a unique person identifier associated with the person.

● a status code of 201 Created
● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- | 
|personId| String   | Unique person identifier|

The sample response body is

```
{
    "personId": "ABCDE12345
}
```

1) #### The request body does not contain either first or last name or both, or that the request body is empty. ####

In this case, 400 error is returned. The sample response 

```
{  
  "errors":[  
    {  
      "code":400,
      "message":"The input was invalid. Please check if either first name or last name is missing."
    }
  ]
}
```

### GET /takehome/person/get

Returns an entity of type `Person` from the database.

The sample request body is given below

| Name | Type | Description | Required|
| :---         |     :---      | :--- |:--- |
| personId | string (request parameter)  | unique person identifier  |Yes|

**Response**

The response payload returns a unique person entity.

● a status code of 200 OK

● Response Body Properties

The example of request body is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| firstName | string  | person's first name  |
| secondName | string  | person's last name  |

```
{
    "firstName": "John",
    "lastName": "Doe"
}
```

1) #### The request parameter ```personId``` does not exist. ####

In this case, 400 error is returned. The sample response 

```
{  
  "errors":[  
    {  
      "code":400,
      "message":"The input was invalid. Please recheck input."
    }
  ]
}
```

2) #### `Person` entity does not exist for the unique person identifier. ####

In this case, 404 error is returned. The sample response 

```
{  
  "errors":[  
    {  
      "code":404,
      "message":"No record was found for the input."
    }
  ]
}
```


### DELETE /takehome/person/delete

Returns an entity of type `Person` from the database.

The sample request body is given below

| Name | Type | Description | Required|
| :---         |     :---      | :--- |:--- |
| personId | string (request parameter)  | unique person identifier  |Yes|

**Response**

The response payload returns the success of the operation.

● a status code of 200 OK

● Response Body Properties

The example of request body is given below

| Name | Type | Description |
| :---         |     :---      | :--- |
| success | boolean  | if true, then it indicates that the entity was deleted  |

```
{
    "success": true
}
```

1) #### The request parameter ```personId``` does not exist. ####

In this case, 400 error is returned. The sample response 

```
{  
  "errors":[  
    {  
      "code":400,
      "message":"The input was invalid. Please recheck input."
    }
  ]
}
```

2) #### `Person` entity does not exist for the unique person identifier. ####

In this case, 404 error is returned. The sample response 

```
{  
  "errors":[  
    {  
      "code":404,
      "message":"No record was found for the input."
    }
  ]
}
```

#### GET /takehome/service/get ####

The function checks if the deadlock has occurred after specified period of time.


**Response**

● a status code of 200 OK

● Response Body Properties

| Name | Type | Description |
| :---         |     :---      | :--- |
| id   | long | identifier|
| userId   | long | user identifier|
| title   | string | title string|
| body   | string | body string|



The sample response is given below

```
   [
   {
    "userId": 1,
    "id": 1,
    "title": "title text",
    "body": "body text"
  }
  ]
```
