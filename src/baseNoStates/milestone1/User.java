package baseNoStates.milestone1;


/** Represents a user within the system, including their name,
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

  public String getName() {
    return this.name;
  }

  public String getCredential() {
    return this.credential;
  }

  public String getRole() {
    return this.role;
  }

  // toString() method returns a readable representation excluding the role.
  @Override
  public String toString() {
    return "name=" + name + ", credential=" + credential;
  }
}
