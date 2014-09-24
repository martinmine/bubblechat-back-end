package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Created by Martin on 14/09/24.
 */
public class ChatMessage extends MessageResponse {
    public ChatMessage() {
        super();
    }

    @Override
    public String getIdentifier() {
        return "BROADCAST_MESSAGE";
    }
}
