public class User {

    private String name;
    private boolean admin;

    public User(String name, boolean admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return name;
    }
}