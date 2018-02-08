package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * An instance of the class encapsulates all functionality associated with calculating the
 * frequency of words.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Component
public class TakeHomeService implements ITakeHomeService {

  /**
   * Returns a max heap data structure that encapsulates the words sorted by the number of 
   * their occurrences.
   * 
   * <p>The implementation is given below:
   * <ul>
   *   <li>The text is split by whitespace into multiple words. Frequency of each word is
   *   calculated.
   *   <li>A combination of word and frequency is aggregated and put in a max heap for
   *   sorting. The insertion operation and sorting is logarithmic operation.
   * </ul>
   */
  @Override
  public ParagraphCount countOccurrencesOfText(
      ParagraphContent content) {
    String text =
        content == null || content.getParagraph() == null ? null : content.getParagraph();
    if (text == null || text.isEmpty()) {
      ParagraphCount pc = new ParagraphCount();
      pc.addError(400, "No paragraph content found");
      return pc;
    }
    String[] words = text.split(" ");
    Map<String, Integer> map = new LinkedHashMap<>();
    for (String word : words) {
      word =
          word.replaceAll("\\.", "").replaceAll(",", "").replaceAll("\\?", "")
              .replaceAll(";", "").replaceAll("!", "");
      map.put(word, map.getOrDefault(word, 0) + 1);
    }

    // Aggregating the imports and sorting them.
    List<String> pq = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      pq.add(entry.getKey());
    }
    Collections.sort(pq);
    List<Map<String, Integer>> list = new ArrayList<>();
    for (String word : pq) {
      Map<String, Integer> entryMap = new LinkedHashMap<>();
      entryMap.put(word, map.get(word));
      list.add(entryMap);
    }
    ParagraphCount pc = new ParagraphCount();
    pc.setWordCount(list);
    return pc;
  }

  /**
   * Generates an array of fibonacci numbers using recursion.
   * 
   * @param n number of sequences to be generated
   * @return array of fibonacci sequences
   */
  @Override
  public long[] generateFibonacciSeries(int n) {
    if (n <= 0) {
      return new long[0];
    }
    long[] fib = new long[n];
    fib[0] = 0;
    if (n == 1) {
      return fib;
    }
    fib[1] = 1;
    if (n == 2) {
      return fib;
    }
    fibHelper(fib, n - 1);
    return fib;
  }
  
  /**
   * Returns the memoized fibonacci value.
   * 
   * @param fib array of fibonacci numbers
   * @param n number of fibonacci numbers to be generated
   * @return memoized fibonacci number
   */
  private long fibHelper(long[] fib, int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    } else if (fib[n] > 0) {
      return fib[n];
    }
    fib[n] = fibHelper(fib, n - 1) + fibHelper(fib, n - 2);
    return fib[n];
  }
}
