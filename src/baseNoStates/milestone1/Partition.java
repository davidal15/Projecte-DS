package baseNoStates.milestone1;


import baseNoStates.milestone2.AreaVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Child of Area. The Partitions might include Spaces in them.
public class Partition extends Area {

  private static final Logger logger = LoggerFactory.getLogger(Partition.class);
  // Both constructors use Area's

  public Partition(String id, List<Door> doors) {
    super(id, doors);
  }

  @Override
  public List<Door> getDoorsGivingAccess() {
    logger.info("Computing doors giving access to area {}", id);

    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();
    if (this.id.equals("building")) {
      logger.debug("Area '{}' is building: returning all doors ({})", id, allDoors.size());
      return allDoors;
    } else {
      for (Door door : allDoors) {
        if (door.getPartition().equals(id)) {
          this.doors.add(door);
          logger.debug("Door {} provides access to area {}", door.getId(), id);
        }
      }

      if (this.doors.isEmpty()) {
        logger.warn("No doors found for area: {}", this.id);
      }
    }
    return this.doors;
  }
  /**
   * Accepts a visitor.
   * This allows hierarchical navigation without adding traversal logic
   * inside domain model methods.
   */
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }

}