package baseNoStates;

import java.util.ArrayList;

public class Space extends Area { // Child of Area

  public Space() {
    super();
  }
  // Both constructors use Area's
  public Space(String id, ArrayList<Door> doors) {
    super(id, doors);
  }



}
