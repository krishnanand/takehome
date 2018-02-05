package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;


/**
 * Unit test for {@link RestService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class RestServiceTest {
  
  @Autowired
  private RestService restService;
  
  @InjectMocks
  private RestTemplate restTemplate;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testFetchResponse() throws Exception {
    List<User> expected = new ArrayList<>();
    User user = new User();
    user.setBody("body");
    user.setTitle("title");
    user.setUserId(1L);
    user.setId(1L);
    expected.add(user);
    Mockito.when(this.restTemplate.execute(
        Mockito.anyString(), Mockito.eq(HttpMethod.GET), (RequestCallback) Mockito.isNull(),
        Mockito.any(ResponseExtractor.class))).thenReturn(expected);
    List<User> actual = this.restService.fetchDataFromExternalService();
    Assert.assertEquals(expected, actual);
  }

}
