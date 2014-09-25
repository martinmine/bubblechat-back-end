package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.MainEnvironment;
import no.hig.imt3662.bubblespawner.Node;
import no.hig.imt3662.bubblespawner.Serializing.NodeIdle;

import java.util.Map;

/**
 * Created by Martin on 14/09/25.
 */
public class Idle implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        Node node = MainEnvironment.getNodeManager().getNode(sender);
        NodeIdle response = new NodeIdle(node.getId());

        MainEnvironment.broadcastMessage(response, node.getLocation(), MainEnvironment.DEFAULT_RADIUS);
    }

    @Override
    public String getIdentifier() {
        return "IDLE";
    }
}
