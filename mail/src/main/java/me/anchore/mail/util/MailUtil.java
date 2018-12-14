package me.anchore.mail.util;

import me.anchore.mail.entity.Mail;
import me.anchore.mail.entity.MailContent;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;

import static me.anchore.mail.entity.Mail.ContentType.MESSAGE_RFC822;
import static me.anchore.mail.entity.Mail.ContentType.MULTIPART_;
import static me.anchore.mail.entity.Mail.ContentType.TEXT_HTML;
import static me.anchore.mail.entity.Mail.ContentType.TEXT_PLAIN;

/**
 * @author anchore
 * @date 2018/7/15
 */
public class MailUtil {
    public static Optional<Mail> fromMessage(Message msg) {
        try {
            return Optional.of(Mail.builder().
                    from(UserUtil.fromAddresses(msg.getFrom())[0]).
                    to(UserUtil.fromAddresses(msg.getRecipients(Message.RecipientType.TO))).
                    cc(UserUtil.fromAddresses(msg.getRecipients(Message.RecipientType.CC))).
                    bcc(UserUtil.fromAddresses(msg.getRecipients(Message.RecipientType.BCC))).
                    sentDate(msg.getSentDate()).
                    receivedDate(msg.getReceivedDate()).
                    subject(msg.getSubject()).
                    seen(msg.isSet(Flags.Flag.SEEN)).
                    build()
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Message> toMessage(Session session, Mail mail) {
        Message msg = new MimeMessage(session);
        try {
            msg.addFrom(UserUtil.toAddresses(mail.getFrom()));
            msg.setRecipients(Message.RecipientType.TO, UserUtil.toAddresses(mail.getTo()));
            msg.setRecipients(Message.RecipientType.CC, UserUtil.toAddresses(mail.getCc()));
            msg.setRecipients(Message.RecipientType.BCC, UserUtil.toAddresses(mail.getBcc()));
            msg.setSubject(mail.getSubject());
            msg.setSentDate(mail.getSentDate());
            msg.setContent(mail.getContent(), "text/html; charset=UTF-8");
            return Optional.of(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static String getContent(Message msg) throws IOException, MessagingException {
        MailContent mc = new MailContent();
        resolveContent(msg, mc);
        return mc.getDefault();
    }

    private static void resolveContent(Part part, MailContent mc) throws MessagingException, IOException {
        if (part.isMimeType(TEXT_HTML.getType())) {
            mc.put(TEXT_HTML.getType(), part.getContent().toString());
            return;
        }
        if (part.isMimeType(TEXT_PLAIN.getType())) {
            mc.put(TEXT_HTML.getType(), part.getContent().toString());
            return;
        }
        if (part.isMimeType(MESSAGE_RFC822.getType())) {
            resolveContent((Part) part.getContent(), mc);
            return;
        }
        if (part.isMimeType(MULTIPART_.getType())) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0, count = multipart.getCount(); i < count; i++) {
                resolveContent(multipart.getBodyPart(i), mc);
            }
        }
    }
}