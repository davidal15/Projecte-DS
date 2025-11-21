package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public final class DirectorySpaces {
  private static List<Space> allSpaces;
  private static final String[] spaceNames = {
      "parking", "room1", "room2", "room3",
      "hall", "IT", "corridor", "stairs", "exterior"
  };

  public static void makeSpaces() {
    allSpaces = new ArrayList<>();
    for (String name : spaceNames) {
      List<Door> doors = findDoorBySpaceId(name);
      Space s = new Space(name, doors);
      allSpaces.add(s);
    }
  }

  public static List<Door> findDoorBySpaceId(String spaceId) { // Returns a list of Door that lead to the Space by parameter
    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getAllDoors();

    for (Door door : allDoors) {
      if (door.getFrom().equals(spaceId) || door.getTo().equals(spaceId)) { // if the Door goes from or to the Space
        doorList.add(door); // it's added to the list
      }
    }

    if (doorList.isEmpty()) {
      System.out.println("No doors found for space: " + spaceId);
    }
    return doorList;
  }

  public static List<Space> getAllSpaces() {
    return allSpaces;
  }
}
