package baseNoStates;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectoryPartitions is responsible for defining and initializing
 * all building partitions. Each partition is assigned an identifier
 * and the list of doors that provide access to it.

 * Responsibilities:
 * - Create all partitions through makePartitions().
 * - Retrieve doors associated with a specific partition.
 * - Provide access to the full partition list.

 * Notes:
 * - Partition names are predefined.
 * - A warning is logged if a partition has no doors.
 */
public final class DirectoryPartitions {

  private static final Logger logger = LoggerFactory.getLogger(DirectoryPartitions.class);

  private static List<Partition> allPartitions;
  private static final String[] partitionNames = {
      "basement", "ground_floor", "floor1"
  };

  public static void makePartitions() {

    logger.info("Initializing partitions...");

    allPartitions = new ArrayList<>();

    for (String name : partitionNames) {

      logger.debug("Creating partition '{}'", name);

      List<Door> doors = findDoorByPartitionId(name);

      Partition p = new Partition(name, doors);
      allPartitions.add(p);

      logger.info("Partition '{}' initialized with {} doors", name, doors.size());
    }

    logger.info("Total partitions initialized: {}", allPartitions.size());
  }

  public static List<Door> findDoorByPartitionId(String partitionName) {

    logger.debug("Searching doors for partition '{}'", partitionName);

    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getAllDoors();

    for (Door door : allDoors) {
      if (door.getPartition().equals(partitionName)) {
        doorList.add(door);
        logger.debug("Door '{}' added to partition '{}'", door.getId(), partitionName);
      }
    }

    if (doorList.isEmpty()) {
      logger.warn("No doors found for partition '{}'", partitionName);
    }

    return doorList;
  }

  public static List<Partition> getAllPartitions() {
    logger.debug("Returning {} partitions", allPartitions.size());
    return allPartitions;
  }
}
