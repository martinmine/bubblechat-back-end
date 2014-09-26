package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.NodeLeft;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/25.
 */
public class Pinger extends TimerTask {
    private static final Logger LOGGER = Logger.getLogger(Pinger.class.getName());
    private Timer pingScheduler;
    private long pingInterval;

    public Pinger(long pingInterval) {
        this.pingInterval = pingInterval;
        this.pingScheduler = new Timer();
        this.pingScheduler.schedule(this, pingInterval, pingInterval);
    }

    @Override
    public void run() {
        List<Node> nodes = MainEnvironment.getNodeManager().findTimedOutNodes(2 * pingInterval);
        for (Node node : nodes) {
            NodeLeft leaveMessage = new NodeLeft(node.getId());
            MainEnvironment.broadcastMessage(leaveMessage, node.getLocation(), MainEnvironment.DEFAULT_RADIUS);
            MainEnvironment.getNodeManager().destroyNode(node.getId());
            LOGGER.info("Node " + node.getId() + " timed out");
        }
    }
}
