package anc.util.mail.entity;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

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

    public static User fromAddr(Address addr) {
        if (addr instanceof InternetAddress) {
            InternetAddress inetAddr = ((InternetAddress) addr);
            return of(inetAddr.getPersonal(), inetAddr.getAddress());
        }
        return null;
    }

    public static Address toAddr(User user) throws UnsupportedEncodingException {
        return new InternetAddress(user.name, user.addr);
    }

    public static User[] fromAddrs(Address[] addrs) {
        if (addrs == null) {
            return new User[0];
        }
        int len = addrs.length;
        User[] users = new User[len];
        for (int i = 0; i < len; i++) {
            users[i] = fromAddr(addrs[i]);
        }
        return users;
    }

    public static Address[] toAddrs(User[] users) throws UnsupportedEncodingException {
        if (users == null) {
            return new Address[0];
        }
        int len = users.length;
        Address[] addrs = new Address[len];
        for (int i = 0; i < len; i++) {
            addrs[i] = toAddr(users[i]);
        }
        return addrs;
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
