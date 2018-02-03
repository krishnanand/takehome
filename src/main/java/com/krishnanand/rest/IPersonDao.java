package com.krishnanand.rest;

import java.util.List;

/**
 * Strategy definition of data access layer to insert person details into the database.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IPersonDao {
  
  /**
   * Inserts the data inside person object into the database.
   * 
   * @param person person object to be inserted
   * @return alphanumeric string key
   */
  String createPerson(Person person);
  
  /**
   * Finds Person object by {@code personId}.
   * 
   * @param personId person id
   * @return person object
   */
  Person findPersonById(String personId);
  
  /**
   * Deletes the person by person id.
   * 
   * @param personId unique person id
   * @return number of records deleted
   */
  int deletePersonByPersonId(String personId);
}
