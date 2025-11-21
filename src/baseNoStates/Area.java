package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public abstract class Area {
  protected final String id;
  protected List<Door> doors;

  public Area(String id, List<Door> doors) {
    this.id = id;
    this.doors = doors;
  }

  public String getId() {
    return id;
  }

  public void setDoors(ArrayList<Door> doors) {
    this.doors = doors;
  }

  public List<Door> getDoorsGivingAccess() {
    List<Door> allDoors = DirectoryDoors.getAllDoors();

    if (this.id.equals("building")) {
      return allDoors;
    } else {
      for (Door door : allDoors) {
        if (door.getPartition().equals(id) ||
            door.getFrom().equals(id) ||
            door.getTo().equals(id)) {
          this.doors.add(door);
        }
      }
      if (this.doors.isEmpty()) {
        System.out.println("No doors found for area: " + this.id);
      }
    }

    return this.doors;
  }
}
