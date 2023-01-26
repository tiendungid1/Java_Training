package rxn.ds.tree;

public class NoSuchNodeException extends Exception {
  public NoSuchNodeException() {
    super("Targeting node does not exist");
  }
}
