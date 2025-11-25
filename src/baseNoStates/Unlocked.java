package baseNoStates;

/* Unlocked is one of the states Door can be in
 *  This class is a child of DoorState, implementing
 *  each of it's methods
 */

public class Unlocked extends DoorState {
  public Unlocked() {
    this.stateName = "unlocked";
  }

  @Override
  public void open(Door door) {
    door.open();
  }

  @Override
  public void close(Door door) {
    door.close();
  }

  @Override
  public void lock(Door door) {
    door.setState(new Locked());
  }

  @Override
  public void unlock(Door door) {
    System.out.println("Ja esta desbloquejat. ");
  }
}
