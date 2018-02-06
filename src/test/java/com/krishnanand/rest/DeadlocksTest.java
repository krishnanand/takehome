package com.krishnanand.rest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.krishnanand.rest.Deadlocks.DeadlockGenerator;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

/**
 * Unit test for {@link Deadlocks}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@RunWith(JMockit.class)
public class DeadlocksTest {
  
  @Mocked
  private ThreadMXBean threadMxBean;
  
  @Test
  public void testIsDeadlockAfterPeriod_Success(
      @Mocked ManagementFactory unusedFactory, @Mocked TimeUnit unit) throws Exception {
    new Expectations() {
      {
        ManagementFactory.getThreadMXBean();
        result = threadMxBean;
        TimeUnit.SECONDS.sleep(anyLong);
        
        threadMxBean.findMonitorDeadlockedThreads();
        result = new long [] {1};        
      }
    };
    Assert.assertTrue(Deadlocks.isDeadlockAfterPeriod(2));
  }
  
  @Test
  public void testIsDeadlockAfterPeriod_FailNooutput(
      @Mocked ManagementFactory unusedFactory, @Mocked TimeUnit unit) throws Exception {
    new Expectations() {
      {
        ManagementFactory.getThreadMXBean();
        result = threadMxBean;
        TimeUnit.SECONDS.sleep(anyLong);
        
        threadMxBean.findMonitorDeadlockedThreads();
        result = null;        
      }
    };
    Assert.assertFalse(Deadlocks.isDeadlockAfterPeriod(2));
  }
  
  @Test
  public void testStartAndDetectDeadlocks_Failure() throws Exception {
	  new MockUp<DeadlockGenerator>() {
		  @Mock
		  public void $init() {}
		  
		  @Mock
		  public void generateDeadlock() {
		  }
	  };
	  new Expectations(Deadlocks.class){{
		  Deadlocks.isDeadlockAfterPeriod(30);
		  result = false;
	  }};
	  Map<String, Boolean> actual = Deadlocks.startAndDetectDeadlocks(30);
	  Map<String, Boolean> expected = new LinkedHashMap<>();
	  expected.put("deadlock", false);
	  Assert.assertEquals(expected, actual);
  }

}
