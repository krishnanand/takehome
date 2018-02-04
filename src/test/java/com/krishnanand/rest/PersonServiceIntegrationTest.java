package com.krishnanand.rest;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for {@link PersonService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/initial.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class PersonServiceIntegrationTest {
  
  @Autowired
  private PersonService personService;
  
  @Autowired
  private DataSource dataSource;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }
  
  @Test
  public void testCreatePerson_IntegrationTest() throws Exception {
    Person person = new Person();
    person.setFirstName("First");
    person.setLastName("Last");
    PersonCredentials actual = this.personService.createPerson(person);
    Assert.assertNotNull(actual.getPersonId());
    Assert.assertEquals(this.fetchPersonById(actual.getPersonId()), person);
  }
  
  private Person fetchPersonById(String personId) {
    // Verification
    Person expected = this.jdbcTemplate.query(
        "SELECT first_name, last_name FROM Person WHERE person_id = ?",
        new Object[] {personId},
        new ResultSetExtractor<Person>() {

          @Override
          public Person extractData(ResultSet rs)
              throws SQLException, DataAccessException {
            while(rs.next()) {
              Person p = new Person();
              p.setFirstName(rs.getString("first_name"));
              p.setLastName(rs.getString("last_name"));
              return p;
            }
            return null;
          }
          
        });
    return expected;
  }
  
  @Test
  public void testFindPersonByPersonId() throws Exception {
    Person actual = this.personService.findPersonByPersonId("ABCDE12345");
    Person expected = new Person();
    expected.setFirstName("Jane");
    expected.setLastName("Doe");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFindPersonByPersonId_PersonNotFound() throws Exception {
    Person actual = this.personService.findPersonByPersonId("");
    Person expected = new Person();
    expected.addError(404, "No record was found for the input.");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeleteByPersonId() throws Exception {
    // Checking that record exists.
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Doe");
    String personId = "ABCDE12345";
    Person personFromDb = this.fetchPersonById(personId);
    Assert.assertEquals(person, personFromDb);
    
    // Deleting from db.
    PersonDeleteResponse actual = this.personService.deletePersonById("ABCDE12345");
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.setDeleteSuccess(true);
    Assert.assertEquals(expected, actual);
    
    // Checking that the record is deleted.
    Assert.assertNull(this.fetchPersonById(personId));
  }
  
  @Test
  public void testDeleteByPersonId_MissingId() throws Exception {
    // Checking no record exists.
    Assert.assertNull(this.fetchPersonById(""));
    
    // Nothing to be deleted.
    PersonDeleteResponse actual = this.personService.deletePersonById("");
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.addError(404, "No record was found for the input.");
    Assert.assertEquals(expected, actual);
  }
}
