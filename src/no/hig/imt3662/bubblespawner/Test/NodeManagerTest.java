package no.hig.imt3662.bubblespawner.Test;

import no.hig.imt3662.bubblespawner.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class NodeManagerTest {
    private final Location nodeLocation;
    private final String gcmKey;
    private int nodeId;

    public NodeManagerTest() throws IOException{
        this.nodeLocation = new Location(60.7898947d, 10.6824657d);
        this.gcmKey = "testKey";
        MainEnvironment.initialize(true);

        int nodeId = MainEnvironment.getNodeManager().getNodeID(this.gcmKey);
        if (nodeId > 0)
            MainEnvironment.getNodeManager().destroyNode(nodeId);
    }

    private void registerNode() {
        this.nodeId = MainEnvironment.getNodeManager().registerNode(this.gcmKey, this.nodeLocation);
    }

    @Test
    public void testGetNodesNearby() throws Exception {
        registerNode();
        List<Node> nodes = MainEnvironment.getNodeManager().getNodesNearby(this.nodeLocation, 20);

        assertEquals(nodes.size(), 1);
    }

    @Test
    public void testUpdateNodeLocation() throws Exception {
        registerNode();
        Location tmpLocation = new Location(2, 2);
        MainEnvironment.getNodeManager().updateNodeLocation(this.gcmKey, tmpLocation);
        List<Node> nodes = MainEnvironment.getNodeManager().getNodesNearby(tmpLocation, 20);

        assertEquals(nodes.size(), 1);
    }

    @Test
    public void testRegisterNode() throws Exception {
        registerNode();
        assertEquals(this.nodeId, MainEnvironment.getNodeManager().getNodeID(this.gcmKey));
    }

    @Test
    public void testGetNode() throws Exception {
        registerNode();
        Node node = MainEnvironment.getNodeManager().getNode(this.gcmKey);
        assertEquals(this.nodeId, node.getId());
        assertEquals(this.gcmKey, node.getKey());
        assertEquals(this.nodeLocation.getLatitude(), node.getLocation().getLatitude(), 0);
        assertEquals(this.nodeLocation.getLongitude(), node.getLocation().getLongitude(), 0);

    }

    @Test
    public void testSetNodePinged() throws Exception {
        registerNode();
        MainEnvironment.getNodeManager().setNodePinged(this.gcmKey);
        List<Node> deadNodes = MainEnvironment.getNodeManager().findTimedOutNodes(MainEnvironment.getCurrentTimestamp() - 500);

        for (Node node : deadNodes) {
            assertNotEquals(node.getId(), this.nodeId);
        }
    }

    @Test
    public void testGetNodeCount() throws Exception {
        registerNode();
        assertEquals(1, MainEnvironment.getNodeManager().getNodeCount(this.nodeLocation, 200));
    }

    @Test
    public void testGetNodeID() throws Exception {
        registerNode();
        assertEquals(this.nodeId, MainEnvironment.getNodeManager().getNodeID(this.gcmKey));
    }

    @Test
    public void testFindTimedOutNodes() throws Exception {
        registerNode();
        MainEnvironment.getNodeManager().setNodePinged(this.gcmKey);
        List<Node> deadNodes = MainEnvironment.getNodeManager().findTimedOutNodes(MainEnvironment.getCurrentTimestamp());

        boolean hasNode = false;
        for (Node node : deadNodes) {
            if (node.getId() == this.nodeId)
                hasNode = true;
        }

        assertTrue(hasNode);
    }

    @Test
    public void testDestroyNode() throws Exception {
        registerNode();
        MainEnvironment.getNodeManager().destroyNode(this.nodeId);
        assertTrue(MainEnvironment.getNodeManager().getNode(this.gcmKey) == null);
    }
}