package baseNoStates;


import java.util.List;

// Child of Area. The Partitions might include Spaces in them.
public class Partition extends Area {

  // Both constructors use Area's
  public Partition(String id, List<Door> doors) {
    super(id, doors);
  }

}