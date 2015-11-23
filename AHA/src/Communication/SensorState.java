package Communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A SensorState is a representation of sensor values in a given time snapshot.
 * The ordering of the sensor values can be assumed to not change. That is,
 * SensorState from time 1 and SensorState from time 2 will have the same ordering of sensor values.
 */
public class SensorState {
  private List<SensorValue> values = new ArrayList<>();
  private Date time;

  public SensorState(Date time1, SensorValue... values1) {
    for (int i = 0; i < values1.length; i++) {
      values.add(values1[i]);
    }
    time = time1;
  }

  /**
   * @return The time the last sensor value was recorded
   */
  public Date getTime() {
    return time;
  }

  /**
   * @return The sensor values in a given time
   */
  public List<SensorValue> getValues() {
    return values;
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i <= values.size(); i++) {
      string = string + ", " + values.toString();
    }
    return string + time.toString();
  }

  @Override
  public int hashCode() {
    return values.hashCode();
  }
}
