package br.com.garbo.persistence.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class GarboDatabaseHelper extends SQLiteOpenHelper {
	
	public GarboDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	
	/**
     * Retorna o banco de dados
     * 
     * @return
     */
    public SQLiteDatabase getDatabase(){
    	return this.getWritableDatabase();
    }
    
    
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
}
