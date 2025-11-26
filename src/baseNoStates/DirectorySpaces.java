package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * DirectorySpaces is responsible for defining and initializing
 * all spaces in the building. Each space is assigned an identifier
 * and the list of doors that give access to it.

 * Responsibilities:
 * - Create all spaces through makeSpaces().
 * - Retrieve doors associated with a specific space.
 * - Provide access to the full space list.

 * Notes:
 * - Space names are predefined.
 * - A warning is logged if a space has no connecting doors.
 */
public final class DirectorySpaces {

  private static final Logger logger = LoggerFactory.getLogger(DirectorySpaces.class);

  private static List<Space> allSpaces;

  private static final String[] spaceNames = {
      "parking", "room1", "room2", "room3",
      "hall", "IT", "corridor", "stairs", "exterior"
  };

  public static void makeSpaces() {

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

  public static List<Door> findDoorBySpaceId(String spaceId) {

    logger.debug("Searching doors for space '{}'", spaceId);

    List<Door> doorList = new ArrayList<>();
    List<Door> allDoors = DirectoryDoors.getAllDoors();

    for (Door door : allDoors) {
      if (door.getFrom().equals(spaceId) || door.getTo().equals(spaceId)) {
        doorList.add(door);
        logger.debug("Door '{}' associated with space '{}'", door.getId(), spaceId);
      }
    }

    if (doorList.isEmpty()) {
      logger.warn("No doors found for space '{}'", spaceId);
    }

    return doorList;
  }

  public static List<Space> getAllSpaces() {
    logger.debug("Returning {} spaces", allSpaces.size());
    return allSpaces;
  }
}
