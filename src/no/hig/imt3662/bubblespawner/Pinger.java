package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.MessageHandling.DestroyNode;
import no.hig.imt3662.bubblespawner.Serializing.NodeLeft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Martin on 14/09/25.
 */
public class Pinger extends TimerTask {
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
        }

    }


}
