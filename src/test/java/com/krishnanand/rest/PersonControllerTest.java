package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Unit test for {@link PersonController}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/initial.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class PersonControllerTest {
  
  @Autowired
  private PersonController personController;
  
  private MockMvc mockMvc;
  
  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.personController).build();
  }
  
  @Test
  public void testInsertPerson() throws Exception {
    Person person = new Person();
    person.setFirstName("Tom");
    person.setLastName("Brady");
    String json = new ObjectMapper().writeValueAsString(person);
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.post("/person/add").
                contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonCredentials pc = mapper.readValue(response, PersonCredentials.class);
    Assert.assertNotNull(pc.getPersonId());
    Assert.assertTrue(pc.getErrors().isEmpty());
  }
  
  @Test
  public void testInsertPerson_NullValue() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.post("/person/add").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonCredentials pc = mapper.readValue(response, PersonCredentials.class);
    Assert.assertNull(pc.getPersonId());
    List<IError.Error> expected = new ArrayList<>();
    expected.add(
        new IError.Error(
            400,
            "The input was invalid. Please check if either first name or last name is missing."));
    Assert.assertEquals(expected, pc.getErrors());
  }
  
  @Test
  public void testInsertPerson_MissingLastName() throws Exception {
    Person person = new Person();
    person.setFirstName("Tom");
    String json = new ObjectMapper().writeValueAsString(person);
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.post("/person/add").
                contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonCredentials pc = mapper.readValue(response, PersonCredentials.class);
    Assert.assertNull(pc.getPersonId());
    List<IError.Error> expected = new ArrayList<>();
    expected.add(
        new IError.Error(
            400,
            "The input was invalid. Please check if either first name or last name is missing."));
    Assert.assertEquals(expected, pc.getErrors());
  }
  
  @Test
  public void testInsertPerson_MissingFirstName() throws Exception {
    Person person = new Person();
    person.setLastName("Brady");
    String json = new ObjectMapper().writeValueAsString(person);
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.post("/person/add").
                contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonCredentials pc = mapper.readValue(response, PersonCredentials.class);
    Assert.assertNull(pc.getPersonId());
    List<IError.Error> expected = new ArrayList<>();
    expected.add(
        new IError.Error(
            400,
            "The input was invalid. Please check if either first name or last name is missing."));
    Assert.assertEquals(expected, pc.getErrors());
  }
  
  @Test
  public void testFetchPersonByPersonId_Success() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/get").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "ABCDE12345")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Person actual = mapper.readValue(response, Person.class);
    Person expected = new Person();
    expected.setFirstName("Jane");
    expected.setLastName("Doe");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFetchPersonByPersonId_NoDataFound() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/get").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "wrong")).
            andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Person actual = mapper.readValue(response, Person.class);
    Person expected = new Person();
    expected.addError(404, "No record was found for the input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFetchPersonByPersonId_EmptyStringQueryParameter() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/get").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "")).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Person actual = mapper.readValue(response, Person.class);
    Person expected = new Person();
    expected.addError(400, "The input was invalid. Please recheck input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFetchPersonByPersonId_MissingQueryParameters() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/get").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Person actual = mapper.readValue(response, Person.class);
    Person expected = new Person();
    expected.addError(400, "The input was invalid. Please recheck input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonById_MissingQueryParameters() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Person actual = mapper.readValue(response, Person.class);
    Person expected = new Person();
    expected.addError(400, "The input was invalid. Please recheck input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonById_InvalidQueryParameters() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "")).
            andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonDeleteResponse actual = mapper.readValue(response, PersonDeleteResponse.class);
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.addError(400, "The input was invalid. Please recheck input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonById_NoDataFound() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "wrong")).
            andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonDeleteResponse actual = mapper.readValue(response, PersonDeleteResponse.class);
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.addError(404, "No record was found for the input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonById_Success() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "ABCDE12345")).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    PersonDeleteResponse actual = mapper.readValue(response, PersonDeleteResponse.class);
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.setSuccess(true);
    Assert.assertEquals(expected, actual);
    
    // Checking if the record is truly deleted.
    MvcResult verifyResult =
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete").
                contentType(MediaType.APPLICATION_JSON_UTF8).param("personId", "ABCDE12345")).
            andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    String verifyResponse = new String(verifyResult.getResponse().getContentAsByteArray());
    ObjectMapper verifyMapper = new ObjectMapper();
    PersonDeleteResponse verifyActual =
        verifyMapper.readValue(verifyResponse, PersonDeleteResponse.class);
    PersonDeleteResponse verifyExpected = new PersonDeleteResponse();
    verifyExpected.addError(404, "No record was found for the input.");
    Assert.assertEquals(verifyExpected, verifyActual);
  }
}
