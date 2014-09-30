package no.hig.imt3662.bubblespawner.Serializing.Test;

import no.hig.imt3662.bubblespawner.Location;
import no.hig.imt3662.bubblespawner.Serializing.ChatMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatMessageTest {
    @Test
    public void test() {
        final int userId = 23;
        final String text = "aisdnian";
        final boolean broadcastLocation = true;
        final double lat = 23;
        final double lng = 34;
        final String username = "";

        ChatMessage message = new ChatMessage(userId, text, new Location(lat, lng), broadcastLocation, username);

        assertEquals(userId, message.getMessage().get("user_id"));
        assertEquals(text, message.getMessage().get("message_text"));
        assertEquals(broadcastLocation, message.getMessage().get("has_location"));
        assertEquals(lat, message.getMessage().get("latitude"));
        assertEquals(lng, message.getMessage().get("longitude"));
        assertEquals(username, message.getMessage().get("username"));
    }
}