package Model.User;

/**
 * Subclass representing an Administrator.
 */
public class Admin extends User {

    public Admin(String name) {
        super(name);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }
}