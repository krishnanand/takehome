package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A value object that creates a person.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class Person implements IError {
  
  private String firstName;
  
  private String lastName;
  
  @JsonInclude(value=Include.NON_EMPTY)
  private List<Error> errors;
  
  public Person() {
    this.errors = new ArrayList<>();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Person other = (Person) obj;
    return this.lastName != null && this.lastName.equals(other.getLastName()) &&
        this.firstName != null && this.firstName.equals(other.getFirstName());
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Person [firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append("]");
    return builder.toString();
  }

  /**
   * Returns a list of errors that were generated.
   */
  @Override
  public List<Error> getErrors() {
    return this.errors;
  }

  /**
   * Creates and adds an error representation.
   */
  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }
}
