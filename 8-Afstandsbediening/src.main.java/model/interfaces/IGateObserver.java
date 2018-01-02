package model.interfaces;

import java.util.ArrayList;

import model.entities.Person;

public interface IGateObserver {
	public void setFrequency(long frequency);
	public void setPersons(ArrayList<Person> persons);
}
