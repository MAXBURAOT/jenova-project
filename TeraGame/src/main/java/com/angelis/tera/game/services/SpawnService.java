package com.angelis.tera.game.services;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.game.config.SpawnConfig;
import com.angelis.tera.game.domain.mapper.xml.CreatureSpawnMapper;
import com.angelis.tera.game.domain.mapper.xml.GatherMapper;
import com.angelis.tera.game.models.campfire.CampFire;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.drop.DropItem;
import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.template.TeraCreatureTemplate;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.tasks.campfire.CampFireDespawnTask;
import com.angelis.tera.game.tasks.creature.CreatureRespawnTask;
import com.angelis.tera.game.tasks.gather.GatherRespawnTask;
import com.angelis.tera.game.xml.entity.BaseStatEntity;
import com.angelis.tera.game.xml.entity.creatures.CreatureSpawnEntity;
import com.angelis.tera.game.xml.entity.creatures.CreatureSpawnsEntityHolder;
import com.angelis.tera.game.xml.entity.creatures.CreatureTemplateEntity;
import com.angelis.tera.game.xml.entity.creatures.CreatureTemplateEntityHolder;
import com.angelis.tera.game.xml.entity.gathers.GatherSpawnEntity;
import com.angelis.tera.game.xml.entity.gathers.GatherSpawnEntityHolder;

public class SpawnService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(SpawnService.class.getName());

    private final List<TeraCreature> creatureSpawns = Collections.synchronizedList(new FastList<TeraCreature>());
    private final List<Gather> gatherSpawns = Collections.synchronizedList(new FastList<Gather>());
    private final List<CampFire> campFireSpawns = Collections.synchronizedList(new FastList<CampFire>());
    private final List<DropItem> dropItemSpawns = Collections.synchronizedList(new FastList<DropItem>());

    private SpawnService() {
    }

    @Override
    public void onInit() {
        // CREATURE SPAWNS
        final Set<CreatureSpawnEntity> creatureEntities = XMLService.getInstance().getEntity(CreatureSpawnsEntityHolder.class).getCreatures();
        for (final CreatureSpawnEntity creatureEntity : creatureEntities) {
            this.creatureSpawns.addAll(MapperManager.getXMLListMapper(CreatureSpawnMapper.class).map(creatureEntity));
        }
        XMLService.getInstance().clearEntity(CreatureSpawnsEntityHolder.class);
        clearDuplicate(this.creatureSpawns);

        // CREATURE TEMPLATES
        final Set<CreatureTemplateEntity> creatureTemplateEntities = XMLService.getInstance().getEntity(CreatureTemplateEntityHolder.class).getCreatureTemplates();
        for (final TeraCreature creature : this.creatureSpawns) {
            boolean found = false;
            for (final CreatureTemplateEntity creatureTemplateEntity : creatureTemplateEntities) {
                final int npcFullId = creature.getId() + (creatureTemplateEntity.getHuntingZoneId() * 100000);
                if (npcFullId != creatureTemplateEntity.getNpcFullId()) {
                    continue;
                }

                final TeraCreatureTemplate teraCreatureTemplate = creature.getTemplate();
                teraCreatureTemplate.setModelId(creatureTemplateEntity.getModelId());
                teraCreatureTemplate.setHuntingZoneId(creatureTemplateEntity.getHuntingZoneId());
                teraCreatureTemplate.setCreatureTitle(creatureTemplateEntity.getCreatureTitle());

                final BaseStatEntity baseStats = creatureTemplateEntity.getBaseStat();
                creature.setLevel(baseStats.getLevel());

                BaseStatService.getInstance().affectCreatureBaseStats(creature, baseStats);
                BaseStatService.getInstance().affectCreatureCurrentStats(creature);
                BaseStatService.getInstance().affectCreatureBonusStats(creature);

                found = true;
                break;
            }

            if (!found) {
                log.error("Can't find template for creature with id " + creature.getId());
            }
        }
        XMLService.getInstance().clearEntity(CreatureTemplateEntityHolder.class);

        // GATHER SPAWNS
        final Set<GatherSpawnEntity> gatherEntities = XMLService.getInstance().getEntity(GatherSpawnEntityHolder.class).getGatherSpawns();
        for (final GatherSpawnEntity gatherEntity : gatherEntities) {
            gatherSpawns.addAll(MapperManager.getXMLListMapper(GatherMapper.class).map(gatherEntity));
        }
        XMLService.getInstance().clearEntity(GatherSpawnEntityHolder.class);
        clearDuplicate(this.gatherSpawns);

        log.info("SpawnService started");
    }

    @Override
    public void onDestroy() {
        this.creatureSpawns.clear();
        this.gatherSpawns.clear();
        this.campFireSpawns.clear();
        log.info("SpawnService stopped");
    }

    /********************
     * CREATURES
     ********************/
    public void spawnCreature(final TeraCreature teraCreature) {
        this.creatureSpawns.add(teraCreature);
        teraCreature.getKnownList().update(true);
    }

    public void despawnCreature(final TeraCreature teraCreature, final boolean mustRespawn) {
        this.creatureSpawns.remove(teraCreature);
        teraCreature.getKnownList().clear();
        teraCreature.getAggroList().clear();
        
        if (mustRespawn) {
            ThreadPoolService.getInstance().scheduleTask(new CreatureRespawnTask(teraCreature), SpawnConfig.SPAWN_CREATURE_RESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public TeraCreature getCreatureById(final int id) {
        for (final TeraCreature teraCreature : this.creatureSpawns) {
            if (teraCreature.getId() == id) {
                return teraCreature;
            }
        }
        return null;
    }
    
    public TeraCreature getCreatureByFullId(final int fullId) {
        for (final TeraCreature teraCreature : this.creatureSpawns) {
            if (teraCreature.getFullId() == fullId) {
                return teraCreature;
            }
        }
        return null;
    }

    public List<TeraCreature> getCreaturesByMapId(final int mapId) {
        final List<TeraCreature> creatures = new FastList<>();
        for (final TeraCreature teraCreature : this.creatureSpawns) {
            if (teraCreature.getWorldPosition().getMapId() == mapId && !teraCreature.getCreatureCurrentStats().isDead()) {
                creatures.add(teraCreature);
            }
        }

        return creatures;
    }

    /********************
     * GATHERS
     ********************/
    public void spawnGather(final Gather gather) {
        this.gatherSpawns.add(gather);
        gather.getKnownList().update(true);
    }

    public void despawnGather(final Gather gather, final boolean mustRespawn) {
        this.gatherSpawns.remove(gather);
        gather.getKnownList().clear();

        if (mustRespawn) {
            ThreadPoolService.getInstance().scheduleTask(new GatherRespawnTask(gather), SpawnConfig.SPAWN_GATHER_RESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public List<Gather> getGathersByMapId(final int mapId) {
        final List<Gather> gathers = new FastList<>();
        for (final Gather gather : this.gatherSpawns) {
            if (gather.getWorldPosition().getMapId() == mapId && gather.getRemainingGather() != 0) {
                gathers.add(gather);
            }
        }
        return gathers;
    }

    /********************
     * CAMPFIRE
     ********************/
    public void spawnCampFire(final CampFire campFire, final boolean mustDespawn) {
        this.campFireSpawns.add(campFire);
        campFire.getKnownList().update(true);

        if (mustDespawn) {
            ThreadPoolService.getInstance().scheduleTask(new CampFireDespawnTask(campFire), SpawnConfig.SPAWN_CAMPFIRE_DESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public void spawnCampFire(final CampFire campFire) {
        this.spawnCampFire(campFire, true);
    }

    public void despawnCampFire(final CampFire campFire) {
        this.campFireSpawns.remove(campFire);
        campFire.getKnownList().clear();
    }

    public List<CampFire> getCampFiresByMapId(final int mapId) {
        final List<CampFire> campFires = new FastList<>();
        for (final CampFire campFire : this.campFireSpawns) {
            if (campFire.getWorldPosition().getMapId() == mapId) {
                campFires.add(campFire);
            }
        }
        return campFires;
    }

    public CampFire getNearestCampFire(final WorldPosition worldPosition) {
        CampFire nearestCampFire = null;
        double nearestDistance = 0.0;
        for (final CampFire campFire : this.getCampFiresByMapId(worldPosition.getMapId())) {
            if (nearestCampFire == null || campFire.getWorldPosition().distanceTo(worldPosition) < nearestDistance) {
                nearestCampFire = campFire;
                nearestDistance = nearestCampFire.getWorldPosition().distanceTo(worldPosition);
            }
        }
        return nearestCampFire;
    }

    /********************
     * DROPITEM
     ********************/
    public void spawnDropItem(final DropItem dropItem) {
        this.dropItemSpawns.add(dropItem);
        dropItem.getKnownList().update(true);
    }

    public void despawnDropItem(final DropItem dropItem) {
        this.dropItemSpawns.remove(dropItem);
        dropItem.getKnownList().clear();
    }

    public List<DropItem> getDropItemsByMapId(final int mapId) {
        final List<DropItem> dropItems = new FastList<>();
        for (final DropItem dropItem : this.dropItemSpawns) {
            if (dropItem.getWorldPosition().getMapId() == mapId) {
                dropItems.add(dropItem);
            }
        }
        return dropItems;
    }

    private synchronized final void clearDuplicate(final List<? extends VisibleTeraObject> collection) {
        final Set<WorldPosition> wps = new FastSet<>();
        final Iterator<? extends VisibleTeraObject> itr = collection.iterator();
        while (itr.hasNext()) {
            final VisibleTeraObject visibleTeraObject = itr.next();
            final WorldPosition worldPosition = visibleTeraObject.getWorldPosition();
            if (wps.contains(worldPosition)) {
                log.error("Spawn entity duplication : " + visibleTeraObject.getId());
                itr.remove();
                continue;
            }

            wps.add(worldPosition);
        }
    }

    /** SINGLETON */
    public static SpawnService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SpawnService instance = new SpawnService();
    }
}
