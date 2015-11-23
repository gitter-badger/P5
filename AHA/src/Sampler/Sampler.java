package Sampler;

import Communication.SensorState;
import Communication.SensorValue;
import Sampler.DatabaseCom.DBFuncs;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
        for (int i=0;i<6;i++) {
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
  public StateScope findAction(SensorState state1, SensorState state2) {
    List<SensorValue> emulatable1 =
    StateScope history = new StateScope(scope);
    DBFuncs.putStateScopeIntoDB(history); //Log scope
    return history;
  }

  public List<SensorValue> findEmulatables(SensorState state) {
    List<SensorValue> emulatable =
        StateScope history = new StateScope(scope);
    DBFuncs.putStateScopeIntoDB(history); //Log scope
    return history;
  }

    public TraningExample getTraningExample(){
        TraningExample tex = new TraningExample(
            new StateScope(null),
            new SensorState(
                Date.from(Instant.now()),
                new SensorValue(6,false),
                new SensorValue(7,false)
            )
        );
        return tex;
    }
}
