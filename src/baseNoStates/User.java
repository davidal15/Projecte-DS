package baseNoStates;


/* Represents a user within the system, including their name,
 * authentication credential, and assigned role.
 * The class is immutable; user properties are final and set at construction time.
 */
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

  public String getRole() {
    return role;
  }

  // toString() method returns a readable representation excluding the role.
  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
