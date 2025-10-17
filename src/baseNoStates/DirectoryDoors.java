package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryDoors {
  private static ArrayList<Door> allDoors;

  public static void makeDoors() {
    // basement
    Door d1 = new Door("D1", "exterior", "parking", "basement"); // exterior, parking
    Door d2 = new Door("D2", "stairs", "parking", "basement"); // stairs, parking

    // ground floor
    Door d3 = new Door("D3", "exterior", "hall", "ground floor"); // exterior, hall
    Door d4 = new Door("D4", "stairs", "hall", "ground floor"); // stairs, hall
    Door d5 = new Door("D5", "hall", "room1", "ground floor"); // hall, room1
    Door d6 = new Door("D6", "hall", "room2", "ground floor"); // hall, room2

    // first floor
    Door d7 = new Door("D7", "stairs", "corridor", "floor 1"); // stairs, corridor
    Door d8 = new Door("D8", "corridor", "room3", "floor 1"); // corridor, room3
    Door d9 = new Door("D9", "corridor", "IT", "floor 1"); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    return allDoors;
  }
}