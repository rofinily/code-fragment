package anc.util.mail;

import anc.util.mail.entity.Account;
import anc.util.mail.entity.Mail;
import anc.util.mail.entity.Server;
import anc.util.mail.entity.User;

import javax.mail.*;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) {

        Properties props = new Properties();
        Server server = Server.create()
                .host("imap.qq.com")
                .port(993)
                .protocol(Server.Protocol.IMAP)
                .useSsl(true);
        server.setProperties(props);

        Account acc = Account.of(User.of("shi", "1009252731@qq.com"), "folovbwogjlnbdib");

        Session session = Session.getDefaultInstance(props);
        try {
            Store store = session.getStore();
            store.connect(acc.getOwner().getAddr(), acc.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message msg = folder.getMessage(1);
            System.out.println(Mail.fromMsg(msg));
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

