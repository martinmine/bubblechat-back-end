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

    protected void setValue(String key, Object value) {
        message.put(key, value);
    }

    public String serializeToJosn() {
        String identifier = getIdentifier();
        if (identifier != null)
            setValue("identifier", identifier);

        return JSONValue.toJSONString(message);
    }

    public abstract String getIdentifier();
}
