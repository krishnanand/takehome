package com.krishnanand.rest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.krishnanand.rest.Deadlocks.DeadlockGenerator;
import mockit.Expectations;
import mockit.Invocation;
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
    public void generateDeadlock(Invocation inv) {
      deadlockCount ++;
    }
  }
  
  class TimerMockUp extends MockUp<Timer> {
    int scheduleCount = 0;

    @Mock
    public void $init() {}

    @Mock
    public void schedule(TimerTask task, long delay) {
      scheduleCount ++;
    };
  }
  
  @Test
  public void testStartAndDetectDeadlocks() throws Exception {
    DeadlockGeneratorMockUp gen = new DeadlockGeneratorMockUp();
    TimerMockUp tmu = new TimerMockUp();
    Deadlocks.startAndDetectDeadlocks(0);
    Assert.assertEquals(1, gen.deadlockCount);
    Assert.assertEquals(1, tmu.scheduleCount);
  }

}
