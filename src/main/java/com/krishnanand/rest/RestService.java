package com.krishnanand.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    return
        this.restTemplate.execute(URL, HttpMethod.GET, null, new ResponseExtractor<List<User>>() {

          @Override
          public List<User> extractData(ClientHttpResponse response) throws IOException {
            InputStream is = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> users = objectMapper.readValue(is, new TypeReference<List<User>>() {});
            return users;
          }
        });
  }

}
