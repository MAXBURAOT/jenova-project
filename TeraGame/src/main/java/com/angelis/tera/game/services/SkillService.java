package com.angelis.tera.game.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.utils.Pair;
import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.mount.Mount;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.SkillList;
import com.angelis.tera.game.models.player.enums.PlayerClassEnum;
import com.angelis.tera.game.models.player.enums.RaceEnum;
import com.angelis.tera.game.models.skill.Skill;
import com.angelis.tera.game.models.skill.SkillArgs;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_ABNORMALITY_BEGIN;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_INSTANCE_ARROW;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_SKILL_LIST;
import com.angelis.tera.game.network.packet.server.SM_SKILL_START_COOLTIME;
import com.angelis.tera.game.tasks.CreatureEndAbnormality;
import com.angelis.tera.game.xml.entity.SkillEntity;
import com.angelis.tera.game.xml.entity.players.PlayerClassSkillsEntity;
import com.angelis.tera.game.xml.entity.players.PlayerClassSkillsEntityHolder;
import com.angelis.tera.game.xml.entity.players.PlayerRaceSkillsEntity;
import com.angelis.tera.game.xml.entity.players.PlayerRaceSkillsEntityHolder;

public class SkillService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(SkillService.class.getName());

    private final Map<PlayerClassEnum, List<Skill>> classSkills = new FastMap<>();
    private final Map<RaceEnum, List<Skill>> raceSkills = new FastMap<>();

    @Override
    public void onInit() {
        for (final PlayerClassSkillsEntity playerClassSkillsEntity : XMLService.getInstance().getEntity(PlayerClassSkillsEntityHolder.class).getPlayerClassSkills()) {
            final List<Skill> skills = new FastList<>();
            for (final SkillEntity skillEntity : playerClassSkillsEntity.getSkills()) {
                final Skill skill = new Skill();
                skill.setSkillId(skillEntity.getId());
                skill.setRequiredLevel(skillEntity.getRequiredLevel());

                skills.add(skill);
            }

            classSkills.put(playerClassSkillsEntity.getTargetClass(), skills);
        }
        XMLService.getInstance().clearEntity(PlayerClassSkillsEntityHolder.class);

        for (final PlayerRaceSkillsEntity playerRaceSkillsEntity : XMLService.getInstance().getEntity(PlayerRaceSkillsEntityHolder.class).getPlayerRaceSkills()) {
            final List<Skill> skills = new FastList<>();
            for (final SkillEntity skillEntity : playerRaceSkillsEntity.getSkills()) {
                final Skill skill = new Skill();
                skill.setSkillId(skillEntity.getId());
                skill.setRequiredLevel(skillEntity.getRequiredLevel());

                skills.add(skill);
            }

            raceSkills.put(playerRaceSkillsEntity.getTargetRace(), skills);
        }
        XMLService.getInstance().clearEntity(PlayerRaceSkillsEntityHolder.class);

        log.info("SkillService started");
    }

    @Override
    public void onDestroy() {
        classSkills.clear();
        raceSkills.clear();

        log.info("SkillService started");
    }

    public void onPlayerSkillUse(final Player player, final SkillArgs skillArgs) {
        final Mount mount = MountService.getInstance().getMountBySkillId(skillArgs.getSkillId());
        if (mount != null) {
            MountService.getInstance().processMount(player, mount);
            this.sendSkillCoolTime(player, skillArgs.getSkillId(), 10);
            return;
        }

        BattleService.getInstance().onPlayerAttack(player, skillArgs);
    }

    public void onPlayerSkillUse(final Player player, final int skillId) {

    }

    public void onPlayerSkillInstanceUse(final Player player, final SkillArgs skillArgs) {
        player.getConnection().sendPacket(new SM_CREATURE_INSTANCE_ARROW(player, skillArgs.getEndPosition(), skillArgs.getSkillId()));
        BattleService.getInstance().onPlayerAttack(player, skillArgs);
        // TODO send new mp
    }

    public void onPlayerSkillCancel(final Player player, final int skillId, final int type) {
        BattleService.getInstance().onPlayerAttackRelease(player, skillId, type);
    }

    public void onPlayerCreate(final Player player) {
        final SkillList skillList = new SkillList();
        skillList.addSkills(classSkills.get(player.getPlayerClass()));
        skillList.addSkills(raceSkills.get(player.getRace()));

        player.setSkillList(skillList);
    }

    public void learnSkill(final Player player, final Integer skillId, final Integer level) {
        final Skill skill = new Skill();
        skill.setSkillId(skillId);
        skill.setRequiredLevel(1);
        player.getSkillList().learnSkill(skill);

        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(SystemMessages.YOU_HAVE_LEARNED_SKILL(String.valueOf(skillId), "112"));
        connection.sendPacket(new SM_PLAYER_SKILL_LIST(player));
    }

    public void unlearnSkill(final Player player, final Integer skillId) {
        final Skill skill = player.getSkillList().getSkillById(skillId);
        if (skill == null) {
            return;
        }

        player.getSkillList().unlearnSkill(skill);
    }

    public void applyAbnormality(final Creature creature, final Abnormality abnormality) {
        VisibleService.getInstance().sendPacketForVisible(creature, new SM_ABNORMALITY_BEGIN(abnormality), false);
        ThreadPoolService.getInstance().scheduleTask(new CreatureEndAbnormality(new Pair<Creature, Abnormality>(creature, abnormality)), 10, TimeUnit.SECONDS);
    }

    private void sendSkillCoolTime(final Player player, final int skillId, final int cooltime) {
        player.getConnection().sendPacket(new SM_SKILL_START_COOLTIME(skillId, cooltime));
    }

    /** SINGLETON */
    public static SkillService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SkillService instance = new SkillService();
    }
}
