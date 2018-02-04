package com.krishnanand.rest;

/**
 * Strategy definition that encapsulates all functions related to processing of paragraph.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface ITakeHomeService {
  
  /**
   * An implementation of this function will count the number of occurrences of of words
   * in a given text.
   * 
   * @param content value object encapsulating the free form text
   * @return object max heap object that encapsulates data sorted by words, and its occurrences.
   */
  ParagraphCount countOccurrencesOfText(ParagraphContent content);
  
  /**
   * An implementation of this function will generate n numbers of fibonacci series.
   * 
   * @param n length of fibonacci sequence
   * @return array of fibonacci numbers
   */
  long[] generateFibonacciSeries(int n);
}
