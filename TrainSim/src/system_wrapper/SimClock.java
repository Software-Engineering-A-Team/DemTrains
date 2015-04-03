package system_wrapper;

public class SimClock {
  private int deltaMs;
  
  private long totalMs = 0;

  private int ms = 0;
  private int second = 0;
  private int minute = 0;
  private int hour = 0;
  private int day = 0;

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
}
