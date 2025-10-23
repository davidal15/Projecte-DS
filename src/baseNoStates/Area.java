package baseNoStates;

import java.util.ArrayList;

public class Area { // Father class for Space and Partition, since each Area is either.
  private final String id;
  private ArrayList<Door> doors; // List of every door that leads to the Area

  public Area() { // Default constructor
    this.id = "";
    this.doors = new ArrayList<>();
  }

  public Area(String id) { // Constructor with id, used to create an area with a certain id
    this.id = id;
    this.doors = new ArrayList<>();
  }


  public String getId() {
    return id;
  }

  public void setDoors(ArrayList<Door> doors) { // Sets a doorList to an Area
    this.doors = doors;
  }

  public ArrayList<Door> getDoorsGivingAccess() { // Returns a list of Door included in a certain Area
    ArrayList<Door> allDoors = DirectoryDoors.getAllDoors();
    if (this.id.equals("building")) { // if the Area searched is building, all the doors are included
      return allDoors;
    } else {
      for (Door door : allDoors) {
        if (door.getPartition().equals(id) || door.getFrom().equals(id) || door.getTo().equals(id)) {
          this.doors.add(door); // if the Door pertains to this Area, it's added to the list
        }
      }
      if (this.doors.isEmpty()) {
        System.out.println("No doors found for space: " + this.id);
      }
    }

    return this.doors;
  }
}
