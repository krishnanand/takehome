package com.krishnanand.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of the class encapsulates a single json object returned from an
 * external rest service.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class User {
  
  private Long id;
  
  private Long userId;
  
  private String title;
  
  private String body;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
    if (!User.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    User other = (User) obj;
    return (this.body == other.getBody() ||
        (this.body != null && this.body.equals(other.getBody()))) &&
        (this.id == other.getId() || (this.id != null && this.id.equals(other.getId()))) &&
        (this.title == other.getTitle() ||
        (this.title != null && this.title.equals(other.getTitle()))) &&
        (this.userId == other.getUserId() ||
        (this.userId != null && this.userId.equals(other.getUserId())));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [");
    builder.append("id=");
    builder.append(id);
    builder.append(", userId=");
    builder.append(userId);
    builder.append(", title=");
    builder.append(title);
    builder.append(", body=");
    builder.append(body);
    builder.append("]");
    return builder.toString();
  }

}
