package com.krishnanand.rest;

/**
 * An instance of the class encapsulates the string and the number of times it occurs in a
 * a string.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class ParagraphResponse implements Comparable<ParagraphResponse>{
  
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (numberOfOccurrences ^ (numberOfOccurrences >>> 32));
    result = prime * result + ((text == null) ? 0 : text.hashCode());
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
    if (!ParagraphResponse.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    ParagraphResponse other = (ParagraphResponse) obj;
    if (numberOfOccurrences != other.numberOfOccurrences) {
      return false;
    }
    if (text == null && other.getText() == null) {
      return true;
    }
    return this.text != null && this.text.equals(other.getText());
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
    int stringCompare = this.text.compareTo(o.getText());
    if (stringCompare == 0) {
      return this.getNumberOfOccurrences() - o.getNumberOfOccurrences();
     }
    return stringCompare;
  }
}
