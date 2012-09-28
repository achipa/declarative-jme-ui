/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dec.parser;

import com.dec.controllers.CommandsHandler;
import com.dec.controllers.LinkHandler;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author mahdi
 */
public class LWUITParser {

    public LWUITParser(InputStreamReader is) throws XmlPullParserException, IOException {
        parser = new KXmlParser();
        parser.setInput(is);
        
    }

    public Form parse() throws IOException, XmlPullParserException {
        Document doc = new Document();
        doc.parse(parser);
        Element root = doc.getRootElement();
        return parseForm(root);

    }

    private static Form parseForm(Element formElement) {
        Form form = new Form();

        int attrSize = formElement.getAttributeCount();
        for (int i = 0; i < attrSize; i++) {
            String attrName = formElement.getAttributeName(i);
            String attrVal = formElement.getAttributeValue(i);
            applyFormAttribute(attrName, attrVal, form);

        }

        int childesSize = formElement.getChildCount();
        Vector components = new Vector();
        for (int i = 0; i < childesSize; i++) {
            if (!formElement.isText(i)) {
                Element child = (Element) formElement.getChild(i);
                if (!parseLayout(child, form)) {
                    if(!parseMenu(child, form)) {
                        Component cmp = parseComponent(child);
                        components.addElement(cmp);
                    }

                }

            }

        }

        int length = components.size();
        for (int i = 0; i < length; i++) {
            Component cmp = (Component)components.elementAt(i);
            if (cmp.getClientProperty("layoutconstraint") != null) {
                form.addComponent((String) cmp.getClientProperty("layoutconstraint"), cmp);
            } else {
                form.addComponent(cmp);

            }

        }

        return form;

    }

    private static boolean parseMenu(Element el, Form form) {
        String name = el.getName();
        if ("menu".equals(name)) {
            int commandsSize = el.getChildCount();
            for (int i = 0; i < commandsSize; i++) {
                if (!el.isText(i)) {
                    Element child = (Element) el.getChild(i);
                    Command cmd = parseCommand(child);
                    form.addCommand(cmd);
                    
                }

            }

            if(commandsHandler==null)
                commandsHandler = new CommandsHandler();

            form.addCommandListener(commandsHandler);
            return true;
        }
        return false;
    }

    private static Command parseCommand(Element el) {
        String text = el.getAttributeValue(null, "text");
        int id = CommandsHandler.getIdByType(el.getAttributeValue(null, "type"));
        Command cmd = new Command(text, id);
        return cmd;
    }

    private static boolean parseLayout(Element el, Form form) {
        String name = el.getName();
        if ("boxlayout".equals(name)) {
            String axis = el.getAttributeValue(null, "axis");
            if(axis ==null ) {
                throw new IllegalArgumentException("BoxLayout should have axis attribute");
            }
            form.setLayout(new BoxLayout(axis.equals("y") ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS));
            return true;
        } else if ("borderlayout".equals(name)) {
            form.setLayout(new BorderLayout());
            return true;
        }

        return false;
    }

    private static boolean isLayout(Element el) {
        String name = el.getName();
        return ("boxlayout".equals(name)
                || "flowlayout".equals(name)
                || "gridlayout".equals(name)
                || "borderlayout".equals(name));
    }

    private static Component parseComponent(Element el) {
        String name = el.getName();
        if ("label".equals(name)) {
            return parseLabelComponent(el);

        } else if ("button".equals(name)) {
            return parseButtonComponent(el);

        } else if("link".equals(name)) {
            return parseLinkComponent(el);

        }

        return null;

    }

    private static Button parseLinkComponent(Element el) {
        Button btn = new Button();
        int attrSize = el.getAttributeCount();
        for (int i = 0; i < attrSize; i++) {
            String attrName = el.getAttributeName(i);
            String attrVal = el.getAttributeValue(i);
            boolean applied = applyLabelAttributes(attrName, attrVal, btn);
            applied = applyLinkAttributes(attrName, attrVal, btn);
            if(!applied)
                applyComponentAttributes(attrName, attrVal, btn);

        }

        if(linkHandler==null)
            linkHandler = new LinkHandler();
        
        btn.addActionListener(linkHandler);
        return btn;

    }

    private static Button parseButtonComponent(Element el) {
        Button btn = new Button();
        int attrSize = el.getAttributeCount();
        for (int i = 0; i < attrSize; i++) {
            String attrName = el.getAttributeName(i);
            String attrVal = el.getAttributeValue(i);
            boolean applied = applyLabelAttributes(attrName, attrVal, btn);
            if(!applied)
                applyComponentAttributes(attrName, attrVal, btn);

        }

        return btn;

    }

    private static Component parseLabelComponent(Element el) {
        Label lbl = new Label();
        int attrSize = el.getAttributeCount();
        for (int i = 0; i < attrSize; i++) {
            String attrName = el.getAttributeName(i);
            String attrVal = el.getAttributeValue(i);
            boolean applied = applyLabelAttributes(attrName, attrVal, lbl);
            if(!applied) {
                applyComponentAttributes(attrName, attrVal, lbl);

            }

        }

        return lbl;

    }

    private static void applyComponentAttributes(String attr, String val, Component cmp) {
        if ("layoutconstraint".equals(attr)) {
            String constraint = "";
            if ("north".equals(val)) {
                constraint = BorderLayout.NORTH;
            } else if ("center".equals(val)) {
                constraint = BorderLayout.CENTER;
            } else if ("east".equals(val)) {
                constraint = BorderLayout.EAST;
            } else if ("west".equals(val)) {
                constraint = BorderLayout.WEST;
            } else if("south".equals(val)) {
                constraint = BorderLayout.SOUTH;
            } else {
                throw new IllegalArgumentException("Invalid layout constraint: " + val);
            }

            cmp.putClientProperty("layoutconstraint", constraint);

        }

    }

    private static boolean applyLinkAttributes(String attr, String val, Label lbl) {
        if ("href".equals(attr)) {
            lbl.putClientProperty("href", val);
            return true;
        }
        return false;

    }

    private static boolean applyLabelAttributes(String attr, String val, Label lbl) {
        if ("text".equals(attr)) {
            lbl.setText(val);
            return true;
        }

        return false;

    }

    private static void applyFormAttribute(String attr, String val, Form form) {
        if (attr.equals("title")) {
            form.setTitle(val);

        }

    }

    KXmlParser parser;
    static LinkHandler linkHandler;
    static CommandsHandler commandsHandler;

}
