package com.dhlee.test;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.PassiveExpiringMap;

public class LRUExpiringCache<K, V> implements Serializable {
	private static final long serialVersionUID = -4070351542505373563L;
	private PassiveExpiringMap<K, V> cache = null;

	@SuppressWarnings({ "serial", "unchecked" })
	public LRUExpiringCache(int capacity, int idleTimeoutSecs) {
		PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<String, Integer> expirationPolicy = new PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<>(
				idleTimeoutSecs, TimeUnit.SECONDS);
		@SuppressWarnings("rawtypes")
		Map cacheMap = new LinkedHashMap(capacity + 1, .75F, true) {
			public boolean removeEldestEntry(Map.Entry eldest) {
				return size() > capacity;
			}
		};
		cache = new PassiveExpiringMap<>(expirationPolicy, cacheMap);
	}

	public synchronized void clear() {
		cache.clear();
	}

	public V get(final K key) throws Throwable {
		return cache.get(key);
	}

	public synchronized void put(K key, V value) {
		cache.put(key, value);
	}
	
	public synchronized V remove(K key) {
		return cache.remove(key);
	}
	
	public int size() {
		return cache.size();
	}
}
