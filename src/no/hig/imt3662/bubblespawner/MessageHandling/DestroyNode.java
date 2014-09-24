package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.MainEnvironment;

import java.util.Map;

/**
 * Created by marti_000 on 14/09/24.
 */
public class DestroyNode implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        MainEnvironment.getDefaultLogger().info("Destroying node");
    }

    @Override
    public String getIdentifier() {
        return "DESTROY_NODE";
    }
}
