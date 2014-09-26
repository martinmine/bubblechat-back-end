package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.NodeLeft;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Removes dead nodes
 * Created by Martin on 14/09/25.
 */
public class NodeVerifier extends TimerTask {
    private static final Logger LOGGER = Logger.getLogger(NodeVerifier.class.getName());
    private Timer scheduler;
    private long interval;

    /**
     * Prepares and starts the verifier
     * @param interval How often to check for dead nodes in seconds
     */
    public NodeVerifier(long interval) {
        this.interval = interval;
        this.scheduler = new Timer();
        this.scheduler.schedule(this, interval, interval);
    }

    @Override
    public void run() {
        List<Node> nodes = MainEnvironment.getNodeManager().findTimedOutNodes(2 * interval);
        for (Node node : nodes) {
            NodeLeft leaveMessage = new NodeLeft(node.getId());
            MainEnvironment.broadcastMessage(leaveMessage, node.getLocation(), MainEnvironment.DEFAULT_RADIUS);
            MainEnvironment.getNodeManager().destroyNode(node.getId());
            LOGGER.info("Node " + node.getId() + " timed out");
        }
    }
}
