/**
 * Abstract class representing a general user in the system.
 */
public abstract class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract methods to be implemented by subclasses
    public abstract boolean isAdmin();
    public abstract String getRoleName();

    @Override
    public String toString() {
        return name + " (" + getRoleName() + ")";
    }
}