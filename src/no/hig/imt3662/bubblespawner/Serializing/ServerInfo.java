package no.hig.imt3662.bubblespawner.Serializing;

/**
 * Server info message which gives general information about the server status
 * Created by Martin on 14/09/24.
 */
public class ServerInfo extends MessageResponse {
    public ServerInfo(int userCount, int userID) {
        super();
        setValue("user_count", String.valueOf(userCount));
        setValue("user_id", userID);
    }

    @Override
    public String getIdentifier() {
        return "SERVER_STATUS";
    }
}
