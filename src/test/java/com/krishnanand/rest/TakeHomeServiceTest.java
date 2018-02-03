package com.krishnanand.rest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit test for {@link TakeHomeService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(JUnit4.class)
public class TakeHomeServiceTest {
  
  private TakeHomeService takeHomeService;
  
  @Before
  public void setUp() throws Exception {
    this.takeHomeService = new TakeHomeService();
  }
  
  @After
  public void tearDown() throws Exception {
    this.takeHomeService = null;
  }
  
  @Test
  public void testCountOccurrencesOfText() throws Exception {
   List<Map<String, Integer>> list =
        this.takeHomeService.countOccurrencesOfText(readParagraphFromFile());
   Assert.assertEquals(list.get(0).get("But"), Integer.valueOf(1));
  }
  
  private ParagraphContent readParagraphFromFile() throws Exception {
    try(InputStream is = ClassLoader.getSystemResourceAsStream("paragraph.txt");
        Scanner scanner = new Scanner(is)) {
      StringBuilder sb = new StringBuilder();
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
      }
      ParagraphContent pc = new ParagraphContent();
      pc.setParagraph(sb.toString());
      return pc;
    }
  }

  @Test
  public void testGenerateFibonacciSeries_ZeroLength() throws Exception {
    Assert.assertArrayEquals(new long[0], this.takeHomeService.generateFibonacciSeries(0));
  }

  @Test
  public void testGenerateFibonacciSeries_Length1() throws Exception {
    Assert.assertArrayEquals(new long[] {0}, this.takeHomeService.generateFibonacciSeries(1));
  }

  @Test
  public void testGenerateFibonacciSeries_Length2() throws Exception {
    Assert.assertArrayEquals(new long[] {0, 1}, this.takeHomeService.generateFibonacciSeries(2));
  }

  @Test
  public void testGenerateFibonacciSeries_Length10() throws Exception {
    Assert.assertArrayEquals(
        new long[] {0, 1, 1, 2, 3, 5, 8, 13, 21, 34},
        this.takeHomeService.generateFibonacciSeries(10));
  }
}
