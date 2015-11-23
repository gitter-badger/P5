package Sampler;

import Communication.SensorState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heider on 19/11/15.
 */
public class StateScope {
  private List<Integer> statesHashed = new ArrayList<>();

  public StateScope(List<SensorState> states) {
    for (int i = 0; i <= states.size() - 1; i++) {
      statesHashed.add(states.get(i).hashCode());
    }
  }

  public List<Integer> getStates() {
    return statesHashed;
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i <= statesHashed.size() - 1; i++) {
      if (i == 0)
        string = statesHashed.get(i).toString();
      else
        string = string + "," + statesHashed.get(i).toString();
    }
    return string;
  }
}
