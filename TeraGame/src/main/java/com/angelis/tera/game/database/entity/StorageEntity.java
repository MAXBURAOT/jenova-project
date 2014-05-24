package com.angelis.tera.game.database.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javolution.util.FastList;

import com.angelis.tera.common.database.entity.AbstractDatabaseEntity;
import com.angelis.tera.game.models.enums.StorageTypeEnum;

@Entity
@Table(name = "storages")
public class StorageEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 5090455704132338034L;

    @Column
    private StorageTypeEnum storageType;

    @Column
    private int size;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage")
    private List<StorageItemEntity> storageItems;

    public StorageEntity(final Integer id, final StorageTypeEnum storageType) {
        super(id);
        this.storageType = storageType;
    }

    public StorageEntity(final StorageTypeEnum storageType) {
        super();
        this.storageType = storageType;
    }

    public StorageEntity(final Integer id) {
        this(id, null);
    }

    public StorageEntity() {
        super();
    }

    public StorageTypeEnum getStorageType() {
        return storageType;
    }

    public void setStorageType(final StorageTypeEnum storageType) {
        this.storageType = storageType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public List<StorageItemEntity> getStorageItems() {
        if (storageItems == null) {
            storageItems = new FastList<StorageItemEntity>();
        }
        return storageItems;
    }

    public void setStorageItems(final List<StorageItemEntity> storageItems) {
        this.storageItems = storageItems;
    }

    public void addStorageItem(final StorageItemEntity storageItem) {
        this.getStorageItems().add(storageItem);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + size;
        result = prime * result + ((storageType == null) ? 0 : storageType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof StorageEntity)) {
            return false;
        }
        final StorageEntity other = (StorageEntity) obj;
        if (size != other.size) {
            return false;
        }
        if (storageType != other.storageType) {
            return false;
        }
        return true;
    }
}
