package baseNoStates;

import java.util.ArrayList;
import java.util.List;


public final class DirectoryAreas {
  private static List<Area> allAreas;
  /*
   * Responsible for managing and grouping all areas in the system,
   * including Partitions, Spaces, and the global "building" structure.
   *
   * Main responsibilities:
   * - Initialize and construct all available areas through makeAreas().
   * - Provide a lookup method to find an area by its identifier.
   *
   * Notes:
   * - The allAreas list is generated from the partitions and spaces previously created.
   * - The "building" area acts as a global container for all structures.
   */

  public static void makeAreas() { // Creates all Spaces and Partitions
    allAreas = new ArrayList<>();
    DirectoryPartitions.makePartitions();
    DirectorySpaces.makeSpaces();

    Area area = new Partition("building", DirectoryDoors.getAllDoors()); // This creates the area Building, which contains every Partition, Space and Door
    // Combines all areas
    allAreas.add(area);
    allAreas.addAll(DirectoryPartitions.getAllPartitions());
    allAreas.addAll(DirectorySpaces.getAllSpaces());
  }

  public static Area findAreaById(String id) { // Searches the area passed by parameter
    if (id == null) {
      System.out.println("Error: id is null");
      return null;
    }

    for (Area a : allAreas) {
      if (a.getId().equalsIgnoreCase(id)) {
        return a;
      }
    }

    System.out.println("No area found with id: " + id);
    return null;
  }

}