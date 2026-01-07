package baseNoStates.milestone1;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectoryPartitions is responsible for defining and initializing
 * all building partitions.
 */
public final class DirectoryPartitions {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryPartitions.class);

  // Singleton instance
  private static final DirectoryPartitions INSTANCE = new DirectoryPartitions();

  // List with all partitions
  private List<Partition> allPartitions;

  /**
   * Private constructor to enforce singleton.
   */
  private DirectoryPartitions() {
    allPartitions = new ArrayList<>();
  }

  public static DirectoryPartitions getInstance() {
    return INSTANCE;
  }

  /**
   * Creates and initializes all partitions using Spaces instead of Doors.
   * PREREQUISITE: DirectorySpaces must be initialized first.
   */
  public void makePartitions() {
    logger.info("Initializing partitions...");

    allPartitions = new ArrayList<>();

    // Necesitamos acceder a los espacios ya creados
    DirectorySpaces ds = DirectorySpaces.getInstance();

    // 1. BASEMENT: Contiene 'parking'
    List<Area> basementChildren = new ArrayList<>();
    addSpaceIfFound(ds, basementChildren, "parking");

    Partition basement = new Partition("basement", basementChildren);
    allPartitions.add(basement);

    // 2. GROUND FLOOR: Contiene 'hall', 'room1', 'room2'
    List<Area> groundChildren = new ArrayList<>();
    addSpaceIfFound(ds, groundChildren, "hall");
    addSpaceIfFound(ds, groundChildren, "room1");
    addSpaceIfFound(ds, groundChildren, "room2");

    Partition groundFloor = new Partition("ground_floor", groundChildren);
    allPartitions.add(groundFloor);

    // 3. FLOOR 1: Contiene 'room3', 'it', 'corridor'
    List<Area> floor1Children = new ArrayList<>();
    addSpaceIfFound(ds, floor1Children, "room3");
    addSpaceIfFound(ds, floor1Children, "it");
    addSpaceIfFound(ds, floor1Children, "corridor");

    Partition floor1 = new Partition("floor1", floor1Children);
    allPartitions.add(floor1);

    logger.info("Total partitions initialized: {}", allPartitions.size());
  }

  // Método auxiliar para buscar el espacio y añadirlo a la lista de forma segura
  private void addSpaceIfFound(DirectorySpaces ds, List<Area> children, String id) {
    Area space = ds.findAreaById(id);
    if (space != null) {
      children.add(space);
    } else {
      logger.warn("Space '{}' not found in DirectorySpaces. Check initialization order.", id);
    }
  }

  public List<Partition> getAllPartitions() {
    return allPartitions;
  }
}