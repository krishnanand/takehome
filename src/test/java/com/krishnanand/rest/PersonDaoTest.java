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
 * Unit test for {@link PersonDao}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/initial.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/cleanup.sql")
public class PersonDaoTest {
  
  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private PersonDao personDao;
  
  private JdbcTemplate jdbcTemplate;
  
  @Before
  public void setUp() throws Exception {
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
  }
  
  @Test
  public void testCreatePerson() throws Exception {
    Person person = new Person();
    person.setFirstName("First");
    person.setLastName("Last");;
    String personId = this.personDao.createPerson(person);
    
    Person actual = this.fetchPersonById(personId);
    Assert.assertEquals(person, actual);
  }
  
  @Test
  public void testFindPersonById() throws Exception {
    Person expected = new Person();
    expected.setFirstName("Jane");
    expected.setLastName("Doe");
    Person actual = this.personDao.findPersonById("ABCDE12345");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonById() throws Exception {
    Person expected = new Person();
    expected.setFirstName("Jane");
    expected.setLastName("Doe");
    String personId = "ABCDE12345";
    Assert.assertEquals(expected, this.fetchPersonById(personId));
    Assert.assertEquals(1,  this.personDao.deletePersonByPersonId(personId));
    Assert.assertNull(this.fetchPersonById(personId));
    
  }
  
  private Person fetchPersonById(String personId) {
    Person actual =
        this.jdbcTemplate.query(
            "SELECT first_name, last_name FROM Person where person_id = ? ",
            new Object[] {personId},
            new ResultSetExtractor<Person>() {

              @Override
              public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
                while(rs.next()) {
                  Person person = new Person();
                  person.setFirstName(rs.getString("first_name"));
                  person.setLastName(rs.getString("last_name"));
                  return person;
                }
                return null;
              }
            });
    return actual;
  }

}
