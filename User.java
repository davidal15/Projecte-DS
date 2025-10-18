package baseNoStates;

public class User {
  private final String name;
  private final String credential;
  private final String role;

  public User(String name, String credential, String role) {
    this.name = name;
    this.credential = credential;
    this.role = role;
  }

  public String getCredential() {
    return credential;
  }
  public String getRole() { return role; }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
