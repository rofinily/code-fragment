package anc.util.mail;

import anc.util.mail.entity.Account;
import anc.util.mail.entity.Mail;
import anc.util.mail.entity.Server;
import anc.util.mail.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSend {
    public static void main(String[] args) {
        Properties props = new Properties();
        Server server = Server.create()
                .host("smtp.qq.com")
                .port(465)
                .protocol(Server.Protocol.SMTP)
                .useSsl(true);
        server.setProperties(props);

        Account acc = Account.of(User.of("shi", "anchore@foxmail.com"), "folovbwogjlnbdib");

        Session session = Session.getDefaultInstance(props);
        try {
            Transport transport = session.getTransport();
            transport.connect(acc.getOwner().getAddr(), acc.getPassword());
            Mail mail = Mail.create()
                    .from(User.of("shi", "anc@foxmail.com"))
                    .to(new User[]{User.of("qq", "1009252731@qq.com")})
                    .cc(null)
                    .bcc(null)
                    .subject("cshi")
                    .content("asdasd");
            Message msg = mail.toMessage();
            Message msg = new MimeMessage(session);
            //发件地址
            Address fromAddress = new InternetAddress(account);
            msg.setFrom(fromAddress);
            //收件地址
            Address toAddress = new InternetAddress(receiver);
            msg.setRecipient(MimeMessage.RecipientType.TO, toAddress);
            //主题
            msg.setSubject(subject);
            //正文
            msg.setContent(content, "text/html; charset=UTF-8");
            msg.saveChanges();
            Transport transport = session.getTransport();
            //连接smtp.qq.com邮件服务器，端口465
            transport.connect(account, password);
            //发送
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
