package br.com.garbo.persistence.android.core;

import java.io.Serializable;
import java.util.List;

public class CacheEntity implements Serializable {

	private static final long serialVersionUID = -4946525591738874542L;

	private String className;
	private String tableName;
	private List<CacheEntityField> fields;
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<CacheEntityField> getFields() {
		return fields;
	}
	public void setFields(List<CacheEntityField> fields) {
		this.fields = fields;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
