package com.angelis.tera.packet.parser.valuereader;
/*
 * This file is part of aion-unique <aion-unique.com>.
 *
 *     Aion-unique is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Aion-unique is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angelis.tera.packet.MainPacket;
import com.angelis.tera.packet.gui.Main;
import com.angelis.tera.packet.parser.datatree.StringValuePart;
import com.angelis.tera.packet.parser.datatree.ValuePart;

/**
 * @author Nemiroff
 */

public class TextReader implements Reader {
    @Override
    public <T extends Enum<T>> T getEnum(ValuePart part) {
        return null;
    }

    @Override
    public boolean loadReaderFromXML(Node n) {
        return true;
    }

    @Override
    public String read(ValuePart part) {
        if (part instanceof StringValuePart) {
            return ((StringValuePart) part).getStringValue();
        }
        MainPacket.getUserInterface().log("Text ValueReader set on a non String part: " + part.getModelPart().getName());
        return "";
    }

    @Override
    public JComponent readToComponent(ValuePart part) {
        JButton view = new JButton("View");
        view.addActionListener(new ButtonActionListener(this.read(part)));
        view.setActionCommand("clicked");
        return view;
    }


    @Override
    public boolean saveReaderToXML(Element element, Document doc) {
        return true;
    }

    @Override
    public boolean supportsEnum() {
        return false;
    }

    class ButtonActionListener implements ActionListener {
        private String _xml;

        public ButtonActionListener(String html) {
            _xml = html;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dlg = new JDialog(((Main) MainPacket.getUserInterface()).getMainFrame(), "Text");
            dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dlg.setSize(350, 400);
            dlg.setLocationRelativeTo(((Main) MainPacket.getUserInterface()).getMainFrame());

            JEditorPane sourceDisplay = new JEditorPane();
            sourceDisplay.setEditable(false);
            sourceDisplay.setContentType("text/plain");
            sourceDisplay.setText(_xml);

            dlg.add(new JScrollPane(sourceDisplay));
            dlg.setVisible(true);
        }
    }
}
