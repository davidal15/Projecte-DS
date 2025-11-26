package baseNoStates;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for managing and grouping all areas in the system,
 * including Partitions, Spaces, and the global "building" structure.

 * Implemented as a singleton so that there is a single shared
 * directory of areas across the application.

 * Main responsibilities:
 * - Initialize and construct all available areas through makeAreas().
 * - Provide a lookup method to find an area by its identifier.

 * Notes:
 * - The allAreas list is generated from the partitions and spaces previously created.
 * - The "building" area acts as a global container for all structures.
 */
public final class DirectoryAreas {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryAreas.class);

  // Singleton instance
  private static final DirectoryAreas INSTANCE = new DirectoryAreas();

  // List with all areas (building, partitions and spaces)
  private List<Area> allAreas;

  /**
   * Private constructor to enforce singleton.
   */
  private DirectoryAreas() {
    allAreas = new ArrayList<>();
  }

  /**
   * Returns the single instance of DirectoryAreas.
   *
   * @return the singleton instance
   */
  public static DirectoryAreas getInstance() {
    return INSTANCE;
  }

  /**
   * Initializes all areas in the system.
   * Creates partitions, spaces and the global "building" area.
   */
  public void makeAreas() {
    logger.info("Initializing areas...");

    allAreas = new ArrayList<>();

    logger.debug("Creating partitions...");
    DirectoryPartitions.getInstance().makePartitions();

    logger.debug("Creating spaces...");
    DirectorySpaces.getInstance().makeSpaces();

    Area building =
        new Partition("building",
            DirectoryDoors.getInstance().getAllDoors());
    allAreas.add(building);
    logger.debug("Added global area 'building' containing all doors");

    allAreas.addAll(DirectoryPartitions.getInstance().getAllPartitions());
    logger.debug("Added {} partitions",
        DirectoryPartitions.getInstance().getAllPartitions().size());

    allAreas.addAll(DirectorySpaces.getInstance().getAllSpaces());
    logger.debug("Added {} spaces",
        DirectorySpaces.getInstance().getAllSpaces().size());

    logger.info("Areas initialized successfully. Total areas: {}",
        allAreas.size());
  }

  /**
   * Finds an area by its identifier.
   *
   * @param id area identifier
   * @return the Area with the given id, or null if not found
   */
  public Area findAreaById(String id) {
    logger.debug("Searching for area with id '{}'", id);

    if (id == null) {
      logger.warn("Attempted to search for an area with null id");
      return null;
    }

    for (Area area : allAreas) {
      if (area.getId().equalsIgnoreCase(id)) {
        logger.info("Area '{}' found", id);
        return area;
      }
    }

    logger.warn("No area found with id '{}'", id);
    return null;
  }
}
