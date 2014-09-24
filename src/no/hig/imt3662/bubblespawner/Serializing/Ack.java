package no.hig.imt3662.bubblespawner.Serializing;

import org.json.simple.JSONValue;

/**
 * Created by Martin on 14/09/24.
 */
public class Ack extends MessageResponse {
    private String messageID;

    public Ack(String messageID) {
        super();
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
    public String serializeToJson(String receiver, String nextMessageId) {
        writeHeader();
        setValue("to", receiver);
        setValue("message_id", messageID);

        return serializeJSON();
    }
}
