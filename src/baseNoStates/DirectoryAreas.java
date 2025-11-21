package baseNoStates;

import java.util.List;
import java.util.ArrayList;

public final class DirectoryAreas {
  private static List<Area> allAreas;


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