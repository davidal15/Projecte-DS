package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


public class Door {
  private final String id;
  private final String from; // every door needs to know where it opens from and where it leads
  private final String to;   // in order to order the Spaces
  private final String partition;
  private boolean closed; // physically
  private boolean locked; // locked or unlocked door
  private boolean propped; // if unlocked shortly doesn't lock the door, it's propped
  private boolean unlocked_shortly;


  public Door(String id, String from, String to, String partition) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.partition = partition;
    closed = true;
    locked = true;
    propped = false;
  }

  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        if (closed && !locked) {
          closed = false;
        } else {
          System.out.println("Can't open door " + id + " because it's already open or locked");
        }
        break;
      case Actions.CLOSE:
        if (!closed) {
          closed = true;
        } else if (!closed && propped) { // used only if door is propped
          closed = true;
          propped = false;
          locked = true;
        } else {
          System.out.println("Can't close door " + id + ", it's already closed");
        }
        break;
      case Actions.LOCK:
        if (!locked && closed) { // if door is not locked and is closed
          locked = true;
        } else {
          System.out.println("Can't lock door " + id + ", it's already locked or it isn't closed");
        }
        break;
      case Actions.UNLOCK:
        if (locked) {
          locked = false;
        } else {
          System.out.println("Can't unlock door " + id + ", it's already unlocked");
        }
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

  public boolean isLocked() {
    return locked;
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
    if (locked) {
      return "locked";
    } else if (propped) {
      return "propped";
    } else if (unlocked_shortly) {
      return "unlocked shortly";
    }
    return "unlocked";
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
