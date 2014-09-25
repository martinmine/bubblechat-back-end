package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Created by Martin on 14/09/24.
 */
public class NodeIdle extends MessageResponse {
    public NodeIdle(int userID) {
        super();
        setValue("user_id", userID);
    }

    @Override
    public String getIdentifier() {
        return "NODE_IDLE";
    }
}
