package dk.sw501e15;

import Communication.DataReceiver;
import Communication.SensorState;
import Communication.SensorValue;
import Sampler.Sampler;

public class Main {

  public static void main(String[] args) {
    DataReceiver receiver = new DataReceiver();
    //Communicator com = new Communicator("/dev/ttyUSB0", 9600, receiver);
    //DBFuncs.createDB();
    Sampler sampler = Sampler.getInstance();
    sampler.getSample(new SensorState(null,
        new SensorValue(52, false),
        new SensorValue(121, false),
        new SensorValue(4215, false)));
    sampler.getSample(new SensorState(null,
        new SensorValue(2213, false),
        new SensorValue(132, false),
        new SensorValue(46, false)));
  }
}