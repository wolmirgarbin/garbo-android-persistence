package br.com.garbo.persistence.android.utils;

import android.database.Cursor;

public abstract class CursorUtils {
	
	public static String stringByColumn(final Cursor cursor, final String columnName) {
		return stringByColumn(cursor, columnName, null);
	}
	
	public static String stringByColumn(final Cursor cursor, final String columnName, final String defaultReturn) {
		if( cursor == null ) 
			return defaultReturn;
		
		int columnIndex = cursor.getColumnIndex( columnName );
		if( columnIndex == 0 )
			return defaultReturn;
		
		return cursor.getString( columnIndex );
	}
	
	

	public static Integer integerByColumn(final Cursor cursor, final String columnName) {
		return integerByColumn(cursor, columnName, null);
	}
	
	public static Integer integerByColumn(final Cursor cursor, final String columnName, final Integer defaultReturn) {
		if( cursor == null ) 
			return defaultReturn;
		
		int columnIndex = cursor.getColumnIndex( columnName );
		if( columnIndex == 0 )
			return defaultReturn;
		
		return cursor.getInt( columnIndex );
	}
	
}
