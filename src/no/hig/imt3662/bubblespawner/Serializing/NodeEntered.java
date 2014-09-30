package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Message used when a node is entering in an area
 * Created by Martin on 14/09/24.
 */
public class NodeEntered extends MessageResponse {
    public NodeEntered(int userID) {
        setValue("user_id", userID);
    }

    @Override
    public String getIdentifier() {
        return "NODE_ENTER";
    }
}
