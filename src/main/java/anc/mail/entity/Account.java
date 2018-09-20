package anc.mail.entity;

/**
 * @author anchore
 */
public class Account {
    private final User owner;
    private final String password;

    public Account(User owner, String password) {
        this.owner = owner;
        this.password = password;
    }

    public static Account of(User owner, String password) {
        return new Account(owner, password);
    }

    public User getOwner() {
        return owner;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner=" + owner +
                ", password='" + password + '\'' +
                '}';
    }
}
