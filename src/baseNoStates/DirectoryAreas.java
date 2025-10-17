package baseNoStates;

import java.util.ArrayList;

public final class DirectoryAreas {
  private static ArrayList<Area> allAreas = new ArrayList<>();


  public static void makeAreas() {
    // Creates all Spaces and Partitions
    DirectoryPartitions.makePartitions();
    DirectorySpaces.makeSpaces();

    allAreas.clear();

    Area area = new Area("building");
    area.setDoors(DirectoryDoors.getAllDoors());
    // Combines all areas
    allAreas.add(area);
    allAreas.addAll(DirectoryPartitions.getAllPartitions());
    allAreas.addAll(DirectorySpaces.getAllSpaces());
  }

  public static Area findAreaById(String id) {
    makeAreas();
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

  public static ArrayList<Area> getAllAreas() {
    return allAreas;
  }
}
