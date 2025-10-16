package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main {
  public static void main(String[] args) {
    DirectoryDoors.makeDoors();
    DirectoryUsers.makeUsers();
    System.out.println(DirectorySpaces.findDoorBySpaceId("hall"));
    new WebServer();
  }
}
