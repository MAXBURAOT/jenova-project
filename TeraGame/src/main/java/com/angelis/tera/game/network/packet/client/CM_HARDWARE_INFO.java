package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_SYSTEM_INFO;
import com.angelis.tera.game.services.AccountService;

public class CM_HARDWARE_INFO extends TeraClientPacket {

    private String osName;
    private String cpuName;
    private String gpuName;
    
    public CM_HARDWARE_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        final short osNameShift = readH();
        final short cpuNameShift = readH();
        final short gpuNameShift = readH();

        readB(osNameShift - this.position());
        this.osName = readS();
        
        readB(cpuNameShift - this.position());
        this.cpuName = readS();
        
        readB(gpuNameShift - this.position());
        this.gpuName = readS();
    }

    @Override
    protected void runImpl() {
        AccountService.getInstance().registerHardwareInfo(this.osName, this.cpuName, this.gpuName);
        this.getConnection().sendPacket(new SM_SYSTEM_INFO());
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("C0CC0100080008000000B2010000FFFFFF7F00000000"));
    }
}
