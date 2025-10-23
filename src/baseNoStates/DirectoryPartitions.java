package baseNoStates;

import java.util.ArrayList;

public final class DirectoryPartitions {
  private static ArrayList<Partition> allPartitions;
  private static final String[] partitionNames = { // Partition names
      "basement", "ground_floor", "floor1"
  };

  public static void makePartitions() { // Creates every Partition. Gives them an id and a list of Door that give access to it
    allPartitions = new ArrayList<>();
    for (String name : partitionNames) {
      ArrayList<Door> doors = findDoorByPartitionId(name);
      Partition p = new Partition(name, doors);
      allPartitions.add(p);
    }
  }

  public static ArrayList<Door> findDoorByPartitionId(String partitionName) { // Returns every door for the Partition
    ArrayList<Door> doorList = new ArrayList<>();                             // passed by parameter
    ArrayList<Door> allDoors = DirectoryDoors.getAllDoors();

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

  public static ArrayList<Partition> getAllPartitions() {
    return allPartitions;
  }
}
