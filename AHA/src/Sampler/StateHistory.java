package Sampler;

import Communication.SensorState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heider on 19/11/15.
 */
public class StateHistory {
    private List<SensorState> states = new ArrayList<>();

    public StateHistory(List<SensorState> states) {
        this.states = states;
    }

    public SensorState getHead(){
        return states.get(0);
    }

    public List<SensorState> getStates(){
        return states;
    }
}
