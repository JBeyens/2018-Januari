package model.interfaces;

import model.entities.Person;

public interface IGateObserver {
	public void setFrequency(long frequency);
	public void registerPerson(Person person);
	public void deActivatePerson(Person person);
}
