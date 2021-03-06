package com.krishnanand.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry for rest end points.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController
public class Controller extends AbstractController {
  
  private final ITakeHomeService takeHomeService;
  
  @Autowired
  public Controller(ITakeHomeService paragraphService) {
    this.takeHomeService = paragraphService;
  }

  /**
   * Returns "Hello World" response.
   * 
   * @return hello world as response
   */
  @RequestMapping(value="/rest/helloworld", method=RequestMethod.GET)
  @ResponseBody
  public String helloWorld() {
   return "Hello World";
  }
  
  /**
   * Returns the number of unique words in the paragraph sorted by the frequency of usage.
   * 
   * @param content value object encapsulating the number of words
   * @return response body representing the words and their frequencies
   */
  @RequestMapping(value="/rest/frequentwords", method=RequestMethod.GET)
  public ResponseEntity<Object> mostFrequentWords(
      @RequestBody(required=false) final ParagraphContent content) {
    ParagraphCount pc = this.takeHomeService.countOccurrencesOfText(content);
    if (pc.getErrors() == null || pc.getErrors().isEmpty()) {
      return new ResponseEntity<Object>(pc.getWordCount(), HttpStatus.OK);
    }
    return new ResponseEntity<Object>(pc, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * Returns an array of {@code n} fibonacci numbers.
   * 
   * @param n length of the fibonacci sequence
   * @return array of fibonacci numbers
   */
  @RequestMapping(value="/rest/fibonacci", method=RequestMethod.GET)
  @ResponseBody
  long[] fibonacciSequence(@RequestParam final int n) {
    return this.takeHomeService.generateFibonacciSeries(n);
  }
  
  /**
   * Initiates a deadlock, and verifies after a configured time has elapsed.
   * 
   * @param timeInSeconds time in seconds
   * @return 
   */
  @RequestMapping(value="/rest/deadlock", method=RequestMethod.GET)
  @ResponseBody
  Map<String, Boolean> isDeadlock(@RequestParam final int timeInSeconds) {
    return Deadlocks.startAndDetectDeadlocks(timeInSeconds);
  }
}
