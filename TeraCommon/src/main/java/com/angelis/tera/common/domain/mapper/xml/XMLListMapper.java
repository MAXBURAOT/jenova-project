package com.angelis.tera.common.domain.mapper.xml;

import java.util.List;

import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.common.xml.entity.AbstractXMLEntity;

public abstract class XMLListMapper<E extends AbstractXMLEntity, M extends AbstractModel> {
    
    public final E map(final List<M> models) {
        return this.map(models, null);
    }
    
    public final List<M> map(final E entity) {
        return this.map(entity, null);
    }
    
    public abstract E map(final List<M> models, AbstractEntity linkedEntity);
    public abstract List<M> map(final E entity, AbstractModel linkedModel);
    public abstract boolean equals(M model, E entity);
}
