package no.hig.imt3662.bubblespawner.Serializing.Test;

import no.hig.imt3662.bubblespawner.Serializing.NodeLeft;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeLeftTest {
    private static final int nodeId = 2;
    private NodeLeft message;

    public NodeLeftTest() {
        this.message = new NodeLeft(nodeId);
    }

    @Test
    public void testMessage() {
        assertEquals(nodeId, message.getMessage().get("user_id"));
    }
}