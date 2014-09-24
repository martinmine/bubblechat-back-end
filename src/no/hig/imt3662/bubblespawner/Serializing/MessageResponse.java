package no.hig.imt3662.bubblespawner.Serializing;

import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 14/09/24.
 */
public abstract class MessageResponse {
    private Map<String, Object> message;

    public MessageResponse() {
        this.message = new HashMap<String, Object>();
    }

    public void setValue(String key, Object value) {
        message.put(key, value);
    }

    protected void writeHeader() {
        String identifier = getIdentifier();
        setValue("identifier", identifier);
    }

    public String serializeToJson(String receiver, String nextMessageId) {
        writeHeader();

        Map<String, Object> message = new HashMap<String, Object>();
        message.put("to", receiver);
        message.put("message_id", nextMessageId);
        message.put("data", JSONValue.toJSONString(this.message));

        return JSONValue.toJSONString(message);
    }

    protected String serializeJSON() {
        return JSONValue.toJSONString(this.message);
    }

    public abstract String getIdentifier();
}
