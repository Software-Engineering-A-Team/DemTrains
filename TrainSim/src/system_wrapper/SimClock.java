package system_wrapper;

// SimClock class for measuring the passage of simulation time
public class SimClock {
  private int deltaMs;
  
  private long totalMs = 0;

  private int ms = 0;
  private int second = 0;
  private int minute = 0;
  private int hour = 0;
  private int day = 0;

  // Initialize with a value to add to the time each clock tick
  public SimClock(int deltaMs) {
    this.deltaMs = deltaMs;
  }
  
  public long getTotalMs() {
    return totalMs;
  }
  
  public int getMs() {
    return ms;
  }
  
  public int getSecond() {
    return second;
  }
  
  public int getMinute() {
    return minute;
  }
  
  public int getHour() {
    return hour;
  }
  
  public int getDay() {
    return day;
  }

  // Add time to the clock
  public void tick() {
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
  
  public int getDeltaMs() {
    return deltaMs;
  }
  
  public double getDeltaS() {
    return (1000.0 * (double) deltaMs);
  }
}