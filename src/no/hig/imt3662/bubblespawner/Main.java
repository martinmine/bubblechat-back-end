package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        MainEnvironment.initialize();
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
