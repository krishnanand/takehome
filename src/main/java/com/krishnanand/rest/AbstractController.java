package com.krishnanand.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Marker controller that defines the base request URI.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController("/takehome")
public abstract class AbstractController {
  
  /**
   * Helper function to return an output response.
   * 
   * <p>The implementation assumes that there will be only one error object that is generated.
   * 
   * @param object response body
   * @return response entity
   */
  protected <T extends IError> ResponseEntity<T> generateEntity(T object) {
    if (object.getErrors() != null && !object.getErrors().isEmpty()) {
      IError.Error error = object.getErrors().get(0);
      if (error.getCode() == 400) {
        return new ResponseEntity<T>(object, HttpStatus.BAD_REQUEST);
      } else if (error.getCode() == 404) {
        return new ResponseEntity<T>(object, HttpStatus.NOT_FOUND);
      }
    }
    return new ResponseEntity<T>(object, HttpStatus.OK);
  }
}
