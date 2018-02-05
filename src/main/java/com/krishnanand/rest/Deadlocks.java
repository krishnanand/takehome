package com.krishnanand.rest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
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
  static boolean isDeadlockAfterPeriod(int timeInSeconds) {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    DeadlockThread thread = new DeadlockThread(bean);
    Timer timer = new Timer();
    timer.schedule(thread, timeInSeconds * 1000);
    try {
      TimeUnit.SECONDS.sleep(timeInSeconds);
    } catch (InterruptedException e) {
      // Swallow the exception.
    }
    return thread.isDeadlock();
  }
  
  /**
   * Starts and detects deadlocks after {@code timeInSeconds} have elapsed.
   * 
   * @param timeInSeconds time in seconds
   * @return {@code true} if deadlock is detected; {@code false} otherwise
   * @throws InterruptedException 
   */
  static Map<String, Boolean> startAndDetectDeadlocks(int timeInSeconds)  {
    startDeadlock();
    boolean deadlock = Deadlocks.isDeadlockAfterPeriod(timeInSeconds);
    Map<String, Boolean> deadlockMap = new LinkedHashMap<>();
    deadlockMap.put("deadlock", deadlock);
    return deadlockMap;
  }
  
  private static void startDeadlock() {
    new DeadlockGenerator().generateDeadlock();
  }
  
  private static class DeadlockThread extends TimerTask {
    
    private final ThreadMXBean bean;
    
    private boolean isDeadlock;
    
    DeadlockThread(ThreadMXBean bean) {
      this.bean = bean;
    }
    
    public boolean isDeadlock() {
      return isDeadlock;
    }

    @Override
    public void run() {
      long [] deadlockThreadIds = this.bean.findMonitorDeadlockedThreads();
      this.isDeadlock = deadlockThreadIds != null && deadlockThreadIds.length > 0;
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
          }
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {}
          synchronized(secondLockObject) {
            System.out.println("Thread2 on second lock object");
          }
        }
      });
      threadOne.start();
      
      Thread threadTwo = new Thread(new Runnable() {

        @Override
        public void run() {
          synchronized(secondLockObject) {
            System.out.println("Thread2 on second lock object");
          }
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {}
          synchronized(firstLockObject) {
            System.out.println("Thread1 on first lock object");
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
