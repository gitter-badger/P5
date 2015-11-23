package Sampler;

import Communication.SensorState;

/**
 * Created by heider on 19/11/15.
 */
public class TraningExample {
  private StateScope input;
  private SensorState target; //Find a target representation maybe an Action instead of a state

  public TraningExample(StateScope input, SensorState target) {
    this.input = input;
    this.target = target;
  }


  /**
   * @return The pattern scope of the traning example
   */
  public StateScope getStateScope() {
    return input;
  }

  /**
   * @return The pattern scope of the traning example
   */
  public SensorState getTarget() {
    return target;
  }

}
