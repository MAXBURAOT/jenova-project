package com.angelis.tera.common.domain.mapper.database;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.angelis.tera.common.database.entity.AbstractDatabaseEntity;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;

public abstract class DatabaseMapper<E extends AbstractDatabaseEntity, M extends AbstractModel> {
    
    public final E map(final M model) {
        return this.map(model, null);
    }
    
    public final M map(final E entity) {
        return this.map(entity, null);
    }
    
    public final static void merge(Object from, Object to){
        try {   
            BeanUtils.copyProperties(to, from);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public abstract E map(final M model, AbstractEntity linkedEntity);
    public abstract M map(final E entity, AbstractModel linkedModel);
    public abstract boolean equals(M model, E entity);
}
