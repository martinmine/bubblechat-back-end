package no.hig.imt3662.bubblespawner.Serializing;

import no.hig.imt3662.bubblespawner.MainEnvironment;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public abstract class MessageResponse {
    private static final Logger LOGGER = Logger.getLogger(MessageResponse.class.getName());
    private Map<String, Object> message;

    /**
     * Create a new MessageResponse and prepare it for usage
     */
    public MessageResponse() {
        this.message = new HashMap<String, Object>();
    }

    /**
     * Set a value in the response (key: value in JSON)
     * @param key Identifier
     * @param value Actual value
     */
    public void setValue(String key, Object value) {
        message.put(key, value);
    }

    protected void writeHeader() {
        String identifier = getIdentifier();
        setValue("identifier", identifier);
    }

    /**
     * Serializes the response to a JSON string
     * @param receiver ID of whom is receiving the message
     * @param messageID Unique ID of the message
     * @return JSON text
     */
    public String serializeToJson(String receiver, String messageID) {
        writeHeader();

        Map<String, Object> message = new HashMap<String, Object>();
        message.put("to", receiver);
        message.put("message_id", messageID);
        message.put("data", this.message);
        String json = JSONValue.toJSONString(message);
        LOGGER.info("Sending json: " + json);
        return json;
    }

    protected String serializeJSON() {
        return JSONValue.toJSONString(this.message);
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    /**
     * Gets the identifier of the message
     * @return Identifier
     */
    public abstract String getIdentifier();
}
