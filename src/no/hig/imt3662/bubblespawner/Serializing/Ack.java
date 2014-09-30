package no.hig.imt3662.bubblespawner.Serializing;


/**
 * Template for responding to ack messages
 * Created by Martin on 14/09/24.
 */
public class Ack extends MessageResponse {
    private String messageID;

    public Ack(String messageID) {
        this.messageID = messageID;
    }

    @Override
    public String getIdentifier() {
        return "ack";
    }

    @Override
    protected void writeHeader() {
        String identifier = getIdentifier();
        setValue("message_type", identifier);
    }

    @Override
    public String serializeToJson(String receiver, String messageID) {
        writeHeader();
        setValue("to", receiver);
        setValue("message_id", this.messageID);

        return serializeJSON();
    }
}
