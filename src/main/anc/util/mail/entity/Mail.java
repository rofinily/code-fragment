package anc.util.mail.entity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

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
            return Mail.create()
                    .from(fromAddr(msg.getFrom()))
                    .to(fromAddr(msg.getRecipients(Message.RecipientType.TO)))
                    .cc(fromAddr(msg.getRecipients(Message.RecipientType.CC)))
                    .bcc(fromAddr(msg.getRecipients(Message.RecipientType.BCC)))
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

    public static String getContent(Message msg) throws IOException, MessagingException {
        StringBuilder sb = new StringBuilder();
        resolveContent(msg, sb);
        return sb.toString();
    }

    private static void resolveContent(Part part, StringBuilder sb) throws MessagingException, IOException {
        if (part.isMimeType("text/html")) {
            sb.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            resolveContent((Part) part.getContent(), sb);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0, count = multipart.getCount(); i < count; i++) {
                resolveContent(multipart.getBodyPart(i), sb);
            }
        }
    }

    public static User fromAddr(Address addr) {
        if (addr instanceof InternetAddress) {
            InternetAddress inetAddr = ((InternetAddress) addr);
            return User.of(inetAddr.getPersonal(), inetAddr.getAddress());
        }
        return null;
    }

    public static User[] fromAddr(Address[] addrs) {
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

    public Mail from(User[] from) {
        this.from = from;
        return this;
    }

    public Mail to(User[] to) {
        this.to = to;
        return this;
    }

    public Mail cc(User[] cc) {
        this.cc = cc;
        return this;
    }

    private Mail bcc(User[] bcc) {
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
