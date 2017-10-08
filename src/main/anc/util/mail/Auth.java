package anc.util.mail;

import anc.util.mail.entity.Account;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {
    private Account account;

    public static Auth create() {
        return new Auth();
    }

    public Auth account(Account account) {
        this.account = account;
        return this;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(account.getOwner().getAddr(), account.getPassword());
    }
}
