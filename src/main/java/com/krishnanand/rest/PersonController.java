package com.krishnanand.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
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
  @PostMapping(value = "/person/add")
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
  @GetMapping(value = "/person/get")
  ResponseEntity<Person> getPersonByPersonId(@RequestParam(required=false) final String personId) {
    Person person = this.personService.findPersonByPersonId(personId);
    return this.generateEntity(person);
  }
  
  /**
   * Deletes a person record by a unique identifier.
   * 
   * @param personId unique person id
   * @return response entity encapsulating person's unique identifier or errors
   */
  @DeleteMapping(value = "/person/delete")
  ResponseEntity<PersonDeleteResponse> deletePersonById(
      @RequestParam(required=false) final String personId) {
    PersonDeleteResponse response = this.personService.deletePersonById(personId);
    return this.generateEntity(response);
  }

  private <T extends IError> ResponseEntity<T> generateEntity(T object) {
    if (object.getErrors() != null && !object.getErrors().isEmpty()) {
      IError.Error error = object.getErrors().get(0);
      if (error.getCode() == 400) {
        return new ResponseEntity<T>(object, HttpStatus.BAD_REQUEST);
      } else if (error.getCode() == 404) {
        return new ResponseEntity<T>(object, HttpStatus.NOT_FOUND);
      }
    }
    return new ResponseEntity<T>(object, HttpStatus.OK);
  }
}
