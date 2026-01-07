package baseNoStates.milestone1;

import baseNoStates.milestone2.AreaVisitor;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
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

    doors.clear();

    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    for (Door door : allDoors) {
      if (door.getFrom().equals(id) || door.getTo().equals(id)) {

        doors.add(door);
        logger.debug("Door {} provides access to area {}", door.getId(), id);
      }
    }

    if (doors.isEmpty()) {
      logger.warn("No doors found for area: {}", this.id);
    }
    return doors;
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

  @Override
  public JSONObject toJson(int depth) {
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", id);

    json.put("locked", isLocked());
    json.put("unlocked", isUnlocked());

    JSONArray jsonDoors = new JSONArray();
    for (Door d : getDoorsGivingAccess()) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }

  @Override
  public boolean isLocked() {
    for (Door d : getDoorsGivingAccess()) {
      if (!d.isLocked()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isUnlocked() {
    for (Door d : getDoorsGivingAccess()) {
      if (!d.isUnlocked()) {
        return false;
      }
    }
    return true;
  }
}