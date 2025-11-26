package baseNoStates;

/**
 * FindAreaVisitor searches for an Area (Partition or Space) whose identifier
 * matches the given target id. It traverses the hierarchy through the Visitor
 * pattern, allowing lookup without embedding search logic inside model classes.

 * The visitor stops evaluating once a match is found, and the result can be
 * retrieved through getResult().

 * This supports milestone refactoring goals by removing direct traversal
 * responsibility from DirectoryAreas and avoiding repeated search code.
 */
public class FindAreaVisitor implements AreaVisitor {
  private final String targetId;
  private Area result;

  public FindAreaVisitor(String id) {
    this.targetId = id;
  }

  @Override
  public void visitPartition(Partition partition) {
    if (partition.getId().equals(targetId)) {
      result = partition;
    }
  }

  @Override
  public void visitSpace(Space space) {
    if (space.getId().equals(targetId)) {
      result = space;
    }
  }

  public Area getResult() {
    return result;
  }
}
