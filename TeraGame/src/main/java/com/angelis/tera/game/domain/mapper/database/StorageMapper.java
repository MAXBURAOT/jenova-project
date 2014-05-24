package com.angelis.tera.game.domain.mapper.database;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.database.entity.StorageEntity;
import com.angelis.tera.game.database.entity.StorageItemEntity;
import com.angelis.tera.game.models.item.Item;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.storage.StorageItem;

public class StorageMapper extends DatabaseMapper<StorageEntity, Storage> {
    
    @Override
    public StorageEntity map(final Storage model, final AbstractEntity linkedEntity) {
        final StorageEntity storageEntity = new StorageEntity(model.getId());
        
        // DIRECT
        storageEntity.setStorageType(model.getStorageType());
        storageEntity.setSize(model.getSize());
        
        // STORAGE ITEM
        final List<StorageItemEntity> storageItems = new FastList<>();
        for (final Entry<Integer, StorageItem> entry : model.getStorageItems().entrySet()) {
            final StorageItem storageItem = entry.getValue();
            
            final StorageItemEntity storageItemEntity = new StorageItemEntity(storageItem.getId());
            storageItemEntity.setSlot(entry.getKey());
            storageItemEntity.setColor(storageItem.getColor());
            storageItemEntity.setCount(storageItem.getCount());
            storageItemEntity.setCreatorName(storageItem.getCreatorName());
            storageItemEntity.setItemId(storageItem.getItem().getId());
            
            storageItemEntity.setStorage(storageEntity);
            storageItems.add(storageItemEntity);
        }
        storageEntity.setStorageItems(storageItems);
        
        return storageEntity;
    }

    @Override
    public Storage map(final StorageEntity entity, final AbstractModel linkedModel) {
        final Storage storage = new Storage(entity.getId());
        
        // DIRECT
        storage.setStorageType(entity.getStorageType());
        storage.setSize(entity.getSize());
        
        // STORAGE ITEM
        final Map<Integer, StorageItem> storageItems = new FastMap<>();
        for (final StorageItemEntity storageItemEntity : entity.getStorageItems()) {
            final StorageItem storageItem = new StorageItem(storageItemEntity.getId());
            storageItem.setColor(storageItemEntity.getColor());
            storageItem.setCount(storageItemEntity.getCount());
            storageItem.setCreatorName(storageItemEntity.getCreatorName());
            storageItem.setItem(new Item(storageItemEntity.getItemId()));
            
            storageItems.put(storageItemEntity.getSlot(), storageItem);
        }
        storage.setStorageItems(storageItems);
        
        return storage;
    }

    @Override
    public boolean equals(final Storage model, final StorageEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }
}
