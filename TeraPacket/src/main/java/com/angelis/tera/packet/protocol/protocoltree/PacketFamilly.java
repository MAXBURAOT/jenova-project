package com.angelis.tera.packet.protocol.protocoltree;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import com.angelis.tera.packet.MainPacket;
import com.angelis.tera.packet.captor.Util;
import com.angelis.tera.packet.parser.PartType;
import com.angelis.tera.packet.parser.parttypes.IntPartType;

/**
 * This class is a container for {@link ProtocolNode ProtocolNodes} and also is
 * a ProtocolNode thus allowing an infinite depth tree structure for the
 * protocol definition.
 * 
 * @author Gilles Duboscq
 * 
 */
public class PacketFamilly extends ProtocolNode {
    private final Map<Integer, ProtocolNode> nodes;
    private PartType switchType;
    private PacketDirectionEnum direction;
    private int id;
    private String name;

    public enum PacketDirectionEnum {
        CLIENT, SERVER
    }

    public PacketFamilly() {
        this((String) null);
    }

    public PacketFamilly(final String name) {
        super();
        this.name = name;
        this.nodes = new FastMap<Integer, ProtocolNode>();
    }

    public PacketFamilly(final int id) {
        this(id, null);
    }

    public PacketFamilly(final int id, final String name) {
        super(id);
        this.id = id;
        this.name = name;
        this.nodes = new FastMap<Integer, ProtocolNode>();
    }

    public void addNode(final ProtocolNode node) {
        ProtocolNode old;
        if ((old = nodes.put(node.getID(), node)) != null) {
            MainPacket.getUserInterface().log("WARNING: More then one packet in familly '" + name + "' with id 0x" + Integer.toHexString(node.getID()) + " (" + old + " && " + node + ")");
        }
    }

    /**
     * @return Returns the switchType.
     */
    public PartType getSwitchType() {
        return switchType;
    }

    /**
     * @param type
     *            The switchType to set.
     */
    public void setSwitchType(final PartType type) {
        if (!(type instanceof IntPartType)) {
            MainPacket.getUserInterface().log("Warrning!: Bad switchType For a PacketFamilly!");
            return;
        }
        switchType = type;
    }

    /**
     * @return Returns the _direction.
     */
    public PacketDirectionEnum getDirection() {
        return direction;
    }

    /**
     * @param direction
     *            The _direction to set.
     */
    public void setDirection(final PacketDirectionEnum direction) {
        this.direction = direction;
    }

    /**
     * @return Returns the _nodes.
     */
    public Map<Integer, ProtocolNode> getNodes() {
        return nodes;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return (name != null ? name : Util.zeropad(Integer.toHexString(id), 2).toUpperCase()) + " familly";
    }

    public String getName() {
        return name;
    }

    /**
     * Used to remove a node in the tree (can be in sub-packetfamillies) Node
     * can be inexistant.
     * 
     * @param node
     */
    public void remove(final ProtocolNode node) {
        if (node == null) {
            return;
        }

        final Iterator<Entry<Integer, ProtocolNode>> i = nodes.entrySet().iterator();
        while (i.hasNext()) {
            final ProtocolNode testNode = i.next().getValue();
            if (node == testNode) {
                i.remove();
            }
            else if (testNode instanceof PacketFamilly) {
                ((PacketFamilly) testNode).remove(node);
            }
        }
    }

}