package com.krishnanand.rest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
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
  public void testIsDeadlockAfterPeriod_Success(@Mocked ManagementFactory unusedFactory,
      @Mocked TimeUnit unit) throws Exception {
    new Expectations() {
      {
        ManagementFactory.getThreadMXBean();
        result = threadMxBean;
        threadMxBean.findMonitorDeadlockedThreads();
        result = new long[] {1};
      }
    };
    Assert.assertTrue(Deadlocks.isDeadlockAfterPeriod());
  }

  @Test
  public void testIsDeadlockAfterPeriod_FailNooutput(@Mocked ManagementFactory unusedFactory)
      throws Exception {
    new Expectations() {
      {
        ManagementFactory.getThreadMXBean();
        result = threadMxBean;
        threadMxBean.findMonitorDeadlockedThreads();
        result = null;
      }
    };
    Assert.assertFalse(Deadlocks.isDeadlockAfterPeriod());
  }
  
  class DeadlockGeneratorMockUp extends MockUp<DeadlockGenerator> {
    int deadlockCount = 0;
    @Mock
    public void $init() {}

    @Mock
    public void generateDeadlock() {
      deadlockCount ++;
    }
  }
  
  @Test
  public void testStartAndDetectDeadlocks(@Mocked final Thread thread) throws Exception {
    new Expectations() {
      {
        Thread.sleep(0);
      }
    };
    DeadlockGeneratorMockUp gen = new DeadlockGeneratorMockUp();
    Deadlocks.startAndDetectDeadlocks(0);
    Assert.assertEquals(1, gen.deadlockCount);
  }

}
