package br.com.garbo.persistence.android;

import android.database.Cursor;

public interface RowMapper<E> {
	
	E mapRow(Cursor cursor, int rowNum) throws GarboPersistenceException;
	
}
