package baseNoStates.milestone1;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Door class represents a door within the system, including its identifiers,
 * origin and destination locations, associated partition, and state.

 * It is responsible for:
 * - Processing incoming requests and delegating behavior to its current DoorState.
 * - Maintaining whether it is closed or open.
 * - Transitioning between states such as locked, unlocked, opened, or closed.

 * Authorization is verified before acting, and state transitions are logged.
 * JSON export is supported for external representation.
 */
public class Door {

  private static final Logger logger = LoggerFactory.getLogger(Door.class);

  private final String id;
  private final String from;
  private final String to;
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

    logger.info("Door '{}' created (from='{}', to='{}', partition='{}')", id, from, to, partition);
  }

  public void processRequest(RequestReader request) {

    logger.debug("Processing request for door '{}' with action '{}'", id, request.getAction());

    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);

      logger.info("Authorized request on door '{}' with action '{}'", id, action);

    } else {
      logger.warn("Unauthorized request on door '{}'", id);
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

    logger.debug("Executing action '{}' on door '{}'", action, id);

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
        logger.info("Door '{}' temporarily unlocked", id);
        break;
      default:
        logger.error("Unknown action '{}' attempted on door '{}'", action, id);
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

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getPartition() {
    return partition;
  }

  public String getStateName() {
    return this.state.getStateName();
  }

  public void setState(DoorState state) {
    logger.debug("Door '{}' transitioned to state '{}'", id, state.getStateName());
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

    logger.debug("Door '{}' serialized to JSON", id);

    return json;
  }
}
