package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for {@link RestfulController}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class RestfulControllerTest {
  
  private MockMvc mockMvc;
  
  @InjectMocks
  private RestfulController restfulController;
  
  @Mock
  private RestService service;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.restfulController).build(); 
  }
  
  @Test
  public void testFetchDataFromExternalService() throws Exception {
    List<User> empty = new ArrayList<>();
    Mockito.when(service.fetchDataFromExternalService()).thenReturn(empty);
    MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/service/get")).
        andExpect(MockMvcResultMatchers.status().isOk()).
        andExpect(MockMvcResultMatchers.content().string("[]")).andReturn();
    ObjectMapper mapper = new ObjectMapper();
    List<User> actual =
        mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {});
    Assert.assertEquals(empty, actual);
  }

}
