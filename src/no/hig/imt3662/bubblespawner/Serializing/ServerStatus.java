package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Created by Martin on 14/09/24.
 */
public class ServerStatus extends MessageResponse {
    public ServerStatus(int userCount) {
        super();
        setValue("user_count", String.valueOf(userCount));
    }

    @Override
    public String getIdentifier() {
        return "SERVER_STATUS";
    }
}
