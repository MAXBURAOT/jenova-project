/**
 * 
 */
package com.angelis.tera.packet.parser.valuereader;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angelis.tera.packet.parser.datatree.IntValuePart;
import com.angelis.tera.packet.parser.datatree.ValuePart;

/**
 * @author Ulysses R. Ribeiro
 *
 */
public class BooleanReader implements Reader
{

    @Override
    public <T extends Enum<T>> T getEnum(ValuePart part)
    {
        return null;
    }

    @Override
    public boolean loadReaderFromXML(Node n)
    {
        return true;
    }

    @Override
    public String read(ValuePart part)
    {
        if (part instanceof IntValuePart)
        {
            return (((IntValuePart)part).getIntValue() == 1 ? "true" : "false");
        }
        return "";
    }

    @Override
    public JComponent readToComponent(ValuePart part)
    {
        return new JLabel(this.read(part));
    }

    @Override
    public boolean saveReaderToXML(Element element, Document doc)
    {
        return true;
    }

    @Override
    public boolean supportsEnum()
    {
        return false;
    }

}
