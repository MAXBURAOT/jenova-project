package com.angelis.tera.game.services;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.angelis.tera.game.database.entity.AccountEntity;
import com.angelis.tera.game.database.entity.CraftEntity;
import com.angelis.tera.game.database.entity.GatherEntity;
import com.angelis.tera.game.database.entity.GuildEntity;
import com.angelis.tera.game.database.entity.PlayerEntity;
import com.angelis.tera.game.database.entity.QuestEntity;
import com.angelis.tera.game.database.entity.SkillEntity;
import com.angelis.tera.game.database.entity.StorageEntity;
import com.angelis.tera.game.database.entity.StorageItemEntity;

public class DatabaseService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DatabaseService.class.getName());

    private AnnotationConfiguration annotationConfiguration;
    private SessionFactory sessionFactory;

    private DatabaseService() {
        try {
            this.annotationConfiguration = new AnnotationConfiguration().configure(new File("conf/hibernate.cfg.xml")).addPackage("com.angelis.game.database.entity");

            this.annotationConfiguration
                .addAnnotatedClass(AccountEntity.class)
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(StorageEntity.class)
                .addAnnotatedClass(GuildEntity.class)
                .addAnnotatedClass(StorageItemEntity.class)
                .addAnnotatedClass(CraftEntity.class)
                .addAnnotatedClass(GatherEntity.class)
                .addAnnotatedClass(SkillEntity.class)
                .addAnnotatedClass(QuestEntity.class)
            ;

            this.sessionFactory = this.annotationConfiguration.buildSessionFactory();
        }
        catch (final Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void onInit() {
        log.info("DatabaseService started");
    }

    @Override
    public void onDestroy() {
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /** SINGLETON */
    public static DatabaseService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final DatabaseService instance = new DatabaseService();
    }
}
