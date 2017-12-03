package database;

import java.util.List;

import javax.sql.DataSource;

import model.dataobjects.Remote;

public class RemoteDAO extends AbstractDAO implements GenericDAO<Remote>{

	public RemoteDAO(DataSource datasource) {
		super(datasource);
	}

	@Override
	public List<Remote> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Remote findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Remote entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Remote entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(Remote entity) {
		// TODO Auto-generated method stub
		
	}

}
