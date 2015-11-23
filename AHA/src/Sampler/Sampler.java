package Sampler;

import Communication.SensorState;
import Communication.SensorValue;
import Sampler.DatabaseCom.DBFuncs;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by heider on 19/11/15.
 */
public class Sampler {
  private static Sampler sampler;
  private static List<SensorState> scope;

  /**
   * Initializes an object of Sampler class.
   */
  private Sampler() {
    List<SensorState> history = new ArrayList<>(); //Initialize scope
    for (int i = 0; i < 6; i++) {
      history.add(new SensorState(null,
              new SensorValue(0, false),
              new SensorValue(0, false),
              new SensorValue(0, false)
          )
      );
    }
    scope = history;
  }

  /**
   * Get instance method to ensure singleton pattern,
   *
   * @return the one and only object of the Sampler class.
   */
  public static Sampler getInstance() {
    if (sampler == null) {
      sampler = new Sampler();
    }
    return sampler;
  }

  /**
   * @return A sensor state
   */
  public StateScope getSample(SensorState state) {
    scope.remove(0); // move scope
    scope.add(state);
    StateScope history = new StateScope(scope);
    DBFuncs.putStateScopeIntoDB(history); //Log scope
    return history;
  }


  /**
   * @return A sensor state
   */
  public List<Action> findActions(SensorState state1, SensorState state2) {
    List<SensorValue> emulatable1 = findEmulatables(state1);
    List<SensorValue> emulatable2 = findEmulatables(state2);
    List<Action> actions = new ArrayList<>();
    for (int i = 0; i < emulatable1.size() - 1; i++) {//CANT ZIP Shitty java
      if (emulatable1.get(i).getValue() == emulatable2.get(i).getValue()) {
        actions.add(new Action(emulatable1.get(i), emulatable2.get(i), null, i));
      }
    }
    return actions;
  }


  public List<SensorValue> findEmulatables(SensorState state) {
    return state.getValues().stream().filter(sensorValue -> sensorValue.isEmulatable()).collect(Collectors.toList());
  }

  public TraningExample getTraningExample() {
    TraningExample tex = new TraningExample(
        new StateScope(null),
        new SensorState(
            Date.from(Instant.now()),
            new SensorValue(6, false),
            new SensorValue(7, false)
        )
    );
    return tex;
  }
}
