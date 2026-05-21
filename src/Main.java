public class Main {

    public static void main(String[] args) {

        Group group = new Group("HC Tigers");

        User admin = new User("Karel", true);
        User member = new User("Petr", false);

        group.addMember(admin);
        group.addMember(member);

        new AdminWindow(group, admin);
        new MemberWindow(group, member);
    }
}