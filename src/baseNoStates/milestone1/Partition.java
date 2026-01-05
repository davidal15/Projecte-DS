package baseNoStates.milestone1;

import baseNoStates.milestone2.AreaVisitor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Child of Area. The Partitions might include Spaces or other Partitions.
public class Partition extends Area {

  private static final Logger logger =
      LoggerFactory.getLogger(Partition.class);

  public Partition(String id, List<Door> doors) {
    super(id, doors);
  }

  /**
   * Returns all doors giving access to this partition.
   * NOT used for navigation, only for actions (lock/unlock).
   */
  @Override
  public List<Door> getDoorsGivingAccess() {
    logger.info("Computing doors giving access to area {}", id);

    doors.clear();

    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    if (id.equals("building")) {
      logger.debug("Area '{}' is building: returning all doors ({})",
          id, allDoors.size());
      doors.addAll(allDoors);
      return doors;
    }

    for (Door door : allDoors) {
      if (id.equals(door.getPartition())) {
        doors.add(door);
        logger.debug("Door {} provides access to area {}", door.getId(), id);
      }
    }

    if (doors.isEmpty()) {
      logger.warn("No doors found for area: {}", id);
    }

    return doors;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }

  /**
   * JSON representation used by RequestChildren.
   * Includes ONLY direct child areas (no doors).
   */
  @Override
  public JSONObject toJson(int depth) {
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", id);
    return json;
  }

}
