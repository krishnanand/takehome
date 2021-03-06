package com.krishnanand.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * An instance of this class represents an entry point to all service requests to perform
 * {@code add}, {@code delete}, and {@code query} operations.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController
public class PersonController extends AbstractController {

  @Autowired
  private final IPersonService personService;

  public PersonController(IPersonService personService) {
    this.personService = personService;
  }

  /**
   * Initiates an insert operation of {@link Person}.
   * 
   * @param person person
   * @return response entity encapsulating person's unique identifier or errors
   */
  @PostMapping(value = "/person")
  ResponseEntity<PersonCredentials> addPerson(@RequestBody(required = false) Person person) {
    PersonCredentials personCredentials = this.personService.createPerson(person);
    if (personCredentials.getErrors() == null || personCredentials.getErrors().isEmpty()) {
      return new ResponseEntity<PersonCredentials>(personCredentials, HttpStatus.CREATED);
    }
    return this.generateEntity(personCredentials);
  }

  /**
   * Initiates an get operation of {@link Person} by a unique identifier
   * 
   * @param personId unique person id
   * @return response entity encapsulating person's unique identifier or errors
   */
  @GetMapping(value = "/person/{personId}")
  ResponseEntity<Person> getPersonByPersonId(@PathVariable(required=false) final String personId) {
    Person person = this.personService.findPersonByPersonId(personId);
    return this.generateEntity(person);
  }
  
  /**
   * Deletes a person record by a unique identifier.
   * 
   * @param personId unique person id
   * @return response entity encapsulating person's unique identifier or errors
   */
  @DeleteMapping(value = "/person/{personId}")
  ResponseEntity<PersonDeleteResponse> deletePersonById(
      @PathVariable(required=false) final String personId) {
    PersonDeleteResponse response = this.personService.deletePersonById(personId);
    return this.generateEntity(response);
  }
}
