package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class Space extends Area { // Child of Area

  // Both constructors use Area's
  public Space(String id, List<Door> doors) {
    super(id, doors);
  }
}