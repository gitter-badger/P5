package Sampler;

import Communication.SensorState;
import Sampler.DatabaseCom.DBFuncs;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heider on 19/11/15.
 */
public class Sampler {

    /**
     * @return The time the last sensor value was recorded
     */
    public List<SensorState> getHistoryOfAGivenState(SensorState state) {
        List<SensorState> history = new ArrayList<>();
        return history;
    }

    /**
     * @return The patternscope for given state
     */
    public StateHistory getStateHistoryOfState(SensorState state) {
        List<SensorState> history = getHistoryOfAGivenState(state);
        StateHistory pat = new StateHistory(history);
        return pat;
    }

    public StateHistory getMostRecentStateHistory(){
        return DBFuncs.getStateHistoryFromDBByDate(Date.from(Instant.now())); // Refactor closest to timenow
    }

    public TraningExample getTraningExample(){
        TraningExample tex = new TraningExample(,new SensorState());
        return tex;
    }
}
