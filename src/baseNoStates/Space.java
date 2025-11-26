package baseNoStates;

import java.util.List;

// Child of Area. It's the smallest possible type of Area.
public class Space extends Area {

  // Both constructors use Area's
  public Space(String id, List<Door> doors) {
    super(id, doors);
  }

  /**
   * Accepts a visitor that performs an operation on this Space instance.
   * Spaces do not delegate further traversal since they represent leaf nodes
   * in the building hierarchy.
   */
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitSpace(this);
  }
}