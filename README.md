# Garbo Android Persistence
ORM para Android que auxilia o desenvolvimento de aplicações android que utilizam banco de dados SQLite para persistência.

É um projeto simples com objetivo de simplificar o desenvolvimento de aplicações aproveitando o conhecimento que os desenvolvedores java possuem de Hibernate, Mapeamento Relacional e Spring JDBC.




# Download, Maven ou Gradle

-


# Get Start - Configuração

-


# Anotando o Model

-


# Insert, update e delete

-


# Select

A busca de dados é essencial para um sistema que trabalha com armazenamento de dados.
Em algumas situações é necessário performance para a busca de dados, pensando nisso, foi desenvolvido a parte de selects como é o spring jdbc, permitindo ao usuário uma grande flexibilidade na busca dos dados e filtros no where.

Buscar todos os registros por um model:


public class ModelRepository extends GarboRepository<Model, Long> {

	public ModelRepository(GarboDatabaseHelper garboDatabaseHelper) {
		super(garboDatabaseHelper);
	}
	
	public List<Model> findAll() {
		return query("select ID, NAME, OTHER from TABLE_MODEL_NAME ", new RowMapModel<Model>());
	}
	
	private static final class RowMapModel<E> implements RowMapper<Model> {
	    public Model mapRow(Cursor cursor, int rowNum) throws GarboPersistenceException {
	    	Model ret = new Model();
			
	    	ret.setId( cursor.getLong( cursor.getColumnIndex("ID") ) );
			ret.setName( cursor.getLong( cursor.getColumnIndex("NAME") ) );
			...
			
			return ret;
	    }
	}
}




