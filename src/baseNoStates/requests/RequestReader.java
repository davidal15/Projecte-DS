package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.DirectoryUsers;
import baseNoStates.Door;
import baseNoStates.User;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;
  private static final Logger logger =
      LoggerFactory.getLogger(RequestReader.class);

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
        + "credential=" + credential
        + ", userName=" + userName
        + ", action=" + action
        + ", now=" + now
        + ", doorID=" + doorId
        + ", authorized="
        + ", closed=" + doorClosed + authorized
        + ", reasons=" + reasons
        + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {

    User user = DirectoryUsers.getInstance().findUserByCredential(credential);
    Door door = DirectoryDoors.getInstance().findDoorById(doorId);

    assert door != null : "door " + doorId + " not found";
    authorize(user, door);

    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    logger.info("Request Reader userName '{}' action '{}' datetime '{}'\ndoorId '{}'  authorized '{}'",user.getName(), action, now, doorId, authorized
    );
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("user doesn't exists");
    } else {
      String role = user.getRole();
      LocalDateTime when = now;

      switch (role) {
        case "Admin":
          authorized = true;
          break;

        case "Manager":
          boolean valid = true;

          // Date range inclusive: [2025-09-01, 2026-03-01]
          LocalDate startDate = LocalDate.of(2025, 9, 1);
          LocalDate endDate = LocalDate.of(2026, 3, 1);
          LocalDate d = when.toLocalDate();

          if (d.isBefore(startDate) || d.isAfter(endDate)) {
            valid = false;
            addReason("Present date not in Manager schedule [2025-09-01..2026-03-01]");
          }

          // Day of week: Monday..Saturday
          DayOfWeek dow = when.getDayOfWeek();
          if (dow == DayOfWeek.SUNDAY) {
            valid = false;
            addReason("Present day of week not in Manager schedule [Mon–Sat]");
          }

          // Time window: [08:00, 20:00)
          LocalTime start = LocalTime.of(8, 0);
          LocalTime end = LocalTime.of(20, 0);
          LocalTime t = when.toLocalTime();

          if (valid && (t.isBefore(start) || !t.isBefore(end))) {
            valid = false;
            addReason("Present time not in Manager schedule [08:00..20:00]");
          }

          // Actions: all are allowed for Manager
          // Spaces: all spaces allowed for Manager; no need to check partition/space
          authorized = valid;
          break;

        case "Employee":
          boolean validE = true;

          // Date range inclusive: [2025-09-01, 2026-03-01]
          LocalDate startDateE = LocalDate.of(2025, 9, 1);
          LocalDate endDateE = LocalDate.of(2026, 3, 1);
          LocalDate dE = when.toLocalDate();

          if (dE.isBefore(startDateE) || dE.isAfter(endDateE)) {
            validE = false;
            addReason("Present date not in Employee schedule [2025-09-01..2026-03-01]");
          }

          // Day of week: Monday-Friday
          DayOfWeek dowE = when.getDayOfWeek();
          if (dowE == DayOfWeek.SUNDAY || dowE == DayOfWeek.SATURDAY) {
            validE = false;
            addReason("Present day of week not in Employee schedule [Mon–Fri]");
          }

          // Time window: [09:00, 17:00)
          LocalTime startE = LocalTime.of(9, 0);
          LocalTime endE = LocalTime.of(17, 0);
          LocalTime tE = when.toLocalTime();

          if (validE && (tE.isBefore(startE) || !tE.isBefore(endE))) {
            validE = false;
            addReason("Present time not in Employee schedule [09:00..17:00]");
          }

          // Employee restrictions
          if (action.equals("lock") || action.equals("unlock")) {
            validE = false;
            addReason("Employees can't lock or unlock doors");
          }

          if (doorId.equals("D1") || doorId.equals("D2")) {
            validE = false;
            addReason("Employees can't access parking doors");
          }

          authorized = validE;
          break;

        case "": // can't do any actions
          authorized = false;
          break;

        default:
          authorized = false;
          logger.debug("This user is '{}'", user.getRole());
          break;
      }

      // authorized = true; // true for debugging
    }
  }
}