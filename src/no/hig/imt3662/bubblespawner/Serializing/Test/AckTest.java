package no.hig.imt3662.bubblespawner.Serializing.Test;

import no.hig.imt3662.bubblespawner.Serializing.Ack;
import org.junit.Test;

import static org.junit.Assert.*;

public class AckTest {
    private Ack message;
    private static final String messageId = "124234";

    public AckTest() {
        this.message = new Ack(messageId);
    }

    @Test
    public void testSerializeToJson() throws Exception {
        final String receiver = "test";

        message.serializeToJson(receiver, null);
        assertEquals(message.getMessage().get("message_id"), messageId);
        assertEquals(message.getMessage().get("to"), receiver);
    }
}