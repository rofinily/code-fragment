package anc.util.mail.entity;

import java.util.Arrays;

public class Mail {
    private User from;
    private User[] to;
    private User[] cc;
    private String subject;
    private byte[] content;

    public static Mail create() {
        return new Mail();
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

    public Mail subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Mail content(byte[] content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "from=" + from +
                ", to=" + Arrays.toString(to) +
                ", cc=" + Arrays.toString(cc) +
                ", subject='" + subject + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }

}
