package com.dhlee.test;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.junit.Assert;
import org.junit.Test;

public class PassiveExpiringMapTest {
	@Test public void givenDataMap_whenWrappingMapWithPassiveExpiringMap_thenObjectsAreRemovedWhenExpired() {
        
//        PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<String, Integer>
//            expirationPolicy = new PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<>(
//            5, TimeUnit.SECONDS);

//        PassiveExpiringMap<String, Integer> expiringMap = new PassiveExpiringMap<>(expirationPolicy, new HashMap<>());
        PassiveExpiringMap<String, Integer> pssiveExpiringMap = new PassiveExpiringMap<>(5 * 1000);
        Map<String, Integer> expiringMap = Collections.synchronizedMap(pssiveExpiringMap);
        expiringMap.put("one", Integer.valueOf(1));
        expiringMap.put("two", Integer.valueOf(2));
        expiringMap.put("three", Integer.valueOf(3));
        
        
        
        int initialCapacity = expiringMap.size();
        System.out.println("initialCapacity = " + initialCapacity);
        Assert.assertEquals(3, initialCapacity);

        System.out.println("Sleeping...");
        try { Thread.sleep(10000L); } catch (InterruptedException e) { }

        int updatedCapacity = expiringMap.size();
        System.out.println("updatedCapacity = " + updatedCapacity);
        Integer one = expiringMap.get("one");
        Assert.assertNull(one);
        Assert.assertEquals(0, updatedCapacity);
    }
}
