package baseNoStates.milestone1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DirectoryDoors is responsible for defining and initializing
 * all doors in the building and providing lookup operations.

 * It is implemented as a singleton so that there is a single
 * shared directory of doors across the application.
 */
public final class DirectoryDoors {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryDoors.class);

  // Singleton instance
  private static final DirectoryDoors INSTANCE = new DirectoryDoors();

  // List with all doors in the system
  private ArrayList<Door> allDoors;

  /**
   * Private constructor to enforce singleton.
   */
  private DirectoryDoors() {
    allDoors = new ArrayList<>();
  }

  /**
   * Returns the single instance of DirectoryDoors.
   *
   * @return the singleton instance
   */
  public static DirectoryDoors getInstance() {
    return INSTANCE;
  }

  /**
   * Creates and initializes all doors in the building.
   * This method should be called once at startup.
   */
  public void makeDoors() {
    logger.info("Initializing doors...");

    // basement doors
    Door d1 = new Door("D1", "exterior", "parking", "basement");
    Door d2 = new Door("D2", "stairs", "parking", "basement");

    // ground floor doors
    Door d3 = new Door("D3", "exterior", "hall", "ground_floor");
    Door d4 = new Door("D4", "stairs", "hall", "ground_floor");
    Door d5 = new Door("D5", "hall", "room1", "ground_floor");
    Door d6 = new Door("D6", "hall", "room2", "ground_floor");

    // first floor doors
    Door d7 = new Door("D7", "stairs", "corridor", "floor1");
    Door d8 = new Door("D8", "corridor", "room3", "floor1");
    Door d9 = new Door("D9", "corridor", "IT", "floor1");

    allDoors = new ArrayList<>(
        Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9)
    );

    logger.debug("Total doors initialized: {}", allDoors.size());
    logger.info("Doors initialized successfully.");
  }

  /**
   * Finds a door by its identifier.
   *
   * @param id door identifier
   * @return the Door with the given id, or null if not found
   */
  public Door findDoorById(String id) {
    logger.debug("Searching for door with id '{}'", id);

    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        logger.info("Door '{}' found", id);
        return door;
      }
    }

    logger.warn("Door with id '{}' not found", id);
    return null;
  }

  /**
   * Returns the list of all doors.
   *
   * @return list with all doors
   */
  public ArrayList<Door> getAllDoors() {
    logger.debug("Returning full door list ({} doors)", allDoors.size());
    return allDoors;
  }
}
