package anc.util.mail;

import anc.util.mail.entity.Account;
import anc.util.mail.entity.Mail;
import anc.util.mail.entity.Server;
import anc.util.mail.entity.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Date;
import java.util.Properties;

public class Sender {
    public static void main(String[] args) {
        Properties props = new Properties();
        Server server = Server.create()
                .host("smtp.qq.com")
                .port(465)
                .protocol(Server.Protocol.SMTP)
                .useSsl(true);
        server.setProperty(props);

        Account acc = Account.of(User.of("yang", "anchore@foxmail.com"), "folovbwogjlnbdib");

        Session session = Session.getDefaultInstance(props/*, Auth.create().account(acc)*/);
        Transport transport = null;
        try {
            transport = session.getTransport();
            transport.connect(acc.getOwner().getAddr(), acc.getPassword());

            Mail mail = Mail.create()
                    .from(acc.getOwner())
                    .to(User.of("fangyu", "anchore@foxmail.com"))
                    .cc(User.EMPTY_USERS)
                    .bcc(User.EMPTY_USERS)
                    .subject("ceshi")
                    .sentDate(new Date())
                    .content("xinyoujian");
            Message msg = mail.toMessage(session);
            msg.saveChanges();

            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
