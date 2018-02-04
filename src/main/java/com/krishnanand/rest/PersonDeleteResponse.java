package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of the class encapsulates the response after the invocation of the delete request.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class PersonDeleteResponse implements IError {
  
  // If {@code true}, then it indicates that the resource was successfully deleted.
  // If {@code false} or {@code null}, then it indicates that the resource was found.
  private Boolean deleteSuccess;
  
  @JsonInclude(value=Include.NON_EMPTY)
  private List<IError.Error> errors = new ArrayList<>();
  
  public PersonDeleteResponse() {
    this.errors = new ArrayList<>();
  }

  @Override
  public List<Error> getErrors() {
    return this.errors;
  }

  public Boolean getDeleteSuccess() {
    return deleteSuccess;
  }

  public void setDeleteSuccess(Boolean deleteSuccess) {
    this.deleteSuccess = deleteSuccess;
  }

  public void setErrors(List<IError.Error> errors) {
    this.errors = errors;
  }
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deleteSuccess == null) ? 0 : deleteSuccess.hashCode());
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
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
    PersonDeleteResponse other = (PersonDeleteResponse) obj;
    if (deleteSuccess == null) {
      if (other.deleteSuccess != null) {
        return false;
      }
    } else if (!deleteSuccess.equals(other.deleteSuccess)) {
      return false;
    }
    if (errors == null) {
      if (other.errors != null) {
        return false;
      }
    } else if (!errors.equals(other.errors)) {
      return false;
    }
    return true;
  }

  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PersonDeleteResponse [deleteSuccess=");
    builder.append(deleteSuccess);
    builder.append(", errors=");
    builder.append(errors);
    builder.append("]");
    return builder.toString();
  }
}
