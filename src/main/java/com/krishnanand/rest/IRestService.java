package com.krishnanand.rest;

import java.util.List;

/**
 * A strategy implementation of this interface will encapsulate functions that will invoke a third
 * party service.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public interface IRestService {
  
  /**
   * Invokes a third party URI to fetch responses.
   * 
   * @return list of json objects representing the users
   */
  List<User> fetchDataFromExternalService();
}
