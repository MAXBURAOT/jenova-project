package com.angelis.tera.common.utils;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;

public class CollectionUtils {
    public static final <K, V> void addToMapOfList(final Map<K, List<V>> map, final K key, final V value) {
        List<V> values = map.get(key);
        if (values == null) {
            values = new FastList<>();
            map.put(key, values);
        }
        
        values.add(value);
    }
    
    public static final <K, V> void addAllToMapOfList(final Map<K, List<V>> map, final K key, final List<V> values) {
        for (final V value : values) {
            CollectionUtils.addToMapOfList(map, key, value);
        }
    }
}
