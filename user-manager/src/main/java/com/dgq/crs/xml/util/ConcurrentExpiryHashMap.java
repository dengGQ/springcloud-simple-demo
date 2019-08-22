package com.dgq.crs.xml.util;

import java.util.concurrent.ConcurrentHashMap;

/*
* @Description: 超时自动删除Map
* @author dgq 
* @date 2019年5月24日
*/
public class ConcurrentExpiryHashMap<K, V> {

	private long millisUntilExpiration;
	
    private ConcurrentHashMap<K, Entry> map;
	
	class Entry {
        private long   timestamp;
        private V val;

        Entry(long timestamp, V val) {
            this.timestamp = timestamp;
            this.val = val;
        }

        long   timestamp()                  { return timestamp;           }
        void   setTimestamp(long timestamp) { this.timestamp = timestamp; }
        void   setVal(V val)           { this.val = val;             }
        V val()                        { return val;                 }
    }
	
	public ConcurrentExpiryHashMap() {
		//默认超时时间1个小时
		this(3600000);
	}
	public ConcurrentExpiryHashMap(long millisUntilExpiration) {
		this.millisUntilExpiration = millisUntilExpiration;
		map = new ConcurrentHashMap<>(16);
	}
	
	public V get(K key) {
		Entry entry = entryFor(key);
		return entry!=null ? entry.val() : null;
	}

	public V put(K key, V value) {
		Entry entry = entryFor(key);
		if(entry != null) {
			entry.setTimestamp(System.currentTimeMillis());
			entry.setVal(value);
			return value;
		}else {
			Entry entry2 = this.map.put(key, new Entry(System.currentTimeMillis(), value));
			return entry2 == null ? null : entry2.val();
		}
	}
	
	public void remove(K key) {
		this.map.remove(key);
	}
	
	private Entry entryFor(K key) {
		//清理过期数据
		this.map.entrySet().parallelStream().forEach(entry->{
			long delta = System.currentTimeMillis() - entry.getValue().timestamp();
			if (delta < 0 || delta >= millisUntilExpiration) {
			    this.map.remove(entry.getKey());
			}
		});
		
		return this.map.get(key);
	}
	
	public static void main(String[] args) {
		try {
			
			ConcurrentExpiryHashMap<String, String> expiryMap = new ConcurrentExpiryHashMap<>(3000);
			expiryMap.put("dgq-1", "dgq-1");
			Thread.sleep(2000L);
			
			expiryMap.put("dgq-2", "dgq-2");
			
			System.out.println(expiryMap.get("dgq-1"));
			
			Thread.sleep(2000L);

			System.out.println(expiryMap.get("dgq-1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
