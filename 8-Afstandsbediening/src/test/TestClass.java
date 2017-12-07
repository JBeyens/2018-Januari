package test;

import java.util.List;

import database.RemoteDAO;
import modelPersistent.Remote;

public class TestClass {

	public static void main(String[] args){
		RemoteDAO mgr = new RemoteDAO();
		
		//mgr.AddRemote("Test", 123456789);
		
		//mgr.DeleteRemote(9);
		
		//mgr.UpdateRemoteFrequency(10, 000000000);
		
		//Remote remote = mgr.ReadRemoteByID(10);
		
		//System.out.println(remote.getFrequency());
		Remote remote;
		
		List<Remote> list = mgr.ReadAll();
		
		for (int i = 0; i < list.size(); i++) {
			remote = list.get(i);
			System.out.println(remote.getSerialNumber());
		}
		
		

	}

}
