package com.krishnanand.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for {@link PersonService}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(JUnit4.class)
public class PersonServiceTest {
  private PersonService ps;
  
  @Mock
  private PersonDao personDao;
  
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.ps = new PersonService(this.personDao);
  }
  
  @Test
  public void testCreatePerson() throws Exception {
    Person person = new Person();
    person.setFirstName("First");
    person.setLastName("Last");
    Mockito.when(this.personDao.createPerson(person)).thenReturn("ABCDE12345");
    PersonCredentials actual = ps.createPerson(person);
    Assert.assertEquals(actual.getPersonId(), "ABCDE12345");
  }
  
  @Test
  public void testFindPersonByPersonId_Success() throws Exception {
    Person expected = new Person();
    expected.setFirstName("First");
    expected.setLastName("Last");
    Mockito.when(this.personDao.findPersonById("ABCDE12345")).thenReturn(expected);
    Person actual = ps.findPersonByPersonId("ABCDE12345");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFindPersonByPersonId_Fail() throws Exception {
    Person expected = new Person();
    expected.addError(404, "No record was found.");
    Mockito.when(this.personDao.findPersonById("")).thenReturn(expected);
    Person actual = ps.findPersonByPersonId("");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonByPersonId_Fail() throws Exception {
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.addError(404, "No record was found.");
    Mockito.when(this.personDao.deletePersonByPersonId("")).thenReturn(0);
    PersonDeleteResponse actual = ps.deletePersonById("");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testDeletePersonByPersonId_Success() throws Exception {
    PersonDeleteResponse expected = new PersonDeleteResponse();
    expected.setDeleteSuccess(true);
    Mockito.when(this.personDao.deletePersonByPersonId("ABCDE12345")).thenReturn(1);
    PersonDeleteResponse actual = ps.deletePersonById("ABCDE12345");
    Assert.assertEquals(expected, actual);
  }
}
