package baseNoStates;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectoryUsers is responsible for defining and initializing
 * all users in the system. Each user is assigned a name,
 * credential, and role before being added to the user list.

 * Implemented as a singleton so that there is a single shared
 * directory of users across the application.

 * Responsibilities:
 * - Populate the system with predefined users through makeUsers().
 * - Provide lookup functionality to find a user based on credentials.

 * Notes:
 * - A warning is logged when a credential lookup fails.
 */
public final class DirectoryUsers {

  private static final Logger logger =
      LoggerFactory.getLogger(DirectoryUsers.class);

  // Singleton instance
  private static final DirectoryUsers INSTANCE = new DirectoryUsers();

  // List with all users
  private final ArrayList<User> users = new ArrayList<>();

  /**
   * Private constructor to enforce singleton.
   */
  private DirectoryUsers() {
    // empty; users are added in makeUsers()
  }

  /**
   * Returns the single instance of DirectoryUsers.
   *
   * @return the singleton instance
   */
  public static DirectoryUsers getInstance() {
    return INSTANCE;
  }

  /**
   * Creates and initializes all users in the system.
   * This method should be called once at startup.
   */
  public void makeUsers() {
    logger.info("Initializing users...");

    users.clear();

    // users without any privilege
    users.add(new User("Bernat", "12345", ""));
    users.add(new User("Blai", "77532", ""));

    // employees
    users.add(new User("Ernest", "74984", "Employee"));
    users.add(new User("Eulalia", "43295", "Employee"));

    // managers
    users.add(new User("Manel", "95783", "Manager"));
    users.add(new User("Marta", "05827", "Manager"));

    // admin
    users.add(new User("Ana", "11343", "Admin"));

    logger.info("Users initialized successfully. Total users: {}",
        users.size());
  }

  /**
   * Finds a user by its credential.
   *
   * @param credential user credential
   * @return the User with the given credential, or null if not found
   */
  public User findUserByCredential(String credential) {
    logger.debug("Searching for user with credential '{}'", credential);

    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        logger.info("User '{}' found for credential '{}'",
            user.toString(), credential);
        return user;
      }
    }

    logger.warn("User with credential '{}' not found", credential);
    return null;
  }
}
