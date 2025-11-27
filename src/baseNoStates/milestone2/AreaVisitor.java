package baseNoStates.milestone2;

import baseNoStates.milestone1.Partition;
import baseNoStates.milestone1.Space;

/**
 * AreaVisitor defines the Visitor interface for traversing the building structure.
 * It allows executing different operations over the elements of the hierarchy
 * (Partitions and Spaces) without modifying their classes.

 * This supports extensibility by enabling new behaviors — such as searching,
 * collecting, filtering, auditing, or reporting — through new Visitor
 * implementations rather than adding traversal logic to Area classes.
 */
public interface AreaVisitor {
  void visitSpace(Space space);

  void visitPartition(Partition partition);
}
