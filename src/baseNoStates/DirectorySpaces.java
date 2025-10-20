package baseNoStates;

import java.util.ArrayList;

public final class DirectorySpaces {
  private static ArrayList<Space> allSpaces;
  private static final String[] spaceNames = {
      "parking", "room1", "room2", "room3",
      "hall", "IT", "corridor", "stairs", "exterior"
  };

  public static void makeSpaces() {
    allSpaces = new ArrayList<>();
    for (String name : spaceNames) {
      ArrayList<Door> doors = findDoorBySpaceId(name);
      Space s = new Space(name, doors);
      allSpaces.add(s);
    }
  }

  public static ArrayList<Door> findDoorBySpaceId(String spaceId) { // Returns a list of Door that lead to the Space by parameter
    ArrayList<Door> doorList = new ArrayList<>();
    ArrayList<Door> allDoors = DirectoryDoors.getAllDoors();

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

  public static ArrayList<Space> getAllSpaces() {
    return allSpaces;
  }
}
