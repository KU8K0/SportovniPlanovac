public class Main {

    public static void main(String[] args) {

        Group group = new Group("HC Tigers");

        User admin = new Admin("Karel");
        User member = new Member("Petr");

        group.addMember(admin);
        group.addMember(member);

        new AdminWindow(group, admin);
        new MemberWindow(group, member);
    }
}