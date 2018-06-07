package com.gray.newreaderview.reader.util;

import java.util.LinkedHashMap;

/**
 * @author wjy on 2018/5/29.
 */
public class LruCacheMap<K, V> extends LinkedHashMap<K, V> {
    private int maxEntries = 10;

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return size() > maxEntries;
    }
}
