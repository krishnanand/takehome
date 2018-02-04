package com.krishnanand.rest;

/**
 * Strategy definition that manages all business logic for person related tasks.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IPersonService {
  
  /**
   * Inserts person entity into the database.
   * 
   * @param person person instance
   * @return value object representing the credentials
   */
  PersonCredentials createPerson(Person person);
  
  /**
   * Finds person by unique person identifier.
   * 
   * @param personId unique person identifier
   * @return unique person representation; an error response if missing.
   */
  Person findPersonByPersonId(String personId);
  
  /**
   * Deletes person by unique identifier and generates a response to be
   * delivered.
   * 
   * @param personId unique person identifier
   * @return response upon the invocation of successful delete
   */
  PersonDeleteResponse deletePersonById(String personId);
}
