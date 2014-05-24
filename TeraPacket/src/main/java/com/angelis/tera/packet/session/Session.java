package com.angelis.tera.packet.session;

import java.io.IOException;
import java.net.Inet4Address;

import javolution.util.FastList;

import com.angelis.tera.packet.MainPacket;
import com.angelis.tera.packet.crypt.NullCrypter;
import com.angelis.tera.packet.crypt.ProtocolCrypter;
import com.angelis.tera.packet.logwriters.PSLogWriter;
import com.angelis.tera.packet.protocol.Protocol;
import com.angelis.tera.packet.protocol.protocoltree.PacketFamilly.PacketDirectionEnum;

/**
 * @author Ulysses R. Ribeiro
 * @author Gilles Duboscq
 * 
 */
public class Session {

    private final long _sessionId;

    private ProtocolCrypter _crypt;

    private final Protocol _protocol;

    private final String _sessionName;

    private FastList<DataPacket> _packets;

    private Runnable _newPacketNotification;

    private boolean _shown;

    private Inet4Address _clientIP;

    private Inet4Address _serverIP;

    private String _serverType;

    private long _analyserBits;

    private String _comments;

    private final boolean _decrypt;

    private final boolean _parse;

    public Session(final long sessionId, final Protocol protocol, final String prefix, final boolean crypted, final boolean decrypt) {
        _sessionName = prefix + sessionId;
        _packets = new FastList<DataPacket>();
        _sessionId = sessionId;
        _protocol = protocol;
        _decrypt = decrypt || !crypted;
        _parse = MainPacket.PARSER_ACTIVE;
        init(crypted);
    }

    public Session(final long sessionId, final Protocol protocol, final String prefix, final boolean crypted) {
        this(sessionId, protocol, prefix, crypted, true);
    }

    private void init(final boolean crypted) {
        if (crypted && _decrypt) {
            try {
                final Class<?> clazz = Class.forName("com.angelis.tera.packet.crypt." + _protocol.getEncryption() + "Crypter");
                _crypt = (ProtocolCrypter) clazz.newInstance();
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        else {
            _crypt = new NullCrypter();
        }
        _crypt.setProtocol(_protocol);
    }

    /**
     * <li>Decrypts the raw data received</li> <li>Adds a DataPacket to the
     * packetList, instantiated from the decrypted data</li> <li>Runs the packet
     * notification, if any</li>
     * 
     * @param data
     *            the raw data WITHOUT header
     * @param fromServer
     *            should be true if packet came from server, false otherwise
     * @param time
     *            (miliseconds)
     */
    public void addPacket(final byte[] data, final boolean fromServer, final long time) {
        final PacketDirectionEnum direction = (fromServer ? PacketDirectionEnum.SERVER : PacketDirectionEnum.CLIENT);

        if (_decrypt) {
            _crypt.decrypt(data, direction);
        }

        _packets.add(new DataPacket(data, direction, time, _protocol, _parse));

        if (_newPacketNotification != null) {
            _newPacketNotification.run();
        }
    }

    public long getSessionId() {
        return _sessionId;
    }

    public String getSessionName() {
        return _sessionName;
    }

    public FastList<DataPacket> getPackets() {
        return _packets;
    }

    public void setPackets(final FastList<DataPacket> packets) {
        _packets = packets;
    }

    public void setNewPacketNotification(final Runnable r) {
        _newPacketNotification = r;
    }

    public void setShown(final boolean b) {
        _shown = b;
    }

    public boolean isShown() {
        return _shown;
    }

    public void setClientIp(final Inet4Address ip) {
        _clientIP = ip;
    }

    public Inet4Address getClientIp() {
        return _clientIP;
    }

    public void setServerIp(final Inet4Address ip) {
        _serverIP = ip;
    }

    public Inet4Address getServerIp() {
        return _serverIP;
    }

    public Protocol getProtocol() {
        return _protocol;
    }

    public String getServerType() {
        return _serverType;
    }

    public void setServerType(final String type) {
        _serverType = type;
    }

    public long getAnalyserBitSet() {
        return _analyserBits;
    }

    public void setAnalyserBitSet(final long bits) {
        _analyserBits = bits;
    }

    public String getComments() {
        return _comments;
    }

    public void setComments(final String com) {
        _comments = com;
    }

    public void saveSession() {
        try {
            final PSLogWriter writer = new PSLogWriter(this);
            writer.writeLog();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadPackets() {
        _packets.clear();
    }

    public boolean isDecrypted() {
        return _decrypt;
    }
}
