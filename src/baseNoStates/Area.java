package baseNoStates;

import java.util.ArrayList;

public class Area {
  private String id;
  private ArrayList<Door> doors;
  // hash map?
  public Area() {
    this.id = "";
    this.doors = new ArrayList<>();
  }

  public Area(String id) {
    this.id = id;
    this.doors = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> allDoors = DirectoryDoors.getAllDoors();
    if (this.id.equals("building")) {
      return allDoors;
    } else {
      for (Door door : allDoors) {
        if (door.getPartition().equals(id) || door.getFrom().equals(id) || door.getTo().equals(id)) {
          this.doors.add(door);
        }
      }
      if (this.doors.isEmpty()) {
        System.out.println("No doors found for space: " + this.id);
      }
    }

    return this.doors;
  }
}
