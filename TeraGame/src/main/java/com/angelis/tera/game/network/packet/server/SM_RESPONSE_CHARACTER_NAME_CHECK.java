package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.enums.CheckNameTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.enums.CheckCharacterNameResponse;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_RESPONSE_CHARACTER_NAME_CHECK extends TeraServerPacket {

    private final CheckNameTypeEnum type;
    private final CheckCharacterNameResponse response;
    private final String name;
    
    public SM_RESPONSE_CHARACTER_NAME_CHECK(final CheckNameTypeEnum type, final CheckCharacterNameResponse response, final String name) {
        this.type = type;
        this.response = response;
        this.name = name;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "01000800080000001600");
        writeD(byteBuffer, this.type.getCode());
        writeD(byteBuffer, this.response.getValue());
        writeS(byteBuffer, this.name);
    }
}
