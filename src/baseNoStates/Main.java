package baseNoStates;

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