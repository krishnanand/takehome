package com.krishnanand.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.VarargMatcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;



/**
 * Unit test for {@link RestService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class RestServiceTest {

  @InjectMocks
  private RestService restService;
  
  @Mock(name="restTemplate")
  private RestTemplate restTemplate;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }
  
  class MyVarargMatcher implements ArgumentMatcher<Object[]>, VarargMatcher {

    @Override
    public boolean matches(Object[] argument) {
      return true;
    }
  }
  
  public class ClassOrSubclassMatcher<T> implements ArgumentMatcher<Class<T>> {

    private final Class<T> targetClass;

    public ClassOrSubclassMatcher(Class<T> targetClass) {
      this.targetClass = targetClass;
    }

    @Override
    public boolean matches(Class<T> argument) {
      return argument.getClass().isAssignableFrom(this.targetClass);
    }
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
    Mockito.when(this.restTemplate.getForObject(
        Mockito.anyString(),
        Mockito.argThat(new ClassOrSubclassMatcher<User[]>(User[].class)))).
            thenReturn(new User[] {user});
    List<User> actual = this.restService.fetchDataFromExternalService();
    Assert.assertEquals(expected, actual);
  }

}
