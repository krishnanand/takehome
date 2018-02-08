package com.krishnanand.rest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Utility function that initiates deadlocks in threads.
 */
public class Deadlocks {
  
  private Deadlocks() {
    throw new AssertionError("Not to be instantiated");
  }
  
  /**
   * Check if the thread is deadlocked after a period.
   * 
   * @param timeInSeconds time in seconds
   * @return {@code true} if the thread is deadlocked; {@code false} otherwise
   */
  static boolean isDeadlockAfterPeriod() {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    DeadlockThread dt = new DeadlockThread(bean);
    return dt.numberOfDeadlockedThreads() > 0;
  }
  
  /**
   * Initiates a deadlock and checks if it still exists after {@code timeInSeconds} have elapsed.
   * 
   * @param timeInSeconds time in seconds
   * @return {@code true} if deadlock is detected; {@code false} otherwise
   */
  static Map<String, Boolean> startAndDetectDeadlocks(int timeInSeconds) {
    new DeadlockGenerator().generateDeadlock();
    try {
      Thread.sleep(timeInSeconds * 1000);
    } catch (InterruptedException e) {
      return Collections.emptyMap();
    }
    final Map<String, Boolean> deadlockMap = new LinkedHashMap<>();
    new Timer().schedule(new TimerTask() {

      @Override
      public void run() {
        deadlockMap.put("deadlock", isDeadlockAfterPeriod());
      }

    }, timeInSeconds * 1000);
    return deadlockMap;
  }
  
  /**
   * An instance of this class checks for number of dead locked threads.
   *
   */
  static class DeadlockThread  {
    
    private final ThreadMXBean bean;

    DeadlockThread(ThreadMXBean bean) {
      this.bean = bean;
    }
    
    /**
     * Returns the the number of dead locked threads.
     * @return number of deadlocked threads
     */
    public int numberOfDeadlockedThreads() {
      long [] deadlockThreadIds = this.bean.findMonitorDeadlockedThreads();
      return deadlockThreadIds == null ? 0 : deadlockThreadIds.length;
    }
  }
  
  /**
   * Instance of this class encapsulates functionality to trigger deadlocks.
   */
  static class DeadlockGenerator {
    private Object firstLockObject = new Object();
    private Object secondLockObject = new Object();
    
    public void generateDeadlock() {
      Thread threadOne = new Thread(new Runnable() {

        @Override
        public void run() {
          synchronized(firstLockObject) {
            System.out.println("Thread1 on first lock object");
            synchronized(secondLockObject) {
              System.out.println("Thread2 on second lock object");
            }
          }
        }
      });
      threadOne.start();
      
      Thread threadTwo = new Thread(new Runnable() {

        @Override
        public void run() {
          synchronized(secondLockObject) {
            System.out.println("Thread2 on second lock object");
            synchronized(firstLockObject) {
              System.out.println("Thread1 on first lock object");
            }
          }
        }
      });
      threadTwo.start();
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {}
    }
  }

}
