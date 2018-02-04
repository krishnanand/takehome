package com.krishnanand.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
  @ResponseBody
  public ParagraphCount mostFrequentWords(
      @RequestBody(required=false) final ParagraphContent content) {
    return this.takeHomeService.countOccurrencesOfText(content);
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
  
  @RequestMapping(value="/rest/deadlock", method=RequestMethod.GET)
  @ResponseBody
  boolean isDeadlock(@RequestParam final int timeInSeconds) {
    return Deadlocks.startAndDetectDeadlocks(timeInSeconds);
  }
  
  //@RequestMapping(value="/addPerson", method=RequestMethod.POST)
  
}
