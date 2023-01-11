package com.dhlee.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.PassiveExpiringMap;

public class CacheExample {

	public CacheExample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			testCache();
			
			testLRUCache();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void sleep(int ms) {
		try {
			System.out.println( String.format("sleep %,d ms...", ms) );
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			;	
		}
	}
	
	private static void testLRUCache() {
		int cahceSize = 10;
		int expireSecs = 10; // 

		LRUExpiringCache<String, String> expCache = new LRUExpiringCache<String, String>(cahceSize, expireSecs);

		int iter = 55;
		int i = 0;
		while (i < iter) {
			expCache.put("key" + (i % 20), "value" + (i % 20));
			i++;
		}

		System.out.println("cache.size = " + expCache.size());
		sleep(5 * 1000);
		System.out.println("cache.size = " + expCache.size());
		sleep(5 * 1000);
		System.out.println("cache.size = " + expCache.size());
	}
	
	public static void testCache() throws InterruptedException {
      PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<String, Integer>
      expirationPolicy = new PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<>(
      5, TimeUnit.SECONDS);
      
      final int MAX_ENTRIES = 10;
      Map cacheMap = new LinkedHashMap(MAX_ENTRIES+1, .75F, true) {
          // This method is called just after a new entry has been added
          public boolean removeEldestEntry(Map.Entry eldest) {
              return size() > MAX_ENTRIES;
          }
      };
      
      PassiveExpiringMap<String, String> cache = new PassiveExpiringMap<>(expirationPolicy, cacheMap);
      
      int iter = 55;
      int i=0;
      while(i<iter) {
    	  cache.put("key" +(i%20), "value" +(i%20));
    	  i++;    	  
      }
      
      System.out.println("cache.size = " + cache.size());
      Thread.sleep(5 *1000);
      System.out.println("cache.size = " + cache.size());
      
	}	 
}
