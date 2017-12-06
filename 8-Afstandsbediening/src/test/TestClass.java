package test;


import database.RemoteDAO;

public class TestClass {

	public static void main(String[] args){
		RemoteDAO mgr = new RemoteDAO();
		
		mgr.AddRemote("Test", 123456789);
		
		

	}

}
