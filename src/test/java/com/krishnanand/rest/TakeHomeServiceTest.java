package com.krishnanand.rest;

import java.io.InputStream;
import java.util.Arrays;
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
   ParagraphCount pc  =
        this.takeHomeService.countOccurrencesOfText(readParagraphFromFile());
   Assert.assertNotNull(pc.getWordCount());
   Assert.assertEquals(pc.getWordCount().get(0).get("But"), Integer.valueOf(1));
  }
  
  @Test
  public void testCountOccurrencesOfText_MissingContent() throws Exception {
   ParagraphCount pc =
        this.takeHomeService.countOccurrencesOfText(new ParagraphContent());
   Assert.assertNull(pc.getWordCount());
   List<IError.Error> errors = pc.getErrors();
   Assert.assertEquals(
       errors, Arrays.asList(new IError.Error(400, "No paragraph content found")));
  }
  
  @Test
  public void testCountOccurrencesOfText_Null() throws Exception {
    ParagraphCount pc =
        this.takeHomeService.countOccurrencesOfText(null);
    Assert.assertNull(pc.getWordCount());
    List<IError.Error> errors = pc.getErrors();
    Assert.assertEquals(
        errors, Arrays.asList(new IError.Error(400, "No paragraph content found")));
  }
  
  
  @Test
  public void testCountOccurrencesOfText_EmptyText() throws Exception {
    ParagraphContent pc = new ParagraphContent();
    pc.setParagraph("");
    ParagraphCount count =
        this.takeHomeService.countOccurrencesOfText(pc);
    Assert.assertNull(count.getWordCount());
    List<IError.Error> errors = count.getErrors();
    Assert.assertEquals(
        errors, Arrays.asList(new IError.Error(400, "No paragraph content found")));
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
