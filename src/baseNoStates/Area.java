package baseNoStates;

import java.util.ArrayList;

public class Area {
  private String id;
  private ArrayList<Door> doors;

  public Area() {
    this.id = "";
    this.doors = new ArrayList<>();
  }

  public Area (String id, ArrayList<Door> doors) {
    this.id = id;
    this.doors = doors;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
}
