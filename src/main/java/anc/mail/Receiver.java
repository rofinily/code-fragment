package anc.mail;

import anc.mail.entity.Account;
import anc.mail.entity.Server;
import anc.mail.entity.User;
import anc.mail.util.MailUtil;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

/**
 * @author anchore
 */
public class Receiver {
    public static void main(String[] args) {
        Properties props = new Properties();
        Server server = Server.builder().
                host("imap.qq.com").
                port(993).
                protocol(Server.Protocol.IMAP).
                useSsl(true).build();
        server.setProperty(props);

        Account acc = Account.of(User.of("shi", "anchore@foxmail.com"), "folovbwogjlnbdib");

        Session session = Session.getDefaultInstance(props);
        try {
            Store store = session.getStore();
            store.connect(acc.getOwner().getAddress(), acc.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message msg = folder.getMessage(folder.getMessageCount() - 3);
            System.out.println(MailUtil.fromMessage(msg));
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
