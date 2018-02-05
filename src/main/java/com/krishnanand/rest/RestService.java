package com.krishnanand.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * An instance of this class encapsulates all functions that invoke third party services.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Component
public class RestService implements IRestService {
  
  private static final String URL = "https://jsonplaceholder.typicode.com/posts";
  
  private final RestTemplate restTemplate;
  
  @Autowired
  public RestService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Fetches content from an external URI.
   */
  @Override
  public List<User> fetchDataFromExternalService() {
    return Arrays.asList(this.restTemplate.getForObject(URL, User[].class));
  }

}
