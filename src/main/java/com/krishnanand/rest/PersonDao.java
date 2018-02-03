package com.krishnanand.rest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of data access object pattern to provide access to the {@code Person} table.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Repository
public class PersonDao implements IPersonDao {
  
  private final DataSource dataSource;
  
  private final JdbcTemplate jdbcTemplate;
  
  private final RandomStringGenerator generator;
  
  @Autowired
  public PersonDao(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.generator = new RandomStringGenerator(10);
  }

  /**
   * Inserts a person into the database upto 5 times.
   * 
   * @param person person object
   * @return unique person identifier
   */
  @Override
  public String createPerson(Person person) {
    String sql =
        new StringBuilder("INSERT INTO Person(person_id, ").
            append("first_name, last_name) VALUES(?, ?, ?)").toString();
    int retry = 0;
    boolean isRegistrationSuccessful = false;
    while (!isRegistrationSuccessful && retry < 5) {
      String personId = this.generator.nextString();
      // Check if it exists. Ideally, this would be generated by an external
      // distributed tool like the ZooKeeper.
      int count = 0;
      try {
        count = this.jdbcTemplate.update(
            sql, new PreparedStatementSetter() {
              
              @Override
              public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, personId);
                ps.setString(2, person.getFirstName());
                ps.setString(3,  person.getLastName());
              }
            });
      } catch (DataAccessException dae) {
        retry ++;
        continue;
      }
      // Insert successful.
      if (count == 1) {
        return personId;
      }
    }
    return null;
  }
  
  /**
   * Deletes person by unique person identifier.
   * 
   * @param personId unique person identifier
   */
  @Override
  public int deletePersonByPersonId(String personId) {
    return this.jdbcTemplate.update(
        "DELETE FROM Person where person_id = ?", new Object[] {personId});
  }

  /**
   * Return all {@code Person} records by last name.
   * 
   * @param person id unique identifier associated with person
   */
  @Override
  public Person findPersonById(String personId) {
    String sql =
        "SELECT first_name, last_name FROM PERSON where person_id = ?";
    return this.jdbcTemplate.query(
        sql, new Object[] {personId},
        new ResultSetExtractor<Person>() {

          @Override
          public Person extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
              Person person = new Person();
              person.setFirstName(rs.getString("first_name"));
              person.setLastName(rs.getString("last_name"));
              return person;
            }
            return null;
          }
    });
  }
}
