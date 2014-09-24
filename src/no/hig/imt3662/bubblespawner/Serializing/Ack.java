package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Created by Martin on 14/09/24.
 */
public class Ack extends MessageResponse {
    public Ack(String receiverID, String messageID) {
        super();
        setValue("message_type", "ack");
        setValue("to", receiverID);
        setValue("message_id", messageID);
    }

    @Override
    public String getIdentifier() {
        return null; // since this is a comm-related message, we return null
    }
}
