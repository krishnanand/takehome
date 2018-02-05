package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of this class encapsulates the person id representing a person entity.
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class PersonCredentials implements IError {
  
  @JsonInclude(Include.NON_EMPTY)
  private List<IError.Error> errors;
  
  public PersonCredentials() {
    this.errors = new ArrayList<>();
  }
  
  private String personId;

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    result = prime * result + ((personId == null) ? 0 : personId.hashCode());
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
    if (!PersonCredentials.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    PersonCredentials other = (PersonCredentials) obj;
    return (
        this.errors == other.getErrors() ||
        (this.errors != null && this.errors.equals(other.getErrors()))) && 
        (this.personId == other.getPersonId() ||
        (this.personId != null && this.personId.equals(other.getPersonId())));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PersonCredentials [errors=");
    builder.append(errors);
    builder.append(", personId=");
    builder.append(personId);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public List<Error> getErrors() {
    return this.errors;
  }

  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }
}
