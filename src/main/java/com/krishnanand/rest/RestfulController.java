package com.krishnanand.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * An instance of this class is responsible for invoking third party services.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController
public class RestfulController extends AbstractController {
  
  private final IRestService restService;
  
  @Autowired
  public RestfulController(IRestService restService) {
    this.restService = restService;
  }
  
  @GetMapping("/service/get")
  @ResponseBody
  public List<User> fetchDataFromExternalService() throws Exception {
    return this.restService.fetchDataFromExternalService();
  }
}
