package br.com.garbo.persistence.android.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import br.com.garbo.persistence.android.GarboPersistenceException;
import br.com.garbo.persistence.android.anotations.Column;
import br.com.garbo.persistence.android.anotations.Entity;
import br.com.garbo.persistence.android.anotations.Id;
import br.com.garbo.persistence.android.anotations.Transient;
import br.com.garbo.persistence.android.utils.StringUtils;

public class CacheReflection {
	
	private static final String METHOD_GET = "get";
	

	/**
	 * Cria um cache da reflexao dos objetos
	 * 
	 * @param entityClass
	 * @return
	 * @throws GarboPersistenceException
	 */
	public static CacheEntity create(Class<?> entityClass) throws GarboPersistenceException {
		// primeiro verifico se  entidade
		if( entityClass.getAnnotation(Entity.class) == null )
			throw new GarboPersistenceException("class "+ entityClass.getName() +" not contains @Entity");

		// inicia
		CacheEntity toReturn = new CacheEntity();
		toReturn.setClassName( entityClass.getName() );
		toReturn.setTableName( getTableName(entityClass) );
		toReturn.setFields( reflectionFieldsbyClass(entityClass) );
		return toReturn;
	}

	/**
	 * Percorre todos os campos da entidade para criar um cache das entidades para n�o fazer reflection sempre 
	 * 
	 * @param entityClass
	 * @return
	 */
	private static List<CacheEntityField> reflectionFieldsbyClass(Class<?> entityClass) {
		List<CacheEntityField> toReturn = new ArrayList<CacheEntityField>();
		
		int fieldsLength = entityClass.getDeclaredFields().length;
		CacheEntityField cacheField;
		for(int i = 0; i < fieldsLength; i++ ){
			cacheField = new CacheEntityField();
			
			Field field = entityClass.getDeclaredFields()[i];
			
			if( ! field.getName().equalsIgnoreCase("serialVersionUID") ) {
				
				Column column = field.getAnnotation(Column.class);
				String fieldName = field.getName();
				
				cacheField.setFieldName( fieldName );
				cacheField.setMethodGet( METHOD_GET + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1,fieldName.length()) );
				
				if( column != null ) {
					cacheField.setColumnName(StringUtils.isBlank(column.name()) ? fieldName.toUpperCase() : column.name().toUpperCase());
					cacheField.setId( field.getAnnotation(Id.class) != null );
					cacheField.setNullable( column.nullable() );
					
				} else if( field.getAnnotation(Transient.class) == null ) {
					cacheField.setColumnName(fieldName.toUpperCase());
					cacheField.setId( field.getAnnotation(Id.class) != null );
					cacheField.setNullable( true );
				}
				
				cacheField.setType( field.getType() );
				
				toReturn.add(cacheField);
			}
		}
		return toReturn;
	}
	
	/**
	 * Return name entity
	 * 
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(Class<?> entityClass) {
		Entity entity = entityClass.getAnnotation(Entity.class);
		if( entity != null ) {
			return StringUtils.isBlank(entity.name()) ? entityClass.getName() : entity.name();
		} else {
			return entityClass.getName();
		}
	}
}
