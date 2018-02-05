package com.krishnanand.rest;

import java.util.Set;

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
  
  @GetMapping("/service/get")
  @ResponseBody
  public Set<User> fetchDataFromExternalService() throws Exception {
    return null;
  }
}
