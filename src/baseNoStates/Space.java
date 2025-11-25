package baseNoStates;

import java.util.List;

// Child of Area. It's the smallest possible type of Area.
public class Space extends Area {

  // Both constructors use Area's
  public Space(String id, List<Door> doors) {
    super(id, doors);
  }
}