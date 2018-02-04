package com.krishnanand.rest;

/**
 * An instance of this class encapsulates the person id representing a person entity.
 * @author krishnanand (Kartik Krishnanand)
 */
public class PersonCredentials {
  
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
    return personId == null && other.personId == null ||
        (this.personId != null && this.personId.equals(other.getPersonId()));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PersonCredentials [personId=");
    builder.append(personId);
    builder.append("]");
    return builder.toString();
  }
  
  
  

}
