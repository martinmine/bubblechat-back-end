package no.hig.imt3662.bubblespawner.MessageHandling;

import java.util.HashMap;

/**
 * Created by Martin on 14/09/24.
 */
public interface MessageHandler {
    void invoke(HashMap<String, Object> jsonRequest);
}
