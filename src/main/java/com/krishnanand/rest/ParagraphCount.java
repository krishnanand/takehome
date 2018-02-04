package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * An instance of this class encapsulates the frequency of words in a paragraph. If no words are
 * found, then error messages are displayed.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@JsonInclude(Include.NON_NULL)
public class ParagraphCount implements IError {
  
  @JsonInclude(value=Include.NON_EMPTY)
  private List<Error> errors;
  

  @JsonInclude(value=Include.NON_EMPTY)
  private List<Map<String, Integer>> wordCount;
  
  public ParagraphCount() {
    this.errors = new ArrayList<>();
  }

  @Override
  public List<Error> getErrors() {
    return this.errors;
  }

  @Override
  public void addError(int code, String message) {
    this.errors.add(new Error(code, message));
  }

  public List<Map<String, Integer>> getWordCount() {
    return wordCount;
  }

  public void setWordCount(List<Map<String, Integer>> wordCount) {
    this.wordCount = wordCount;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ParagraphCount [errors=");
    builder.append(errors);
    builder.append(", wordCount=");
    builder.append(wordCount);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    result = prime * result + ((wordCount == null) ? 0 : wordCount.hashCode());
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
    ParagraphCount other = (ParagraphCount) obj;
    if (errors == null) {
      if (other.errors != null) {
        return false;
      }
    } else if (!errors.equals(other.errors)) {
      return false;
    }
    if (wordCount == null) {
      if (other.wordCount != null) {
        return false;
      }
    } else if (!wordCount.equals(other.wordCount)) {
      return false;
    }
    return true;
  }

}
