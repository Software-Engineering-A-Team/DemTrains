package ctc_office;

// SimClock class for measuring the passage of simulation time
public class SimClock {
  private static int deltaMs = 10;
  
  private static long totalMs = 0;

  private static int ms = 0;
  private static int second = 0;
  private static int minute = 0;
  private static int hour = 0;
  private static int day = 0;

  // Initialize with a value to add to the time each clock tick
  public SimClock(int deltaMs) {
    this.deltaMs = deltaMs;
  }
  
  public static long getTotalMs() {
    return totalMs;
  }
  
  public static int getMs() {
    return ms;
  }
  
  public static int getSecond() {
    return second;
  }
  
  public static int getMinute() {
    return minute;
  }
  
  public static int getHour() {
    return hour;
  }
  
  public static int getDay() {
    return day;
  }

  // Add time to the clock
  public static void tick() {
    totalMs += deltaMs;

    ms += deltaMs;

    if (ms >= 1000) {
      ms -= 1000;
      second++;
      
      if (second >= 60) {
        second -= 60;
        minute++;
        
        if (minute >= 60) {
          minute -= 60;
          hour++;
          
          if (hour >= 24) {
            hour -= 24;
            day++;
            
            if (day >= 7) {
              day -= 7;
            }
          }
        }
      }
    }
  }
  
  public static int getDeltaMs() {
    return deltaMs;
  }
  
  public static double getDeltaS() {
    return (1000.0 * (double) deltaMs);
  }
}