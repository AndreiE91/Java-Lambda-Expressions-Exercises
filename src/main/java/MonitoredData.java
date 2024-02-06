import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MonitoredData {
    LocalDateTime startTime, endTime;
    String activity;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MonitoredData(String startTime, String endTime, String activity) {
        this.startTime = LocalDateTime.parse(startTime, formatter);
        this.endTime = LocalDateTime.parse(endTime, formatter);
        this.activity = activity;
    }

    public MonitoredData() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public boolean respectSleep() {
        if(!activity.equals("Sleeping")) {
            return false;
        }
        if(endTime.minusHours(startTime.getHour()).getHour() > 6) {
            return true;
        }
        return false;
    }

    public int computeDuration() {
        return endTime.minusHours(startTime.getHour()).getHour();
    }

    public int getDay() {
        return startTime.getDayOfYear();
    }

    @Override
    public String toString() {
        return "MonitoredData{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", activity='" + activity +
                '}';
    }
}
