package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.action.Action;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_SKILL_RESULTS extends TeraServerPacket {

    private final Creature caster;
    private final Action action;
    private final int damage;

    public SM_SKILL_RESULTS(final Creature caster, final Action action, final int damage) {
        this.caster = caster;
        this.action = action;
        this.damage = damage;
    }
    
    public SM_SKILL_RESULTS(final Player player, final Action action) {
        this(player, action, 0);
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, (short) (this.action.getVisualEffect() != null ? /*VisualEffect.Times.Count*/0 : 0)); //count of timesteps
        writeH(byteBuffer, 0); //shift

        writeUid(byteBuffer, this.caster); //attacker uniqueid
        writeUid(byteBuffer, this.action.getTarget()); //target unique id

        int model = 0;
        if (this.caster instanceof Player) {
            model = ((Player) this.caster).getRaceGenderClassValue();
        }
        else if (this.caster instanceof Creature) {
            model = caster.getId();
        }
        writeD(byteBuffer, model);
        
        writeD(byteBuffer, this.action.getSkillId() + 0x4000000);
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, this.action.getUid());

        writeB(byteBuffer, "C6010000"); // unknow

        writeD(byteBuffer, this.damage); //damage
        writeD(byteBuffer, this.action.getAttackType() != null ? this.action.getAttackType().value : 0); // 1 - Normal, 65537 - Critical
        
        writeB(byteBuffer, new byte[30]);
    }
}
