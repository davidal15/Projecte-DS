package baseNoStates;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Area class applies the composite pattern. In this case, each Area might be
 * a Partition or a Space. Every Area has a door list and an identifier.
 */
public abstract class Area {

  private static final Logger logger = LoggerFactory.getLogger(Area.class);

  protected final String id;
  protected List<Door> doors;

  public Area(String id, List<Door> doors) {
    this.id = id;
    this.doors = doors;

    logger.debug("Area created with id={}", id);
  }

  public String getId() {
    return id;
  }

  public void setDoors(ArrayList<Door> doors) {
    this.doors = doors;
    logger.debug("Doors list replaced for area {}", id);
  }

  /**
   * Returns the list of doors giving access to this Area.
   * If the Area is 'building', all doors are returned.
   */
  public List<Door> getDoorsGivingAccess() {

    logger.info("Computing doors giving access to area {}", id);

    List<Door> allDoors = DirectoryDoors.getAllDoors();

    if (this.id.equals("building")) {
      logger.debug("Area '{}' is building: returning all doors ({})", id, allDoors.size());
      return allDoors;
    } else {
      for (Door door : allDoors) {
        if (door.getPartition().equals(id) ||
            door.getFrom().equals(id) ||
            door.getTo().equals(id)) {

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
}
