package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


/*
 * The Door class Represents a door within the system, including its identifiers,
 * origin and destination locations, associated partition, and state.
 *
 * It is responsible for:
 * - Processing incoming requests and delegating behavior to its current DoorState.
 * - Maintaining whether it is closed or open.
 * - Transitioning between states such as locked, unlocked, opened, or closed.
 *
 * Each door knows the Space it opens from and where it leads, allowing navigation ordering.
 * Authorization is checked before executing requested actions.
 * The state pattern is used to encapsulate door behavior based on its current state.
 * JSON export is supported for external representation.
 */

public class Door {
  private final String id;
  private final String from; // every door needs to know where it opens from and where it leads
  private final String to;   // in order to order the Spaces
  private final String partition;
  private boolean closed;
  private DoorState state;


  public Door(String id, String from, String to, String partition) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.partition = partition;
    this.closed = true;
    this.state = new Locked();
  }

  public void processRequest(RequestReader request) {
    // it is the Door that processes the request because the door has and knows
    // its state, and if it's closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  public void open() {
    closed = false;
  }

  public void close() {
    closed = true;
  }

  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open(this);
        break;
      case Actions.CLOSE:
        state.close(this);
        break;
      case Actions.LOCK:
        state.lock(this);
        break;
      case Actions.UNLOCK:
        state.unlock(this);
        break;
      case Actions.UNLOCK_SHORTLY:
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getFrom() { // returns origin
    return from;
  }

  public String getTo() { // returns destination
    return to;
  }

  public String getPartition() {
    return partition;
  }

  public String getStateName() {
    return this.state.getStateName();
  }

  public void setState(DoorState state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Door{"
        + " id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }


}