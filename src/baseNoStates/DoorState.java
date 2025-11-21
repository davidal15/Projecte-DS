package baseNoStates;

public abstract class DoorState {
  protected String stateName;

  public abstract void open(Door door);
  public abstract void close(Door door);
  public abstract void lock(Door door);
  public abstract void unlock(Door door);

  public String getStateName() {
    return this.stateName;
  }
}