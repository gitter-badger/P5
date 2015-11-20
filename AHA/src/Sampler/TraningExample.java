package Sampler;

import Communication.SensorState;

/**
 * Created by heider on 19/11/15.
 */
public class TraningExample {
    private StateHistory pat;
    private SensorState target; //Find a target representation maybe an Action instead of a state

    public TraningExample(StateHistory pat, SensorState target) {
        this.pat = pat;
        this.target = target;
    }


    /**
     * @return The pattern scope of the traning example
     */
    public StateHistory getPatternScope() {
        return pat;
    }

    /**
     * @return The pattern scope of the traning example
     */
    public SensorState getTarget() {
        return target;
    }

}
