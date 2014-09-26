package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * XMPP Packet Extension for GCM Cloud Connection Server.
 * This class is copied and modified from
 *   https://developer.android.com/google/gcm/ccs.html
 */
final class GcmPacketExtension extends DefaultPacketExtension {

    private final String json;

    public GcmPacketExtension(String json) {
        super(MainEnvironment.GCM_ELEMENT_NAME, MainEnvironment.GCM_NAMESPACE);
        this.json = json;
    }

    public Map<String, Object> getJsonValues() throws ParseException{
        return (Map<String, Object>) JSONValue.parseWithException(this.json);
    }

    @Override
    public String toXML() {
        return String.format("<%s xmlns=\"%s\">%s</%s>", MainEnvironment.GCM_ELEMENT_NAME,
                MainEnvironment.GCM_NAMESPACE, StringUtils.escapeForXML(json), MainEnvironment.GCM_ELEMENT_NAME);
    }

    @Override
    public String toString() {
        return this.json;
    }

    public Packet toPacket() {
        Message message = new Message();
        message.addExtension(this);
        return message;
    }
}
