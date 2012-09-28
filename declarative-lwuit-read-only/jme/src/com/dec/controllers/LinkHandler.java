/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dec.controllers;

import com.dec.parser.LWUITParser;
import com.dec.util.ViewManager;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author mhijazi
 */
public class LinkHandler implements ActionListener {

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        Label lbl = (Label) src;
        String href = (String) lbl.getClientProperty("href");
        handleLink(href);

    }

    private void handleLink(String href) {
        if (href.startsWith("file://")) {
            String fileName = href.substring(7);
            loadForm(fileName);
        }

    }

    private void loadForm(String fileName) {
        try {
            LWUITParser parser = new LWUITParser(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));
            Form f = parser.parse();
            ViewManager.getInstance().show(f);
            
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
        } catch (XmlPullParserException ex) {
        }

    }
}
