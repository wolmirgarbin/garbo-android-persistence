package br.com.garbo.persistence.android;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import br.com.garbo.persistence.android.core.Cache;
import br.com.garbo.persistence.android.core.CacheEntity;
import br.com.garbo.persistence.android.core.CacheEntityField;
import br.com.garbo.persistence.android.core.SQLCreate;

public abstract class GarboRepository<E extends Serializable, PK extends Serializable> {

	private GarboDatabaseHelper garboDatabaseHelper;
	private SQLiteDatabase database;
	
	public GarboRepository(GarboDatabaseHelper garboDatabaseHelper) {
		this.garboDatabaseHelper = garboDatabaseHelper;
	}
	
	
	public void insert(E entity) {
		insert(Arrays.asList(entity));
	}
	
	public void insert(List<E> lsEntity) {
		
		if( verifyList(lsEntity) ) 
			return;
		
		CacheEntity cacheEntity = Cache.getInstance().get( lsEntity.get(0).getClass() );
		SQLiteStatement stmt = getDatabaseWritable().compileStatement(SQLCreate.insert(cacheEntity));
		
		if (!database.inTransaction())
			database.beginTransaction();
		
		try {
			for(Object entity : lsEntity) {
				int qtdColumn = cacheEntity.getFields().size();
				for(int index = 0; index < qtdColumn; index++ ) {
					final CacheEntityField column = cacheEntity.getFields().get(index);
					
					//Property property = cache.getPropertyByColumn(column.getFieldName());
					Method meth = entity.getClass().getMethod( column.getMethodGet() );  
					Object param = meth.invoke(entity);

					int indexToStatement = index + 1;
					addValuesInStmtForType(stmt, param, indexToStatement);
				}
				
				stmt.executeInsert();
			}
			
			database.setTransactionSuccessful();
			
		} catch(Exception e) {
			throw new GarboPersistenceException(e.getMessage());
		} finally {
			stmt.close();
			database.endTransaction();
			database.close();
		}
	}
	
	public void update(E entity) {
		update(Arrays.asList(entity));
	}
	
	private void update(List<E> lsEntity) {
		if( verifyList(lsEntity) ) 
			return;
		
		CacheEntity cacheEntity = Cache.getInstance().get( lsEntity.get(0).getClass() );
		SQLiteStatement stmt = getDatabaseWritable().compileStatement(SQLCreate.update(cacheEntity));
		
		if (!database.inTransaction())
			database.beginTransaction();
		
		try {
			for(Object entity : lsEntity) {
				int qtdColumn = cacheEntity.getFields().size();
				CacheEntityField columnId = null;
				int index = 0;
				for(; index < qtdColumn; index++ ) {
					final CacheEntityField column = cacheEntity.getFields().get(index);
					
					if( column.isId() ) {
						columnId = column;
					} else {
						Method meth = entity.getClass().getMethod( column.getMethodGet() );  
						Object param = meth.invoke(entity);
	
						int indexToStatement = index + 1;
						addValuesInStmtForType(stmt, param, indexToStatement);
					}
				}
				
				// Coloca o id no statement
				Method meth = entity.getClass().getMethod( columnId.getMethodGet() );  
				Object param = meth.invoke(entity);

				int indexToStatement = index + 1;
				addValuesInStmtForType(stmt, param, indexToStatement);
				
				stmt.executeInsert();
			}
			
			database.setTransactionSuccessful();
			
		} catch(Exception e) {
			throw new GarboPersistenceException(e.getMessage());
		} finally {
			stmt.close();
			database.endTransaction();
			database.close();
		}
	}
	
	public E merge(E entity) {
		// verificar e pegar o id, se estiver diferente de null update ou insere
		
		
		return entity;
	}
	
	public void delete(E entity) {
		delete(Arrays.asList(entity));
	}
	
	public void delete(List<E> lsEntity) {
		if( verifyList(lsEntity) ) 
			return;
		
		CacheEntity cacheEntity = Cache.getInstance().get( lsEntity.get(0).getClass() );
		SQLiteStatement stmt = getDatabaseWritable().compileStatement(SQLCreate.delete(cacheEntity));
		
		if (!database.inTransaction())
			database.beginTransaction();
		
		try {
			for(Object entity : lsEntity) {
				int qtdColumn = cacheEntity.getFields().size();

				for(int index = 0; index < qtdColumn; index++ ) {
					final CacheEntityField column = cacheEntity.getFields().get(index);
					if( column.isId() ) {
						// Coloca o id no statement
						Method meth = entity.getClass().getMethod( column.getMethodGet() );  
						Object param = meth.invoke(entity);

						addValuesInStmtForType(stmt, param, 0);
						
						break;
					}
				}
				
				stmt.executeInsert();
			}
			
			database.setTransactionSuccessful();
			
		} catch(Exception e) {
			throw new GarboPersistenceException(e.getMessage());
		} finally {
			stmt.close();
			database.endTransaction();
			database.close();
		}
	}
	
	
	public long count(Class<E> clazz) {
		CacheEntity cacheEntity = Cache.getInstance().get( clazz );
		Cursor cursor = getDatabaseReadable().rawQuery(SQLCreate.selectCount(cacheEntity), null);
		cursor.moveToFirst();
		return cursor.getLong(cursor.getColumnIndex("QTD"));
	}
	
	
	public <E> E findById(Class<E> clazz, PK id) {
		
		return null;
	}
	
	public <E> List<E> findAll(Class<E> clazz) {
		CacheEntity cacheEntity = Cache.getInstance().get(clazz);
		SQLiteStatement stmt = getDatabaseReadable().compileStatement(SQLCreate.update(cacheEntity));
		
		
		return new ArrayList<E>();
	}
	
	public List<E> findByFilter(E entity) {
		
		return new ArrayList<E>();
	}
	
	
	/**
	 * Close databaseHelper
	 */
	public void close() {
		if( garboDatabaseHelper != null )
			garboDatabaseHelper.close();
	}
	
	/**
	 * Get data base writable
	 * @return
	 */
	public SQLiteDatabase getDatabaseReadable() {
		if( (database == null || !database.isOpen()) || (database != null && database.isOpen() && !database.inTransaction()))
			database = garboDatabaseHelper.getReadableDatabase();
			
		return database;
	}
	
	/**
	 * Get data base writable
	 * @return
	 */
	public SQLiteDatabase getDatabaseWritable() {
		if( (database == null || !database.isOpen()) || (database != null && database.isOpen() && !database.inTransaction()))
			database = garboDatabaseHelper.getWritableDatabase();
			
		return database;
	}
	
	private boolean verifyList(List<E> ls) {
		return ls == null ? true : ls.isEmpty();
	}
	
	
	/**
	 * Adiciona os valores ao statemente no saveAll e no updateAll
	 * 
	 * @param myStmt
	 * @param param
	 * @param indexToStatement
	 */
	private void addValuesInStmtForType(final SQLiteStatement myStmt, Object param, int indexToStatement) {
		if( param == null )
			myStmt.bindNull(indexToStatement);
		else if( param instanceof String )
			myStmt.bindString(indexToStatement, (String) param);
		else if( param instanceof Double )
			myStmt.bindDouble(indexToStatement, (Double) param);
		else if( param instanceof Float )
			myStmt.bindDouble(indexToStatement, (Float) param);
		else if( param instanceof Long )
			myStmt.bindLong(indexToStatement, (Long) param);
		else if( param instanceof Integer )
			myStmt.bindLong(indexToStatement, (Integer) param);
		else if( param instanceof byte[] )
			myStmt.bindBlob(indexToStatement, (byte[]) param);
	}
	
	
	public List<E> query(String sql, RowMapper<E> mapRow, String... selectArgs) throws GarboPersistenceException {
		try { 
			Cursor cursor = getDatabaseReadable().rawQuery(sql, selectArgs);
			List<E> toReturn = new ArrayList<E>();
			
			int count = cursor.getCount();
			cursor.moveToFirst();
			for (int rowNum = 0; rowNum < count; rowNum++) {
				toReturn.add(mapRow.mapRow(cursor, rowNum));
				cursor.moveToNext();
			}
			return toReturn;
		} catch(Exception e) {
			throw new GarboPersistenceException(e.getMessage());
		}
	}
}
