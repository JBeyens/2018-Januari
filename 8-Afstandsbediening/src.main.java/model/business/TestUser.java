package model.business;

import java.util.ArrayList;

import model.business.interfaces.AdminObserver;
import model.entities.Person;

public class TestUser {

	public static void main(String[] args) {
		ArrayList<Person> listPerson = DataManager.getAllPersons();
		ArrayList<AdminObserver> observerList = new ArrayList<>();
		
		for (Person person : listPerson) {
			if(person.getRemote()!= null)
				if(person.getRemote().getIsActive())
					observerList.add(new User(person));
		}
		
		Administrator admin = new Administrator(observerList);
		
		System.out.println("Administrator created");
		System.out.println("Administrator has " + admin.getListeners().size() +" observers");
	}

}
