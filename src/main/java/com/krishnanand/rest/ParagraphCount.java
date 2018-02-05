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
}
