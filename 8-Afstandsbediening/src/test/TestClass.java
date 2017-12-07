package test;

import java.util.List;

import database.AbstractDAO;
import database.RemoteDAO;
import modelPersistent.Remote;

public class TestClass {

	public static void main(String[] args){
		AbstractDAO mgr = new RemoteDAO();
		
		Remote remote = (Remote) mgr.findOne(8);
		
		System.out.println(remote.getFrequency());
		
		

	}

}
