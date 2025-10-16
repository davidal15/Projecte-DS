package baseNoStates;

import java.util.ArrayList;

public final class DirectoryAreas {
  private static ArrayList<Area> allAreas = new ArrayList<>();

  public static void makeAreas() {
    // Asegúrate de que las demás listas estén creadas
    DirectoryPartitions.makePartitions();
    DirectorySpaces.makeSpaces();

    // Combina todas las áreas
    allAreas.clear();
    allAreas.addAll(DirectoryPartitions.getAllPartitions());
    allAreas.addAll(DirectorySpaces.getAllSpaces());
  }

  public static Area findAreaById(String id) {
    if (id == null) {
      System.out.println("Error: id is null");
      return null;
    }

    for (Area a : allAreas) {
      if (a.getId().equalsIgnoreCase(id)) {
        return a;
      }
    }

    System.out.println("⚠️ No area found with id: " + id);
    return null;
  }

  public static ArrayList<Area> getAllAreas() {
    return allAreas;
  }
}
