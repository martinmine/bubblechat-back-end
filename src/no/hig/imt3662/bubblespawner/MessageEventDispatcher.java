package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Packet;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 14/09/24.
 */
public class MessageEventDispatcher implements PacketListener {
    private Map<String, MessageEvent> messageEvents;

    public MessageEventDispatcher() {
        this.messageEvents = new HashMap<String, MessageEvent>();
        // TODO register message events
    }

    private int id = 0;
    @Override
    public void processPacket(Packet packet) throws SmackException.NotConnectedException {
        GcmPacketExtension gcmPacket = (GcmPacketExtension) packet.getExtension(MainEnvironment.GCM_NAMESPACE);

        try {
            Map<String, Object> messageValues = gcmPacket.getJsonValues();

            Object messageType = messageValues.get("message_type");

            String sender = (String)messageValues.get("from");

            Map<String, Object> dataValues = (Map<String, Object>)messageValues.get("data");

            if (dataValues.containsKey("message_type")) { // Communication-level message

            }
            else {

            }
            if (dataValues == null) {
                MainEnvironment.getDefaultLogger().info("NOPE");
                MainEnvironment.getDefaultLogger().info(gcmPacket.toString());
                return; // because is ack
            }

            String value = (String)dataValues.get("my_message");

            MainEnvironment.getDefaultLogger().info("Received message with type: " + messageType);
            MainEnvironment.getDefaultLogger().info("And the message content was: " + value);
            MainEnvironment.getDefaultLogger().info("Actual content: " + gcmPacket.toString());
            MainEnvironment.getDefaultLogger().info("Sent from: " + sender);
            /*

                public static String createJsonMessage(String to, String messageId,
                                           Map<String, String> payload, String collapseKey, Long timeToLive,
                                           Boolean delayWhileIdle) {
             */
            id++;
            Map<String, String> payload = new HashMap<String, String>();
            payload.put("Hello", "World");
            payload.put("CCS", "Dummy Message");
            payload.put("EmbeddedMessageId", String.valueOf(id));
            String collapseKey = "sample";
            Long timeToLive = 10000L;
            String message = CommunicationHandler.instance.createJsonMessage(sender, String.valueOf(id), payload,
                    collapseKey, timeToLive, true);

            CommunicationHandler.instance.sendDownstreamMessage(message);

        } catch (ParseException e) {
            MainEnvironment.getDefaultLogger().severe("Exception during parsing: " + e.toString());
        }
    }
}
