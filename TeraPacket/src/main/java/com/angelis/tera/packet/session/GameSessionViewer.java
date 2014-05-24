/**
 * 
 */
package com.angelis.tera.packet.session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ulysses R. Ribeiro
 * 
 */
public class GameSessionViewer {
    private final List<DataPacket> _logPackets;
    private final Session _session;

    public GameSessionViewer(final Session s) {
        _session = s;
        _logPackets = new ArrayList<DataPacket>(s.getPackets().size());
        _logPackets.addAll(s.getPackets());
    }

    public List<DataPacket> getAllPackets() {
        return _logPackets;
    }

    public DataPacket getPacket(final int index) {
        return _logPackets.get(index);
    }

    public Session getSession() {
        return _session;
    }
}
