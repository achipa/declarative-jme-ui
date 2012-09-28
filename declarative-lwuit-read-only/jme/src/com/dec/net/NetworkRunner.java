/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dec.net;

import com.dec.parser.LWUITParser;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.io.ConnectionRequest;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author mahdi
 */
public class NetworkRunner implements ActionListener {

    private static NetworkRunner instance;

    protected NetworkRunner() {
    }

    public static NetworkRunner getInstance() {
        if (instance == null) {
            instance = new NetworkRunner();
        }

        return instance;
    }

    public void start(String url) {
        NetworkManager.getInstance().start();
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);
        req.addResponseListener(this);
        NetworkManager.getInstance().addToQueue(req);

    }

    public void actionPerformed(ActionEvent evt) {
        NetworkEvent nev = (NetworkEvent) evt;
        byte[] data = (byte[]) nev.getMetaData();
        try {
            LWUITParser parser = new LWUITParser(new InputStreamReader(new ByteArrayInputStream(data)));
            Form f = parser.parse();
            f.show();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
        } catch(XmlPullParserException ex) {

        }

    }
}
