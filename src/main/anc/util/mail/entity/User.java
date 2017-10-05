package anc.util.mail.entity;

public class User {
    private final String name;
    private final String addr;

    private User(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }

    public static User of(String name, String addr) {
        return new User(name, addr);
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
