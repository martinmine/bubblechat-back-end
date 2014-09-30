package no.hig.imt3662.bubblespawner.Serializing.Test;

import no.hig.imt3662.bubblespawner.Serializing.NodeEntered;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeEnteredTest {
    @Test
    public void test() {
        final int nodeId = 23;
        NodeEntered message = new NodeEntered(nodeId);
        assertEquals(nodeId, message.getMessage().get("user_id"));
    }

}