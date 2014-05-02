package br.com.garbo.persistence.android.core;

import java.io.Serializable;
import java.lang.reflect.Field;

public class CacheEntityField implements Serializable {

	private static final long serialVersionUID = -4946525591738874542L;

	private String fieldName;
	private String methodGet;
	private String columnName;
	private boolean id;
	private boolean nullable;
	private Class<?> type;
	
	
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public boolean isId() {
		return id;
	}
	public void setId(boolean id) {
		this.id = id;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getMethodGet() {
		return methodGet;
	}
	public void setMethodGet(String methodGet) {
		this.methodGet = methodGet;
	}
	
}
