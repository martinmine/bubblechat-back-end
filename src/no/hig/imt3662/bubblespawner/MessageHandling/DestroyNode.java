package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.MainEnvironment;
import no.hig.imt3662.bubblespawner.Node;
import no.hig.imt3662.bubblespawner.Serializing.NodeLeft;

import java.util.Map;
import java.util.logging.Logger;

/**
 * This messages is created when the node shall be destroyed.
 * It is then broadcasted to other nodes telling this nodes is now gone.
 * Created by Martin on 14/09/24.
 */
public class DestroyNode implements MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(DestroyNode.class.getName());

    @Override
    public void invoke(Map<String, Object> data, String sender) {
        Node node = MainEnvironment.getNodeManager().getNode(sender);

        if (node != null) {
            LOGGER.info("Node requesting to be destroyed: " + node.getId());
            MainEnvironment.getNodeManager().destroyNode(node.getId());
            NodeLeft notice = new NodeLeft(node.getId());
            MainEnvironment.broadcastMessage(notice, node.getLocation(), MainEnvironment.DEFAULT_RADIUS);
        }
    }

    @Override
    public String getIdentifier() {
        return "DESTROY_NODE";
    }
}
