package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unlocked is one of the states a Door can be in.
 * Represents a door that can be opened while unlocked.
 */
public class Unlocked extends DoorState {

  private static final Logger logger = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked() {
    this.stateName = "unlocked";
  }

  @Override
  public void open(Door door) {
    logger.info("Door '{}' opened", door.getId());
    door.open();
  }

  @Override
  public void close(Door door) {
    logger.info("Door '{}' closed", door.getId());
    door.close();
  }

  @Override
  public void lock(Door door) {
    logger.info("Door '{}' locked", door.getId());
    door.setState(new Locked());
  }

  @Override
  public void unlock(Door door) {
    logger.warn("Door '{}' is already unlocked", door.getId());
  }
}
