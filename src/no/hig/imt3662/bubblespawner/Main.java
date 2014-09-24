package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        // TODO read this from config + MySQL
        final long senderId = 437017129818L; // your GCM sender id
        final String password = "AIzaSyDPt6s8tyrzqW5nE3Q6GiEjWzAJ_ev-lnM";
        final String gcmServer = "gcm.googleapis.com";
        final int gcmPort = 5235;

        CommunicationHandler handler = new CommunicationHandler(gcmServer, gcmPort);

        try {
            handler.connect(senderId, password);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = new String();
        do {
            try {
                s = br.readLine();
            } catch (IOException e) {
                //
            }
        }
        while (s.equals("exit") == false);

    }
}
