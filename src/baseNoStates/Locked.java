package baseNoStates;

public class Locked extends DoorState {
  public Locked() {
    this.stateName = "locked";
  }

  @Override
  public void open(Door door) {
    System.out.println("No es pot obrir, desbloqueja primer. ");
  }

  @Override
  public void close(Door door) {
    System.out.println("Ja esta tancat. ");
  }

  @Override
  public void lock(Door door) {
    System.out.println("Ja esta bloquejat. ");
  }

  @Override
  public void unlock(Door door) {
    door.setState(new Unlocked());
  }
}
