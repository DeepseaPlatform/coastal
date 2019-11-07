package minepump.MinePumpSystem;

public class Environment {

  public enum WaterLevelEnum { low, normal, high }

  private WaterLevelEnum waterLevel = WaterLevelEnum.normal;

  private boolean methaneLevelCritical = false;

  void lowerWaterLevel() {
    switch (waterLevel) {
    case high:
      waterLevel = WaterLevelEnum.normal;
      break;
    case normal:
      waterLevel = WaterLevelEnum.low;
      break;
    }
  }

  public void waterRise() {
    switch (waterLevel) {
    case low:
      waterLevel = WaterLevelEnum.normal;
      break;
    case normal:
      waterLevel = WaterLevelEnum.high;
      break;
    }
  }

  public void changeMethaneLevel() {
    methaneLevelCritical = !methaneLevelCritical;
  }

  public boolean isMethaneLevelCritical() { return methaneLevelCritical; }

  @Override
  public String toString() {
    return "Env(Water:" + waterLevel + ",Meth:" +
        (methaneLevelCritical ? "CRIT" : "OK") + ")";
  }

  public WaterLevelEnum getWaterLevel() { return waterLevel; }

  boolean isHighWaterSensorDry() { return waterLevel != WaterLevelEnum.high; }
}
