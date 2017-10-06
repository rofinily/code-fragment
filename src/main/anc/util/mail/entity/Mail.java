package anc.util.mail.entity;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

public class Mail {
    private User from;
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
            msg.addFrom(new Address[]{User.toAddr(from)});
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContent(Message msg) throws IOException, MessagingException {
        StringBuilder sb = new StringBuilder();
        resolveContent(msg, sb);
        return sb.toString();
    }

    private static void resolveContent(Part part, StringBuilder sb) throws MessagingException, IOException {
        if (part.isMimeType("text/html")) {
            sb.append(part.getContent().toString());
            return;
        }
        if (part.isMimeType("text/plain")) {
            sb.append(part.getContent().toString());
            return;
        }
        if (part.isMimeType("message/rfc822")) {
            resolveContent((Part) part.getContent(), sb);
            return;
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0, count = multipart.getCount(); i < count; i++) {
                resolveContent(multipart.getBodyPart(i), sb);
            }
        }
    }

    public Mail from(User from) {
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

    public Mail bcc(User[] bcc) {
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
                "from=" + from +
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
