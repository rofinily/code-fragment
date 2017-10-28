package anc.util.mail.entity;

import java.util.Properties;

public class Server {
    private String host;
    private int port;
    private Server.Protocol protocol;
    private boolean useSsl;

    public static Server create() {
        return new Server();
    }

    public Server host(String host) {
        this.host = host;
        return this;
    }

    public Server port(int port) {
        this.port = port;
        return this;
    }

    public Server protocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public Server useSsl(boolean useSsl) {
        this.useSsl = useSsl;
        return this;
    }

    public void setProperty(Properties props) {
        protocol.setProperty(props);
        String name = protocol.getName();
        props.setProperty("mail." + name + ".host", host);
        props.setProperty("mail." + name + ".port", String.valueOf(port));
        props.setProperty("mail." + name + ".ssl.enable", String.valueOf(useSsl));
    }

    @Override
    public String toString() {
        return "Server{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", protocol=" + protocol +
                ", useSsl=" + useSsl +
                '}';
    }

    public enum Protocol {
        SMTP("smtp") {
            @Override
            void setProperty(Properties props) {
                props.setProperty("mail.transport.protocol", name);
            }
        },
        IMAP("imap") {
            @Override
            void setProperty(Properties props) {
                props.setProperty("mail.store.protocol", name);
                props.setProperty("mail.imap.partialfetch", "false");
            }
        },
        POP3("pop3") {
            @Override
            void setProperty(Properties props) {
                props.setProperty("mail.store.protocol", name);
                props.setProperty("mail.pop3.partialfetch", "false");
            }
        };

        Protocol(String name) {
            this.name = name;
        }

        String name;

        abstract void setProperty(Properties props);

        public String getName() {
            return name;
        }
    }
}
