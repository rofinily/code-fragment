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
        String prot = protocol.getProtocol();
        protocol.setProperty(props);
        props.setProperty("mail." + prot + ".ssl.enable", String.valueOf(useSsl));
        props.setProperty("mail." + prot + ".host", host);
        props.setProperty("mail." + prot + ".port", String.valueOf(port));
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
                props.setProperty("mail.transport.protocol", protocol);
            }
        },
        IMAP("imap") {
            @Override
            void setProperty(Properties props) {
                props.setProperty("mail.store.protocol", protocol);
                props.setProperty("mail.imap.partialfetch", "false");
            }
        },
        POP3("pop3") {
            @Override
            void setProperty(Properties props) {
                props.setProperty("mail.store.protocol", protocol);
                props.setProperty("mail.pop3.partialfetch", "false");
            }
        };

        Protocol(String protocol) {
            this.protocol = protocol;
        }

        String protocol;

        abstract void setProperty(Properties props);

        public String getProtocol() {
            return protocol;
        }
    }
}
