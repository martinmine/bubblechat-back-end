package no.hig.imt3662.bubblespawner.Serializing.Test;

import no.hig.imt3662.bubblespawner.Serializing.ServerInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerInfoTest {
    @Test
    public void test() {
        final int userId = 23;
        final int userCount = 43;
        final int radius = 9001;

        ServerInfo message = new ServerInfo(userCount, userId, radius);

        assertEquals(userId, message.getMessage().get("user_id"));
        assertEquals(userCount, message.getMessage().get("user_count"));
        assertEquals(radius, message.getMessage().get("radius"));
    }
}