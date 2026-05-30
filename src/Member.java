/**
 * Subclass representing a standard Member.
 */
public class Member extends User {

    public Member(String name) {
        super(name);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public String getRoleName() {
        return "Member";
    }
}