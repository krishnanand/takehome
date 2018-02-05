package com.krishnanand.rest;

import java.util.List;

/**
 * An instance of the class encapsulates the string and the number of times it occurs in a
 * a string.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class ParagraphResponse implements Comparable<ParagraphResponse> {
  
  private String text;
  
  private int numberOfOccurrences;
  
  public ParagraphResponse(String text, int numberOfOccurrences) {
    this.text = text;
    this.numberOfOccurrences = numberOfOccurrences;
  }
  
  // Visible for testing only
  ParagraphResponse() {}

  public String getText() {
    return text;
  }

  // Visible for testing.
  public void setText(String text) {
    this.text = text;
  }

  public int getNumberOfOccurrences() {
    return numberOfOccurrences;
  }

  // Visible for testing.
  public void setNumberOfOccurrences(int numberOfOccurrences) {
    this.numberOfOccurrences = numberOfOccurrences;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ParagraphContentResponse [text=");
    builder.append(text);
    builder.append(", numberOfOccurrences=");
    builder.append(numberOfOccurrences);
    builder.append("]");
    return builder.toString();
  }

  /**
   * Orders objects by the text content, and then by number of occurrences
   */
  @Override
  public int compareTo(ParagraphResponse o) {
    return this.text.compareTo(o.getText());
  }
}
