package baseNoStates.milestone1;

import baseNoStates.milestone2.AreaVisitor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList; // <--- NECESARIO IMPORTAR ESTO
import java.util.List;

// Child of Area. The Partitions might include Spaces or other Partitions.
public class Partition extends Area {

  private static final Logger logger = LoggerFactory.getLogger(Partition.class);

  // 1. AQUI DECLARAMOS LA LISTA QUE FALTABA
  private List<Area> areas;

  // 2. MODIFICAMOS EL CONSTRUCTOR
  // Una partición se construye con una lista de Áreas hijas, no de puertas directas.
  public Partition(String id, List<Area> areas) {
    // Al padre (Area) le pasamos una lista vacía de puertas,
    // porque las puertas pertenecen a los Espacios, no a las Particiones.
    super(id, new ArrayList<>());
    this.areas = areas;
  }

  // (Opcional) Getter por si lo necesitas fuera
  public List<Area> getAreas() {
    return areas;
  }

  /**
   * Returns all doors giving access to this partition.
   * NOT used for navigation, only for actions (lock/unlock).
   */
  @Override
  public List<Door> getDoorsGivingAccess() {
    logger.info("Computing doors giving access to area {}", id);

    // Nota: 'doors' viene heredado de la clase padre 'Area'
    doors.clear();

    List<Door> allDoors = DirectoryDoors.getInstance().getAllDoors();

    if (id.equals("building")) { // O "ROOT"
      logger.debug("Area '{}' is building: returning all doors ({})",
          id, allDoors.size());
      doors.addAll(allDoors);
      return doors;
    }

    for (Door door : allDoors) {
      // getPartition() debe devolver el ID de la partición a la que pertenece la puerta
      if (id.equals(door.getPartition())) {
        doors.add(door);
        logger.debug("Door {} provides access to area {}", door.getId(), id);
      }
    }

    if (doors.isEmpty()) {
      logger.warn("No doors found for area: {}", id);
    }

    return doors;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }

  @Override
  public boolean isUnlocked() {
    for (Area a : this.areas) {
      if (!a.isUnlocked()) {
        return false;
      }
    }
    return true;
  }

  /**
   * JSON representation used by RequestChildren.
   * Includes ONLY direct child areas (no doors).
   */
  @Override
  public JSONObject toJson(int depth) {
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", id);

    json.put("locked", isLocked());
    json.put("unlocked", isUnlocked());

    if (depth > 0) {
      JSONArray jsonAreas = new JSONArray();
      for (Area a : this.areas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }

  @Override
  public boolean isLocked() {
    for (Area a : this.areas) {
      if (!a.isLocked()) {
        return false;
      }
    }
    return true;
  }
}