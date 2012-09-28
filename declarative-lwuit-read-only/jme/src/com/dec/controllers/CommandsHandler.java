/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dec.controllers;

import com.dec.util.ViewManager;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

/**
 *
 * @author mhijazi
 */
public class CommandsHandler implements ActionListener {

    public void actionPerformed(ActionEvent evt) {
        Command cmd = (Command) evt.getSource();
        int id = cmd.getId();
        switch(cmd.getId()) {
            case CommandsHandler.ID_BACK:
                ViewManager.getInstance().back();
                break;

            case CommandsHandler.ID_EXIT:
                ViewManager.getInstance().exit();
                break;
                
        }
    }


    public static int getIdByType(String type) {
        if("back".equals(type))
            return ID_BACK;
        else if("exit".equals(type))
            return ID_EXIT;
        
        return -1;
    }

    private static final String TYPE_BACK = "back";
    private static final String TYPE_EXIT = "exit";
    public static final int ID_BACK     = 0;
    public static final int ID_EXIT     = 1;
}
