package model.business;


public class TestUser {

	public static void main(String[] args) {
		Administrator admin = new Administrator();
		
		System.out.println("Administrator created");
		System.out.println("Administrator has " + admin.getListeners().size() +" observers");
	}

}
