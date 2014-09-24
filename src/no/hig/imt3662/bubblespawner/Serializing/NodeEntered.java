package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Created by Martin on 14/09/24.
 */
public class NodeEntered extends MessageResponse {
    public NodeEntered() {
        super();
    }

    @Override
    public String getIdentifier() {
        return "NODE_ENTER";
    }
}
