package br.com.garbo.persistence.android.core;

public class SQLCreate {

	public static String insert(CacheEntity cacheEntity){
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		
		sql.append( cacheEntity.getTableName() ).append("( ");
		
		for (CacheEntityField field : cacheEntity.getFields()) {
			if( fields.toString().length() == 0 ) {
				fields.append( field.getColumnName() );
				values.append( "?" );
			} else {
				fields.append(",").append( field.getColumnName() );
				values.append( ",?" );
			}
		}
		
		sql.append( fields );
		sql.append(") VALUES (");
		sql.append( values );
		sql.append(")");
		
		return sql.toString();
	}
	
	
	
	public static String update(CacheEntity cacheEntity){
		StringBuilder sql = new StringBuilder("UPDATE ");
		StringBuilder values = new StringBuilder("");
		
		sql.append( cacheEntity.getTableName() ).append(" SET ");
		
		CacheEntityField id = null;
		for (CacheEntityField field : cacheEntity.getFields()) {
			if( field.isId() ) {
				id = field;
			} else {
				if( values.toString().length() == 0 )
					values.append( field.getColumnName() ).append("=?");
				else
					values.append(",").append( field.getColumnName() ).append("=?");
			}
		}
		
		sql.append( values );
		sql.append(" WHERE ").append( id.getColumnName() ).append("=?");
		return sql.toString();
	}
	
	
	
	public static String delete(CacheEntity cacheEntity){
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append( cacheEntity.getTableName() ).append(" WHERE ");
		
		for (CacheEntityField field : cacheEntity.getFields()) {
			if( field.isId() ) {
				sql.append( field.getColumnName() ).append("=?");
			}
		}
		
		return sql.toString();
	}
	
	
	public static String selectAll(CacheEntity cacheEntity){
		StringBuilder sql = new StringBuilder("SELECT ");
		StringBuilder fields = new StringBuilder("");
		
		for (CacheEntityField field : cacheEntity.getFields()) {
			if( fields.toString().length() == 0 ) 
				fields.append( field.getColumnName() );
			else 
				fields.append(",").append( field.getColumnName() );
		}
		
		sql.append( fields.toString() ).append(" FROM ").append( cacheEntity.getTableName() );
		
		return sql.toString();
	}
	
	
}
