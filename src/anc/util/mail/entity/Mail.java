package anc.util.mail.entity;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author anchore
 */
public class Mail {

    private User[] from;
    private User[] to;
    private User[] cc;
    private User[] bcc;
    private Date sentDate;
    private Date receivedDate;
    private String subject;
    private String content;
    private boolean seen;

    public static Mail create() {
        return new Mail();
    }

    public static Mail fromMsg(Message msg) {
        try {
            return create()
                    .from(User.fromAddrs(msg.getFrom())[0])
                    .to(User.fromAddrs(msg.getRecipients(Message.RecipientType.TO)))
                    .cc(User.fromAddrs(msg.getRecipients(Message.RecipientType.CC)))
                    .bcc(User.fromAddrs(msg.getRecipients(Message.RecipientType.BCC)))
                    .sentDate(msg.getSentDate())
                    .receivedDate(msg.getReceivedDate())
                    .subject(msg.getSubject())
                    .content(getContent(msg))
                    .seen(msg.isSet(Flags.Flag.SEEN));
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message toMessage(Session session) {
        Message msg = new MimeMessage(session);
        try {
            msg.addFrom(User.toAddrs(from));
            msg.setRecipients(Message.RecipientType.TO, User.toAddrs(to));
            msg.setRecipients(Message.RecipientType.CC, User.toAddrs(cc));
            msg.setRecipients(Message.RecipientType.BCC, User.toAddrs(bcc));
            msg.setSubject(subject);
            msg.setSentDate(sentDate);
            msg.setContent(content, "text/html; charset=UTF-8");
            return msg;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getContent(Message msg) throws IOException, MessagingException {
        MailContent mc = new MailContent();
        resolveContent(msg, mc);
        return mc.getDefault();
    }

    private static void resolveContent(Part part, MailContent mc) throws MessagingException, IOException {
        if (part.isMimeType("text/html")) {
            mc.put("text/html", part.getContent().toString());
            return;
        }
        if (part.isMimeType("text/plain")) {
            mc.put("text/plain", part.getContent().toString());
            return;
        }
        if (part.isMimeType("message/rfc822")) {
            resolveContent((Part) part.getContent(), mc);
            return;
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0, count = multipart.getCount(); i < count; i++) {
                resolveContent(multipart.getBodyPart(i), mc);
            }
        }
    }

    public Mail from(User... from) {
        this.from = from;
        return this;
    }

    public Mail to(User... to) {
        this.to = to;
        return this;
    }

    public Mail cc(User... cc) {
        this.cc = cc;
        return this;
    }

    public Mail bcc(User... bcc) {
        this.bcc = bcc;
        return this;
    }

    public Mail sentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public Mail receivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    public Mail subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Mail content(String content) {
        this.content = content;
        return this;
    }

    public Mail seen(boolean seen) {
        this.seen = seen;
        return this;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "from=" + Arrays.toString(from) +
                ", to=" + Arrays.toString(to) +
                ", cc=" + Arrays.toString(cc) +
                ", bcc=" + Arrays.toString(bcc) +
                ", sentDate=" + sentDate +
                ", receivedDate=" + receivedDate +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", seen=" + seen +
                '}';
    }

}
