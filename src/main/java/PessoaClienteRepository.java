

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.database.Cursor;
import br.com.garbo.persistence.android.GarboDatabaseHelper;
import br.com.garbo.persistence.android.GarboPersistenceException;
import br.com.garbo.persistence.android.GarboRepository;
import br.com.garbo.persistence.android.RowMapper;
import br.com.viasoft.android.crm.entity.PessoaClienteMobile;

public class PessoaClienteRepository extends GarboRepository<PessoaClienteMobile, Long> {

	public PessoaClienteRepository(GarboDatabaseHelper garboDatabaseHelper) {
		super(garboDatabaseHelper);
	}

	
	public List<PessoaClienteMobile> findAllOrderName() {
		return query(
			"SELECT IDCONTAMOV, NOME, APELIDO, CPF, CNPJ FROM PESSOACLIENTE ORDER BY APELIDO", 
			new PessoaRowMap<PessoaClienteMobile>());
	}
	
	
	public List<PessoaClienteMobile> findByNameOrIdOrderName(String search) {
		Long id = StringUtils.isNumeric(search) ? Long.valueOf(search) : null;
		
		if( id != null ) {
			return query(
				"SELECT IDCONTAMOV, NOME, APELIDO, CPF, CNPJ FROM PESSOACLIENTE WHERE IDCONTAMOV = "+ id +" ORDER BY NOME", 
				new PessoaRowMap<PessoaClienteMobile>());
		} else {
			return query(
				"SELECT IDCONTAMOV, NOME, APELIDO, CPF, CNPJ FROM PESSOACLIENTE WHERE NOME LIKE '%"+ search +"%' OR APELIDO LIKE '%"+ search +"%' ORDER BY NOME", 
				new PessoaRowMap<PessoaClienteMobile>());
		}
	}
	
	
	public Cursor selectReturnCursor(String filter) {
		Long id = StringUtils.isNumeric(filter) ? Long.valueOf(filter) : null;
		
		if( id != null )
			return getDataBase().rawQuery("select IDCONTAMOV as _id, NOME, APELIDO FROM PESSOACLIENTE WHERE IDCONTAMOV = "+ id +" ORDER BY NOME", null);
		else 
			return getDataBase().rawQuery("select IDCONTAMOV as _id, NOME, APELIDO FROM PESSOACLIENTE WHERE NOME LIKE '%"+ filter +"%' or APELIDO LIKE '%"+ filter +"%' ORDER BY NOME LIMIT 5,20", null);
	}

	
	private static final class PessoaRowMap<E> implements RowMapper<PessoaClienteMobile> {
	    public PessoaClienteMobile mapRow(Cursor cursor, int rowNum) throws GarboPersistenceException {
	    	PessoaClienteMobile p = new PessoaClienteMobile();
			p.setId( cursor.getLong( cursor.getColumnIndex("IDCONTAMOV") ) );
			p.setNome( cursor.getString( cursor.getColumnIndex("NOME") ) );
			p.setApelido( cursor.getString( cursor.getColumnIndex("APELIDO") ) );
			p.setCpf( cursor.getString( cursor.getColumnIndex("CPF") ) );
			p.setCnpj( cursor.getString( cursor.getColumnIndex("CNPJ") ) );
			return p;
	    }        
	}
		
}
