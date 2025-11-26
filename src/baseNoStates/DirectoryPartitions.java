package baseNoStates;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectoryPartitions is responsible for defining and initializing
 * all building partitions. Each partition is assigned an identifier
 * and the list of doors that provide access to it.

 * Implemented as a singleton so that there is a single shared
 * directory of partitions across the application.
 */
public final class DirectoryPartitions {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryPartitions.class);

  // Singleton instance
  private static final DirectoryPartitions INSTANCE = new DirectoryPartitions();

  // List with all partitions
  private List<Partition> allPartitions;

  // Predefined partition names
  private static final String[] partitionNames = {
      "basement", "ground_floor", "floor1"
  };

  /**
   * Private constructor to enforce singleton.
   */
  private DirectoryPartitions() {
    allPartitions = new ArrayList<>();
  }

  /**
   * Returns the single instance of DirectoryPartitions.
   *
   * @return the singleton instance
   */
  public static DirectoryPartitions getInstance() {
    return INSTANCE;
  }

  /**
   * Creates and initializes all partitions.
   * This method should be called once at startup.
   */
  public void makePartitions() {
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

  /**
   * Finds all doors that belong to a given partition.
   *
   * @param partitionName identifier of the partition
   * @return list of doors that belong to the partition
   */
  public List<Door> findDoorByPartitionId(String partitionName) {
    logger.debug("Searching doors for partition '{}'", partitionName);

    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    for (Door door : allDoors) {
      if (door.getPartition().equals(partitionName)) {
        doorList.add(door);
        logger.debug("Door '{}' added to partition '{}'",
            door.getId(), partitionName);
      }
    }

    if (doorList.isEmpty()) {
      logger.warn("No doors found for partition '{}'", partitionName);
    }

    return doorList;
  }

  /**
   * Returns the list of all partitions.
   *
   * @return list with all partitions
   */
  public List<Partition> getAllPartitions() {
    logger.debug("Returning {} partitions", allPartitions.size());
    return allPartitions;
  }
}
