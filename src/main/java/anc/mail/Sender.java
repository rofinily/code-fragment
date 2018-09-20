package anc.mail;

import anc.mail.entity.Account;
import anc.mail.entity.Mail;
import anc.mail.entity.Server;
import anc.mail.entity.User;
import anc.mail.util.MailUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

/**
 * @author anchore
 */
public class Sender {
    public static void main(String[] args) {
        Properties props = new Properties();
        Server server = Server.builder().
                host("smtp.qq.com").
                port(465).
                protocol(Server.Protocol.SMTP).
                useSsl(true).build();
        server.setProperty(props);

        Account acc = Account.of(User.of("yang", "anchore@foxmail.com"), "folovbwogjlnbdib");

        Session session = Session.getDefaultInstance(props);
        try (Transport transport = session.getTransport()) {
            transport.connect(acc.getOwner().getAddress(), acc.getPassword());

            Mail mail = Mail.builder()
                    .from(acc.getOwner())
                    .to(User.of("fangyu", "anchore@foxmail.com"))
                    .cc()
                    .bcc()
                    .subject("ceshi")
                    .sentDate(new Date())
                    .content("xinyoujian").build();
            Optional<Message> msg = MailUtil.toMessage(session, mail);
            if (msg.isPresent()) {
                Message message = msg.get();
                transport.sendMessage(message, message.getAllRecipients());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
