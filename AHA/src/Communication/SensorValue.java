package Communication;

/**
 * Created by simon on 17/11/2015.
 */
public class SensorValue {

  private final int value;
  private final boolean isEmulatable;

  public SensorValue(int value, boolean isEmulatable) {
    this.value = value;
    this.isEmulatable = isEmulatable;
  }

  public boolean isEmulatable() {
    return isEmulatable;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
