package baseNoStates;

import java.util.ArrayList;
import java.util.List;

/* The DirectoryPartitions class, is responsible
*  for the status of each Partition, creating them
*  with it's doorList and identification
*/
public final class DirectoryPartitions {
  private static List<Partition> allPartitions;
  private static final String[] partitionNames = { // Partition names
      "basement", "ground_floor", "floor1"
  };
  // Creates every Partition. Gives them an id and a list of Door that give access to it
  public static void makePartitions() {
    allPartitions = new ArrayList<>();
    for (String name : partitionNames) {
      List<Door> doors = new ArrayList<>();
      doors = findDoorByPartitionId(name);
      Partition p = new Partition(name, doors);
      allPartitions.add(p);
    }
  }
  // Returns every door for the Partition passed by parameter
  public static List<Door> findDoorByPartitionId(String partitionName) {
    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getAllDoors();

    for (Door door : allDoors) {
      if (door.getPartition().equals(partitionName)) { // if the Door is in the Partition, it's added to the list
        doorList.add(door);
      }
    }

    if (doorList.isEmpty()) {
      System.out.println("No doors found for partition: " + partitionName);
    }
    return doorList;
  }

  public static List<Partition> getAllPartitions() {
    return allPartitions;
  }
}