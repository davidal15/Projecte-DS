package baseNoStates;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for managing and grouping all areas in the system,
 * including Partitions, Spaces, and the global "building" structure.
 * Main responsibilities:
 * - Initialize and construct all available areas through makeAreas().
 * - Provide a lookup method to find an area by its identifier.
 * Notes:
 * - The allAreas list is generated from the partitions and spaces previously created.
 * - The "building" area acts as a global container for all structures.
 */
public final class DirectoryAreas {

  private static final Logger logger = LoggerFactory.getLogger(DirectoryAreas.class);

  private static List<Area> allAreas;

  public static void makeAreas() {
    logger.info("Initializing areas...");

    allAreas = new ArrayList<>();

    logger.debug("Creating partitions...");
    DirectoryPartitions.makePartitions();

    logger.debug("Creating spaces...");
    DirectorySpaces.makeSpaces();

    Area area = new Partition("building", DirectoryDoors.getAllDoors());
    allAreas.add(area);
    logger.debug("Added global area 'building' containing all doors");

    allAreas.addAll(DirectoryPartitions.getAllPartitions());
    logger.debug("Added {} partitions", DirectoryPartitions.getAllPartitions().size());

    allAreas.addAll(DirectorySpaces.getAllSpaces());
    logger.debug("Added {} spaces", DirectorySpaces.getAllSpaces().size());

    logger.info("Areas initialized successfully. Total areas: {}", allAreas.size());
  }

  public static Area findAreaById(String id) {

    logger.debug("Searching for area with id '{}'", id);

    if (id == null) {
      logger.warn("Attempted to search for an area with null id");
      return null;
    }

    for (Area a : allAreas) {
      if (a.getId().equalsIgnoreCase(id)) {
        logger.info("Area '{}' found", id);
        return a;
      }
    }

    logger.warn("No area found with id '{}'", id);
    return null;
  }
}
