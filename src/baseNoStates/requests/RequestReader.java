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

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    this.now = now;
    this.reasons = new ArrayList<>();
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
        + ", authorized=" + authorized
        + ", closed=" + doorClosed
        + ", reasons=" + reasons
        + "}";
  }

  @Override
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

  public void process() {
    // Back to static directories (original design)
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryDoors.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    door.processRequest(this);
    doorClosed = door.isClosed();
  }

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
          LocalDate startDate = LocalDate.of(2025, 9, 1);
          LocalDate endDate = LocalDate.of(2026, 3, 1);
          LocalDate d = when.toLocalDate();
          if (d.isBefore(startDate) || d.isAfter(endDate)) {
            valid = false;
            addReason("Present date not in Manager schedule [2025-09-01..2026-03-01]");
          }
          DayOfWeek dow = when.getDayOfWeek();
          if (dow == DayOfWeek.SUNDAY) {
            valid = false;
            addReason("Present day of week not in Manager schedule [Mon–Sat]");
          }
          LocalTime start = LocalTime.of(8, 0);
          LocalTime end = LocalTime.of(20, 0);
          LocalTime t = when.toLocalTime();
          if (valid && (t.isBefore(start) || !t.isBefore(end))) {
            valid = false;
            addReason("Present time not in Manager schedule [08:00..20:00]");
          }
          authorized = valid;
          break;
        case "Employee":
          boolean validE = true;
          LocalDate startDateE = LocalDate.of(2025, 9, 1);
          LocalDate endDateE = LocalDate.of(2026, 3, 1);
          LocalDate dE = when.toLocalDate();
          if (dE.isBefore(startDateE) || dE.isAfter(endDateE)) {
            validE = false;
            addReason("Present date not in Employee schedule [2025-09-01..2026-03-01]");
          }
          DayOfWeek dowE = when.getDayOfWeek();
          if (dowE == DayOfWeek.SUNDAY || dowE == DayOfWeek.SATURDAY) {
            validE = false;
            addReason("Present day of week not in Employee schedule [Mon–Fri]");
          }
          LocalTime startE = LocalTime.of(9, 0);
          LocalTime endE = LocalTime.of(17, 0);
          LocalTime tE = when.toLocalTime();
          if (validE && (tE.isBefore(startE) || !tE.isBefore(endE))) {
            validE = false;
            addReason("Present time not in Employee schedule [09:00..17:00]");
          }
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
        case "":
          authorized = false;
          break;
        default:
          authorized = false;
          System.out.print("This user is " + user.getRole());
          break;
      }
    }
  }
}
