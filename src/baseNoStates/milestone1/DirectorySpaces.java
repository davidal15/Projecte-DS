package baseNoStates.milestone1;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectorySpaces is responsible for defining and initializing
 * all spaces in the building. Each space is assigned an identifier
 * and the list of doors that give access to it.

 * Implemented as a singleton so that there is a single shared
 * directory of spaces across the application.
 */
public final class DirectorySpaces {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectorySpaces.class);

  // Singleton instance
  private static final DirectorySpaces INSTANCE = new DirectorySpaces();

  // List with all spaces
  private List<Space> allSpaces;

  // Predefined space names
  private static final String[] spaceNames = {
      "parking", "room1", "room2", "room3",
      "hall", "IT", "corridor", "stairs", "exterior"
  };

  /**
   * Private constructor to enforce singleton.
   */
  private DirectorySpaces() {
    allSpaces = new ArrayList<>();
  }

  /**
   * Returns the single instance of DirectorySpaces.
   *
   * @return the singleton instance
   */
  public static DirectorySpaces getInstance() {
    return INSTANCE;
  }

  /**
   * Creates and initializes all spaces.
   * This method should be called once at startup.
   */
  public void makeSpaces() {
    logger.info("Initializing spaces...");

    allSpaces = new ArrayList<>();

    for (String name : spaceNames) {
      logger.debug("Creating space '{}'", name);
      List<Door> doors = findDoorBySpaceId(name);
      Space s = new Space(name, doors);
      allSpaces.add(s);
      logger.info("Space '{}' initialized with {} doors", name, doors.size());
    }

    logger.info("Total spaces initialized: {}", allSpaces.size());
  }

  /**
   * Finds all doors that give access to a given space.
   *
   * @param spaceId identifier of the space
   * @return list of doors associated with the space
   */
  public List<Door> findDoorBySpaceId(String spaceId) {
    logger.debug("Searching doors for space '{}'", spaceId);

    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    for (Door door : allDoors) {
      if (door.getFrom().equals(spaceId) || door.getTo().equals(spaceId)) {
        doorList.add(door);
        logger.debug("Door '{}' associated with space '{}'",
            door.getId(), spaceId);
      }
    }

    if (doorList.isEmpty()) {
      logger.warn("No doors found for space '{}'", spaceId);
    }

    return doorList;
  }

  /**
   * Returns the list of all spaces.
   *
   * @return list with all spaces
   */
  public List<Space> getAllSpaces() {
    logger.debug("Returning {} spaces", allSpaces.size());
    return allSpaces;
  }

  /**
   * Finds a space by its identifier.
   * NECESSARY for DirectoryPartitions to link spaces.
   */
  public Space findAreaById(String id) {
    for (Space space : allSpaces) {
      if (space.getId().equals(id)) {
        return space;
      }
    }
    logger.warn("Space with id '{}' not found", id);
    return null;
  }
}
