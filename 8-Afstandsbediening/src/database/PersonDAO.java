package database;

import java.util.List;

import javax.sql.DataSource;

import model.dataobjects.Person;

public class PersonDAO extends AbstractDAO implements GenericDAO<Person>{

	public PersonDAO(DataSource datasource) {
		super(datasource);
	}

	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Person entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Person entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(Person entity) {
		// TODO Auto-generated method stub
		
	}

}
