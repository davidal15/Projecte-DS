package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area { // Child of Area

  public Partition() {
    super();
  }
  // Both constructors use Area's
  public Partition(String id, ArrayList<Door> doors) {
    super(id);
  }

}
