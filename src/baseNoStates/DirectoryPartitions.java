package baseNoStates;

import java.util.ArrayList;

public final class DirectoryPartitions {
  private static ArrayList<Partition> allPartitions;
  private static final String[] partitionNames = {
      "basement", "ground floor", "floor 1"
  };

  public static void makePartitions() {
    allPartitions = new ArrayList<>();
    for (String name : partitionNames) {
      ArrayList<Door> doors = findDoorByPartitionId(name);
      Partition p = new Partition(name, doors);
      allPartitions.add(p);
    }
  }

  public static ArrayList<Door> findDoorByPartitionId(String partitionName) {
    ArrayList<Door> doorList = new ArrayList<>();
    ArrayList<Door> allDoors = DirectoryDoors.getAllDoors();

    for (Door door : allDoors) {
      if (door.getPartition().equals(partitionName)) {
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
