package com.angelis.tera.packet.protocol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.angelis.tera.packet.captor.Util;
import com.angelis.tera.packet.parser.PartType;

/**
 * 
 * @author Gilles Duboscq
 * 
 */
public class PacketId {
    public static class IdPart {
        private final PartType type;
        private final long value;

        public IdPart(final PartType type, final long value) {
            this.type = type;
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public PartType getType() {
            return type;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IdPart)) {
                return false;
            }
            final IdPart other = (IdPart) obj;
            return (this.getType() == other.getType() && this.getValue() == other.getValue());
        }
    }

    private final List<IdPart> _parts;
    private String _toStr;

    public PacketId() {
        _parts = new ArrayList<IdPart>(2);
    }

    public PacketId(final PartType[] types, final long[] values) {
        if (types.length != types.length) {
            throw new IllegalArgumentException("Invalid arrays, both must be the same length");
        }
        _parts = new ArrayList<IdPart>(types.length);
        int i = 0;
        for (final long val : values) {
            this.add(val, types[i]);
            i++;
        }
    }

    public void add(final long value, final PartType type) {
        _parts.add(new IdPart(type, value));
        _toStr = null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PacketId)) {
            return false;
        }
        final PacketId other = (PacketId) obj;
        if (this.size() != other.size()) {
            return false;
        }
        final int size = this.size();
        for (int i = 0; i < size; i++) {
            final IdPart id1 = this.getParts().get(i);
            if (id1 == null || !id1.equals(other.getParts().get(i))) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return _parts.size();
    }

    public List<IdPart> getParts() {
        return _parts;
    }

    @Override
    public String toString() {
        if (_toStr == null) {
            final StringBuilder sb = new StringBuilder();
            final Iterator<IdPart> it = this.getParts().iterator();
            for (IdPart part = it.next(); it.hasNext(); part = it.next()) {
                if (part == null) {
                    break;
                }
                final String str = Long.toHexString(part.getValue());
                sb.append(Util.zeropad(str, str.length() + str.length() % 2));
                if (it.hasNext()) {
                    sb.append(":");
                }
            }
            _toStr = sb.toString().toUpperCase();
        }
        return _toStr;
    }
}