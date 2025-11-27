package baseNoStates.milestone2;

import baseNoStates.milestone1.Area;
import baseNoStates.milestone1.Partition;
import baseNoStates.milestone1.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private static final Logger logger =
      LoggerFactory.getLogger(FindAreaVisitor.class);

  private final String targetId;
  private Area result;

  public FindAreaVisitor(String id) {
    this.targetId = id;
    logger.debug("FindAreaVisitor created for targetId='{}'", id);
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug("Visiting partition '{}'", partition.getId());

    if (partition.getId().equals(targetId)) {
      result = partition;
      logger.info("Area found: partition '{}'", targetId);
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug("Visiting space '{}'", space.getId());

    if (space.getId().equals(targetId)) {
      result = space;
      logger.info("Area found: space '{}'", targetId);
    }
  }

  public Area getResult() {
    if (result == null) {
      logger.debug("No area found matching id '{}'", targetId);
    }
    return result;
  }
}
