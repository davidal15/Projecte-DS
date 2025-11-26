package baseNoStates;

/** The DoorState class applies the State pattern.
*  It is an abstract class so the Door might have as many states as
*  needed without having to modify the code.
*
*  The code for every method is implemented on the child classes
*
*  The way to add a new State is simple: add a new child,
*  and implement each of the abstract methods, depending
*  on the conditions of the state
*/
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