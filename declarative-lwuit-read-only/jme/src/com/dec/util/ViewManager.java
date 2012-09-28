/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dec.util;

import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import java.util.Stack;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author mhijazi
 */
public class ViewManager {
    public static ViewManager getInstance() {
        if(instance==null)  instance = new ViewManager();

        return instance;
    }

    protected ViewManager() {
        screensStack = new Stack();

    }

    public void show(final Form form) {
        Display.getInstance().callSerially(new Runnable() {

            public void run() {
                form.show();
                screensStack.push(form);
            }
        });

    }

    public void back() {
        Display.getInstance().callSerially(new Runnable() {

            public void run() {
                screensStack.pop();
                Form form = (Form) screensStack.peek();
                form.showBack();

            }
        });

    }

    public static void init(MIDlet midlet) {
        ViewManager.midlet = midlet;
        
    }

    public void exit() {
        midlet.notifyDestroyed();

    }
    
    private static ViewManager instance;
    Stack screensStack;
    private static MIDlet midlet;

}
