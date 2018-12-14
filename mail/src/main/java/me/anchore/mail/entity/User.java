package me.anchore.mail.entity;

/**
 * @author anchore
 */
public class User {
    private String name, address;

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static User of(String name, String address) {
        return new User(name, address);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
