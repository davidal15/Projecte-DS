package baseNoStates;

import java.util.ArrayList;

public final class DirectorySpaces {
  private static ArrayList<DirectorySpaces> allSpaces;

  public static void makeSpaces() {
    String[] spaceNames = {
        "parking", "room1", "room2", "room3",
        "hall", "IT", "corridor", "stairs", "exterior"
    };
    for (String name : spaceNames) {
      new Space(name, findDoorBySpaceId(name));
    }
  }

  public static ArrayList<Door> findDoorBySpaceId(String spaceId) { // returns a list with all the doors that connect with selected Space
    ArrayList<Door> doorList = new ArrayList<>(); // list of doors for each space
    ArrayList<Door> allDoors = new ArrayList<>();
    allDoors = DirectoryDoors.getAllDoors(); // gets the list of all Doors from DirectoryDoors

    for (Door door : allDoors) {
      if (door.getFrom().equals(spaceId) || door.getTo().equals(spaceId)) {
        doorList.add(door);
      }
    }
    if (doorList.isEmpty()) {
      System.out.println("no doors were found");
      return null;
    }
    return doorList;
  }
}
