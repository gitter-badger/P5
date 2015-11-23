package Sampler;

import Communication.SensorValue;

import java.sql.Time;

/**
 * Created by deter on 23-Nov-15.
 */
public class Action {
  private SensorValue s1;
  private SensorValue s2;
  private Time time;
  private int sensorId; //What device

  public Action(SensorValue s1, SensorValue s2, Time time, int sensorId) {
    this.s1 = s1;
    this.s2 = s2;
    this.time = time;
    this.sensorId = sensorId;
  }

  public int getDevice() {
    return sensorId;
  }

  public Time getTime() {
    return time;
  }

  public int getChange() {
    return s2.getValue() - s1.getValue();
  }
}
