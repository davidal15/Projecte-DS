package baseNoStates;


import java.util.List;

// Child of Area. The Partitions might include Spaces in them.
public class Partition extends Area {

  // Both constructors use Area's
  public Partition(String id, List<Door> doors) {
    super(id, doors);
  }

  /**
   * Accepts a visitor.
   * This allows hierarchical navigation without adding traversal logic
   * inside domain model methods.
   */
  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }

}