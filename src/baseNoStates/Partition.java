package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class Partition extends Area { // Child of Area

  // Both constructors use Area's
  public Partition(String id, List<Door> doors) {
    super(id, doors);
  }

}