package baseNoStates.milestone1;

import baseNoStates.milestone2.AreaVisitor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Child of Area. It's the smallest possible type of Area.
public class Space extends Area {

  private static final Logger logger = LoggerFactory.getLogger(Space.class);
  // Both constructors use Area's

  public Space(String id, List<Door> doors) {
    super(id, doors);
  }

  @Override
  public List<Door> getDoorsGivingAccess() {
    logger.info("Computing doors giving access to area {}", id);

    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    for (Door door : allDoors) {
      if (door.getFrom().equals(id) || door.getTo().equals(id)) {

        this.doors.add(door);
        logger.debug("Door {} provides access to area {}", door.getId(), id);
      }
    }

    if (this.doors.isEmpty()) {
      logger.warn("No doors found for area: {}", this.id);
    }
    return this.doors;
  }
  /**
   * Accepts a visitor that performs an operation on this Space instance.
   * Spaces do not delegate further traversal since they represent leaf nodes
   * in the building hierarchy.
   */

  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitSpace(this);

  }
}