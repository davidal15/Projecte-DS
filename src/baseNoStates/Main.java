package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.milestone1.DirectoryAreas;
import baseNoStates.milestone1.DirectoryDoors;
import baseNoStates.milestone1.DirectoryUsers;

public class Main {
  public static void main(String[] args) {
    DirectoryDoors.getInstance().makeDoors();
    DirectoryUsers.getInstance().makeUsers();
    DirectoryAreas.getInstance().makeAreas();
    new WebServer();
  }
}