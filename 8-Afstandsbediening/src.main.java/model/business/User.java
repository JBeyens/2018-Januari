package model.business;

import model.business.interfaces.AdminObserver;
import model.entities.Person;
import model.entities.Remote;

public class User implements AdminObserver{
	private Person person;
	private Administrator gate;
	private Remote remote;

	public User(Person person, Remote remote, Administrator gate){
		this.person = person;
		this.gate = gate;
		this.remote = remote;
	}

	public Person getPerson() {
		return person;
	}
	
	public boolean openGate(){
		//hir komt dan boolean check of deze frequentie == admin frequency
		//sommige sites zeggen dat observer ook reference naar subject mag bijhouden mss es nadeken hierover
		return gate.askFrequency(giveId()) == getPerson().getRemote().getFrequency();		
	}

	public boolean giveId(Administrator gateAdmin){
		return gateAdmin.checkId(this);
	}
	
	public String giveId(){
		return getPerson().getRemote().getSerialNumber();
	}

	@Override
	public void update(long frequency) {
		//If remote not active, it cannot be updated
		//is logischer als dit in admin komt ergens
		if(getPerson().getRemote().getIsActive())
			getPerson().getRemote().setFrequency(frequency);
	}
	
}
