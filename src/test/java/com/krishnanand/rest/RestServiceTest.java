package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

/**
 * Unit test for {@link RestService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(JUnit4.class)
public class RestServiceTest {

  @InjectMocks
  private RestService restService;

  @Mock(name = "restTemplate")
  private RestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFetchResponse() throws Exception {
    List<User> expected = new ArrayList<>();
    User user = new User();
    user.setBody("body");
    user.setTitle("title");
    user.setUserId(1L);
    user.setId(1L);
    expected.add(user);
    Mockito.when(this.restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User[].class)))
        .thenReturn(new User[] {user});
    List<User> actual = this.restService.fetchDataFromExternalService();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFetchResponse_ReturnsNull() throws Exception {
    Mockito.when(this.restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User[].class)))
        .thenReturn(null);
    List<User> actual = this.restService.fetchDataFromExternalService();
    Assert.assertEquals(new ArrayList<>(), actual);
  }
  
  @Test
  public void testFetchResponse_ReturnsEmptyList() throws Exception {
    Mockito.when(this.restTemplate.getForObject(Mockito.anyString(), Mockito.eq(User[].class)))
        .thenReturn(new User[0]);
    List<User> actual = this.restService.fetchDataFromExternalService();
    Assert.assertEquals(new ArrayList<>(), actual);
  }

}
