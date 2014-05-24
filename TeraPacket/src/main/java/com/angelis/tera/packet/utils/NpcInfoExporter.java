package com.angelis.tera.packet.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.packet.MainPacket;
import com.angelis.tera.packet.parser.datatree.ValuePart;
import com.angelis.tera.packet.session.DataPacket;

/**
 * @author ATracer
 */
public class NpcInfoExporter {
    private final List<DataPacket> packets;
    private final String sessionName;
    private final FastList<NpcInfo> npcInfoList = new FastList<NpcInfo>();

    public NpcInfoExporter(final List<DataPacket> packets, final String sessionName) {
        super();
        this.packets = packets;
        this.sessionName = sessionName;
    }

    public void parse() {
        final String fileName = "npcinfo_" + sessionName + ".txt";

        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

            for (final DataPacket packet : packets) {
                final String name = packet.getName();
                if ("SM_NPC_INFO".equals(name)) {
                    final List<ValuePart> valuePartList = packet.getValuePartList();
                    final NpcInfo npc = new NpcInfo();

                    for (final ValuePart valuePart : valuePartList) {
                        final String partName = valuePart.getModelPart().getName();
                        if ("npcId".equals(partName)) {
                            npc.npcId = Integer.parseInt(valuePart.readValue());
                        }
                        else if ("titleId".equals(partName)) {
                            npc.titleId = Integer.parseInt(valuePart.readValue());
                        }
                        else if ("nameId".equals(partName)) {
                            npc.nameId = Integer.parseInt(valuePart.readValue());
                        }
                        else if ("npcMaxHP".equals(partName)) {
                            npc.npcMaxHp = Integer.parseInt(valuePart.readValue());
                        }
                        else if ("npclevel".equals(partName)) {
                            npc.npcLevel = Byte.parseByte(valuePart.readValue());
                        }
                        else if ("npcTemplateHeight".equals(partName)) {
                            npc.npcTemplateHeight = Float.parseFloat(valuePart.readValue());
                        }
                    }
                    npcInfoList.add(npc);
                }

            }

            out.write("npcId\ttitleId\tnameId\tnpcMaxHp\tnpcLevel\tnpcTemplateHeight\n");

            for (final NpcInfo npc : npcInfoList) {
                final StringBuilder sb = new StringBuilder();
                sb.append(npc.npcId);
                sb.append("\t");
                sb.append(npc.titleId);
                sb.append("\t");
                sb.append(npc.nameId);
                sb.append("\t");
                sb.append(npc.npcMaxHp);
                sb.append("\t");
                sb.append(npc.npcLevel);
                sb.append("\t");
                sb.append(npc.npcTemplateHeight);
                sb.append("\t");
                sb.append("\n");
                out.write(sb.toString());
            }

            out.close();
        }
        catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MainPacket.getUserInterface().log("The npc infos have been written");
    }

    class NpcInfo {
        int npcId;
        int titleId;
        int nameId;
        int npcMaxHp;
        byte npcLevel;
        float npcTemplateHeight;
    }
}
