package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public final class DirectoryPartitions {
  private static List<Partition> allPartitions;
  private static final String[] partitionNames = { // Partition names
      "basement", "ground_floor", "floor1"
  };

  public static void makePartitions() { // Creates every Partition. Gives them an id and a list of Door that give access to it
    allPartitions = new ArrayList<>();
    for (String name : partitionNames) {
      List<Door> doors = new ArrayList<>();
      doors = findDoorByPartitionId(name);
      Partition p = new Partition(name, doors);
      allPartitions.add(p);
    }
  }

  public static List<Door> findDoorByPartitionId(String partitionName) { // Returns every door for the Partition
    List<Door> doorList = new ArrayList<>();                             // passed by parameter
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