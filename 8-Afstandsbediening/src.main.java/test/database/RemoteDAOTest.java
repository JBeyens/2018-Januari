package test.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entities.Remote;
import database.GenericDAO;

public class RemoteDAOTest {
	private ArrayList<Remote> tempList;
	
	@Before
	public void setUp(){
		
		
		tempList = new ArrayList<>();
	}

	@After
	public void tearDown(){
	}

	@Test
	@Transactional
	public void test_DeleteAll_Function() {
		GenericDAO<Remote> dao = new GenericDAO<>(Remote.class);
		dao.deleteAll(); 
		
		tempList = (ArrayList<Remote>) dao.findAll();
		
		assertEquals(tempList.size(), 0);
	}

}
