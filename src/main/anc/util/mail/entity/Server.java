package anc.util.mail.entity;

import java.util.Properties;

public class Server {
    String host;
    int port;
    Server.Protocol protocol;
    boolean useSsl;


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

    public void setProperties(Properties props) {
        String prot = protocol.getProtocol();
        protocol.setProperties(props);
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
            void setProperties(Properties props) {
                props.setProperty("mail.transport.protocol", getProtocol());
            }
        },
        IMAP("imap") {
            @Override
            void setProperties(Properties props) {
                props.setProperty("mail.store.protocol", getProtocol());
            }
        },
        POP3("pop3") {
            @Override
            void setProperties(Properties props) {
                props.setProperty("mail.store.protocol", getProtocol());
            }
        };

        Protocol(String protocol) {
            this.protocol = protocol;
        }

        String protocol;

        abstract void setProperties(Properties props);

        public String getProtocol() {
            return protocol;
        }
    }
}
