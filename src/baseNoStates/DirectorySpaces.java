package baseNoStates;

import java.util.ArrayList;

public final class DirectorySpaces {
  private static ArrayList<DirectorySpaces> allSpaces;

  public static void makeSpaces() {
    Space s1 = new Space("parking", findDoorBySpaceId("parking"));
    Space s2 = new Space("room1", findDoorBySpaceId("room1"));
    Space s3 = new Space("room2", findDoorBySpaceId("room2"));
    Space s4 = new Space("room3", findDoorBySpaceId("room3"));
    Space s5 = new Space("hall", findDoorBySpaceId("hall"));
    Space s6 = new Space("IT",  findDoorBySpaceId("IT"));
    Space s7 = new Space("corridor", findDoorBySpaceId("corridor"));
    Space s8 = new Space("stairs", findDoorBySpaceId("stairs"));
    Space s9 = new Space("exterior", findDoorBySpaceId("exterior"));
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
