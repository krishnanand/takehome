package com.krishnanand.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of the class contains paragraph content representing a request body for all
 * JSON requests.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class ParagraphContent {

  // Represents a paragraph string to be passed as request body.
  private String paragraph;

  @JsonInclude(Include.ALWAYS)
  public String getParagraph() {
    return paragraph;
  }

  public void setParagraph(String paragraph) {
    this.paragraph = paragraph;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((paragraph == null) ? 0 : paragraph.hashCode());
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
    if (!ParagraphContent.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    ParagraphContent other = (ParagraphContent) obj;
    if (this.paragraph == null && other.getParagraph() == null) {
      return true;
    } 
    return this.paragraph != null && this.paragraph.equals(other.getParagraph());
  }

}
