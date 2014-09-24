package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.xmlpull.v1.XmlPullParser;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class CommunicationHandler {

    private Logger logger;

    private String gcmServer;
    private int gcmPort;


    private XMPPConnection connection;

    public static CommunicationHandler instance; //TODO remove this

    public CommunicationHandler(String gcmServer, int gcmPort) {
        instance = this; // TODO remove this
        this.gcmServer = gcmServer;
        this.gcmPort = gcmPort;
        this.logger = Logger.getLogger("SmackCcsClient");

        ProviderManager.addExtensionProvider(MainEnvironment.GCM_ELEMENT_NAME, MainEnvironment.GCM_NAMESPACE,
                new PacketExtensionProvider() {
                    @Override
                    public PacketExtension parseExtension(XmlPullParser parser) throws
                            Exception {
                        String json = parser.nextText();
                        return new GcmPacketExtension(json);
                    }
                });
    }

    private String nextMessageId() {
        return "m-" + UUID.randomUUID().toString();
    }

    protected void send(String jsonRequest) throws SmackException.NotConnectedException {
        Packet request = new GcmPacketExtension(jsonRequest).toPacket();
        connection.sendPacket(request);
    }


    /**
     * Connects to GCM Cloud Connection Server using the supplied credentials.
     *
     * @param senderId Your GCM project number
     * @param apiKey API Key of your project
     */
    public void connect(long senderId, String apiKey)
            throws XMPPException, IOException, SmackException {
        ConnectionConfiguration config = new ConnectionConfiguration(this.gcmServer, this.gcmPort);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
        config.setReconnectionAllowed(true);
        config.setRosterLoadedAtLogin(false);
        config.setSendPresence(false);
        config.setSocketFactory(SSLSocketFactory.getDefault());

        connection = new XMPPTCPConnection(config);
        connection.connect();

        connection.addConnectionListener(new LoggingConnectionListener());

        // Handle incoming packets
        connection.addPacketListener(new MessageEventDispatcher(), new PacketTypeFilter(Message.class));

        // Log all outgoing packets
        connection.addPacketInterceptor(new PacketInterceptor() {
            @Override
            public void interceptPacket(Packet packet) {
                logger.log(Level.INFO, "Sent: {0}", packet.toXML());
            }
        }, new PacketTypeFilter(Message.class));

        connection.login(senderId + "@gcm.googleapis.com", apiKey);
    }

    public void sendMessage(MessageResponse response, String sender) {
        String jsonText = response.serializeToJson(sender, nextMessageId());
        try {
            send(jsonText);
        } catch (SmackException.NotConnectedException e) {
            // TODO
        }
    }
}
