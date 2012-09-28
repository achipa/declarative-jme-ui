/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.view;

import com.dec.parser.LWUITParser;
import com.dec.util.ViewManager;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.microedition.midlet.*;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author mahdi
 */
public class Test extends MIDlet {

    public void startApp() {
        Display.init(this);
        ViewManager.init(this);
        
        try {
            Resources res = Resources.open("/LWUITtheme.res");
            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
//            NetworkRunner.getInstance().start(getAppProperty("INIT_URL"));
            try {
                LWUITParser parser = new LWUITParser(new InputStreamReader(this.getClass().getResourceAsStream("/index.xml")));
                Form f = parser.parse();
                ViewManager.getInstance().show(f);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
            } catch (XmlPullParserException ex) {
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
