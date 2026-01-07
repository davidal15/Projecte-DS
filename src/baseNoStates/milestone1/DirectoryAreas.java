package baseNoStates.milestone1;

import baseNoStates.milestone2.FindAreaVisitor;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DirectoryAreas {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryAreas.class);

  private Area rootArea;
  private static final DirectoryAreas INSTANCE = new DirectoryAreas();
  private List<Area> allAreas;

  private DirectoryAreas() {
    allAreas = new ArrayList<>();
  }

  public static DirectoryAreas getInstance() {
    return INSTANCE;
  }

  /**
   * Initializes all areas creating the explicit Tree Hierarchy.
   * Structure: Building -> Partitions (Floors) -> Spaces (Rooms)
   */
  public void makeAreas() {
    logger.info("Initializing areas with Tree Structure...");
    allAreas = new ArrayList<>();

    DirectoryDoors dd = DirectoryDoors.getInstance();

    List<Door> doorsParking = new ArrayList<>();
    addDoorIfFound(doorsParking, "D1");
    addDoorIfFound(doorsParking, "D2");
    Space parking = new Space("parking", doorsParking);

    // Hall (D3, D4)
    List<Door> doorsHall = new ArrayList<>();
    addDoorIfFound(doorsHall, "D3");
    addDoorIfFound(doorsHall, "D4");
    Space hall = new Space("hall", doorsHall);

    // Room1 (D5)
    List<Door> doorsRoom1 = new ArrayList<>();
    addDoorIfFound(doorsRoom1, "D5");
    Space room1 = new Space("room1", doorsRoom1);

    // Room2 (D6)
    List<Door> doorsRoom2 = new ArrayList<>();
    addDoorIfFound(doorsRoom2, "D6");
    Space room2 = new Space("room2", doorsRoom2);

    // Corridor (D7)
    List<Door> doorsCorridor = new ArrayList<>();
    addDoorIfFound(doorsCorridor, "D7"); // Asumiendo D7 para pasillo
    Space corridor = new Space("corridor", doorsCorridor);

    // Room3 (D8)
    List<Door> doorsRoom3 = new ArrayList<>();
    addDoorIfFound(doorsRoom3, "D8");
    Space room3 = new Space("room3", doorsRoom3);

    // IT (D9)
    List<Door> doorsIT = new ArrayList<>();
    addDoorIfFound(doorsIT, "D9");
    Space it = new Space("it", doorsIT);

    allAreas.add(parking);
    allAreas.add(hall);
    allAreas.add(room1);
    allAreas.add(room2);
    allAreas.add(corridor);
    allAreas.add(room3);
    allAreas.add(it);

    List<Area> basementChildren = new ArrayList<>();
    basementChildren.add(parking);
    Partition basement = new Partition("basement", basementChildren);

    List<Area> groundChildren = new ArrayList<>();
    groundChildren.add(room1);
    groundChildren.add(room2);
    groundChildren.add(hall);
    Partition groundFloor = new Partition("ground_floor", groundChildren);

    List<Area> floor1Children = new ArrayList<>();
    floor1Children.add(room3);
    floor1Children.add(it);
    floor1Children.add(corridor);
    Partition floor1 = new Partition("floor1", floor1Children);

    allAreas.add(basement);
    allAreas.add(groundFloor);
    allAreas.add(floor1);

    List<Area> buildingChildren = new ArrayList<>();
    buildingChildren.add(basement);
    buildingChildren.add(groundFloor);
    buildingChildren.add(floor1);

    Partition building = new Partition("building", buildingChildren);

    rootArea = building;
    allAreas.add(building);

    logger.info("Areas initialized. Root is '{}' with {} children.",
        rootArea.getId(), buildingChildren.size());
  }

  private void addDoorIfFound(List<Door> list, String id) {
    Door d = DirectoryDoors.getInstance().findDoorById(id);
    if (d != null) {
      list.add(d);
    } else {
      logger.warn("Door {} not found via DirectoryDoors", id);
    }
  }

  public Area findAreaById(String id) {
    logger.debug("Searching for area with id '{}'", id);

    if (id.equals("ROOT")) {
      return rootArea;
    } else {
      if (id == null) {
        return null;
      }
      FindAreaVisitor visitor = new FindAreaVisitor(id);
      for (Area area : allAreas) {
        area.accept(visitor);
        if (visitor.getResult() != null) {
          break;
        }
      }
      return visitor.getResult();
    }
  }
}