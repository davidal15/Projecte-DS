package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Locked is one of the states a Door can be in.
 * Represents a blocked door that cannot be opened until unlocked.
 * Logging added to replace System.out.println usage.
 */
public class Locked extends DoorState {

  private static final Logger logger = LoggerFactory.getLogger(Locked.class);

  public Locked() {
    this.stateName = "locked";
  }

  @Override
  public void open(Door door) {
    logger.warn("Attempt to OPEN door '{}' while in state '{}': action denied",
        door.getId(), stateName);
  }

  @Override
  public void close(Door door) {
    logger.warn("Attempt to CLOSE door '{}' while already closed and locked",
        door.getId());
  }

  @Override
  public void lock(Door door) {
    logger.warn("Attempt to LOCK door '{}' but it is already locked",
        door.getId());
  }

  @Override
  public void unlock(Door door) {
    logger.info("Door '{}' unlocked (transitioning from '{}' to 'unlocked')",
        door.getId(), stateName);
    door.setState(new Unlocked());
  }
}
