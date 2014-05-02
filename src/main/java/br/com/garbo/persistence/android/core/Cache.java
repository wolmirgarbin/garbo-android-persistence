package br.com.garbo.persistence.android.core;

import java.io.Serializable;
import java.util.HashMap;

public class Cache implements Serializable {

	private static final long serialVersionUID = 3313435139622963615L;

	private static Cache instance;
	
	
	private HashMap<String, CacheEntity> hashCache;
	
	
	public static Cache getInstance() {
		if( instance == null ) {
			instance = new Cache();
			instance.hashCache = new HashMap<String, CacheEntity>();
		}
		
		return instance;
	}
	
	public void add(CacheEntity c) {
		instance.hashCache.put(c.getTableName(), c);
	}
	
	public CacheEntity get(Class<?> entityClass) {
		String name = CacheReflection.getTableName(entityClass);
		if( instance.hashCache.containsKey(name) ) 
			return instance.hashCache.get( name );
		
		CacheEntity cacheEntity = CacheReflection.create(entityClass);
		instance.hashCache.put(name, cacheEntity);
		
		return cacheEntity;
	}
}
