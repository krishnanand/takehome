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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for {@link Controller} functions.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class ControllerTest {
  
  private MockMvc mockMvc;
  
  @Autowired
  private Controller controller;
  
  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
  }
  
  @After
  public void tearDown() throws Exception {
    this.mockMvc = null;
  }
  
  @Test
  public void testHelloWorld() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/helloworld")).
        andExpect(MockMvcResultMatchers.status().isOk()).
        andExpect(MockMvcResultMatchers.content().string("Hello World"));
  }
  
  @Test
  public void testMostFrequentWords() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/frequentwords").
            contentType(MediaType.APPLICATION_JSON_UTF8).content(this.readParagraphFromFile())).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    ParagraphCount pc = mapper.readValue(response, ParagraphCount.class);
    List<Map<String, Integer>> wordCount = pc.getWordCount();
    Assert.assertEquals(wordCount.get(0).get("But"), Integer.valueOf(1));
  }
  
  @Test
  public void testMostFrequentWords_MissingParagraph() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/frequentwords").
            contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    ParagraphCount pc =
        mapper.readValue(response, ParagraphCount.class);
    Assert.assertTrue(pc.getWordCount() == null || pc.getWordCount().isEmpty());
    List<IError.Error> errors = pc.getErrors();
    Assert.assertEquals(
        Arrays.asList(new IError.Error(400, "No paragraph content found")), errors);
  }
  
  @Test
  public void testFibonacciForZeroNumber() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci").
            contentType(MediaType.APPLICATION_JSON_UTF8).param("n", "0")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    long[] output = mapper.readValue(response, long[].class);
    Assert.assertArrayEquals(output, new long[0]);
  }
  
  @Test
  public void testFibonacciForOneNumber() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci").
            contentType(MediaType.APPLICATION_JSON_UTF8).param("n", "1")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    long[] output = mapper.readValue(response, long[].class);
    Assert.assertArrayEquals(output, new long[] {0});
  }
  
  @Test
  public void testFibonacciForTwoNumbers() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci").
            contentType(MediaType.APPLICATION_JSON_UTF8).param("n", "2")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    long[] output = mapper.readValue(response, long[].class);
    Assert.assertArrayEquals(output, new long[] {0, 1});
  }
  
  @Test
  public void testFibonacciFor10Numbers() throws Exception {
    MvcResult result = 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fibonacci").
            contentType(MediaType.APPLICATION_JSON_UTF8).param("n", "10")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    long[] output = mapper.readValue(response, long[].class);
    Assert.assertArrayEquals(output, new long[] {0, 1, 1, 2, 3, 5, 8, 13, 21, 34});
  }
  
  private String readParagraphFromFile() throws Exception {
    try(InputStream is = ClassLoader.getSystemResourceAsStream("paragraph.txt");
        Scanner scanner = new Scanner(is)) {
      StringBuilder sb = new StringBuilder();
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
      }
      ParagraphContent pc = new ParagraphContent();
      pc.setParagraph(sb.toString());
      ObjectMapper reader = new ObjectMapper();
      return reader.writeValueAsString(pc);
    }
  }
}
