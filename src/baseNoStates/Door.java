package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


public class Door {
  private final String id;
  private final String from;
  private final String to;
  private boolean closed; // physically
  private boolean locked; // locked or unlocked door
  private boolean propped; // if unlocked shortly doesn't lock the door, it's propped

  public Door(String id, String from, String to) {
    this.id = id;
    this.from = from;
    this.to = to;
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
        } else if (!closed && propped) { //used only if door is propped
          closed = true;
          propped = false;
          locked = true;
        }
        else {
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
        if (closed && locked){
          locked = false;
          try { //10 second timer, after it, the door is locked if closed
            Thread.sleep(10000); // miliseconds
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (!closed) { //if the door is not closed after 10 seconds, it is propped
            propped = true;
            //while propped
            //send alarm
          } else {
            locked = true;
          }

        } else {
          System.out.println("Can't unlock door " + id + ", it's already unlocked or open");
        }
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

  public String getFrom() { //returns origin
    return from;
  }

  public String getTo() { //returns destination
    return to;
  }

  public String getStateName() {
    return "unlocked";
  }

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
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
