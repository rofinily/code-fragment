package me.anchore.mail.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * @author anchore
 */
public class Mail {
    private User[] from, to, cc, bcc;
    private Date sentDate, receivedDate;
    private String subject, content;
    private boolean seen;

    public User[] getFrom() {
        return from;
    }

    public User[] getTo() {
        return to;
    }

    public User[] getCc() {
        return cc;
    }

    public User[] getBcc() {
        return bcc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public boolean isSeen() {
        return seen;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Mail mail = new Mail();

        public Builder from(User... from) {
            mail.from = from;
            return this;
        }

        public Builder to(User... to) {
            mail.to = to;
            return this;
        }

        public Builder cc(User... cc) {
            mail.cc = cc;
            return this;
        }

        public Builder bcc(User... bcc) {
            mail.bcc = bcc;
            return this;
        }

        public Builder sentDate(Date sentDate) {
            mail.sentDate = sentDate;
            return this;
        }

        public Builder receivedDate(Date receivedDate) {
            mail.receivedDate = receivedDate;
            return this;
        }

        public Builder subject(String subject) {
            mail.subject = subject;
            return this;
        }

        public Builder content(String content) {
            mail.content = content;
            return this;
        }

        public Builder seen(boolean seen) {
            mail.seen = seen;
            return this;
        }

        public Mail build() {
            return mail;
        }
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

    /**
     * @author anchore
     * @date 2018/7/15
     */
    public enum ContentType {
        //
        TEXT_HTML("text/html"),
        TEXT_PLAIN("text/html"),
        MESSAGE_RFC822("message/rfc822"),
        MULTIPART_("multipart/*");

        String type;

        ContentType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
