package com.krishnanand.rest;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation that bridges between the view and the model implementations.
 * @author krishnanand (Kartik Krishnanand)
 */
@Component
public class PersonService implements IPersonService {
  
  private final IPersonDao personDao;
  
  @Autowired
  public PersonService(IPersonDao personDao) {
    this.personDao = personDao;
  }
  
  /**
   * Verifies if the string is empty.
   * 
   * @param str string to be verified
   * @return {@code true} if empty; {@code false} otherwise
   */
  private boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * Creates an entry in the database and returns a unique identifier.
   */
  @Override
  @Transactional
  public PersonCredentials createPerson(Person person) {
    PersonCredentials pc = new PersonCredentials();
    if (person == null) {
      pc.addError(400, "No record was found.");
      return pc;
    }
    if (isEmpty(person.getFirstName()) || isEmpty(person.getLastName())) {
      pc.addError(400, "The record was invalid.");
      return pc;
    }
    String personId = this.personDao.createPerson(person);
    pc.setPersonId(personId);
    return pc;
  }

  /**
   * Returns a person associated with a unique identifier.
   */
  @Override
  @Transactional
  public Person findPersonByPersonId(String personId) {
    Person person =  this.personDao.findPersonById(personId);
    if (person == null) {
      person = new Person();
      person.addError(404, "No record was found.");
    }
    return person;
  }

  @Override
  @Transactional
  public PersonDeleteResponse deletePersonById(String personId) {
    int count = this.personDao.deletePersonByPersonId(personId);
    PersonDeleteResponse response = new PersonDeleteResponse();
    if (count == 0) {
      response.addError(404,"No record was found.");
    } else {
      response.setDeleteSuccess(true);
    }
    return response;
  }
}
