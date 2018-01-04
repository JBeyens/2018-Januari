package model.business;

import java.util.ArrayList;
import values.DefaultSettings;

public class Administrator implements AdminSubject{
	private ArrayList<AdminObserver> listeners;
	private long frequency;
	
	public Administrator(ArrayList<AdminObserver> list){
		this.setListeners(list);
		this.setFrequency(DefaultSettings.RANDOM.nextLong());
	}
	
	public ArrayList<AdminObserver> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<AdminObserver> listeners) {
		this.listeners = listeners;
	}
	
	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public void addObserver(AdminObserver o) {
		this.listeners.add(o);
	}

	@Override
	public void removeObserver(AdminObserver o) {
		this.listeners.remove(o);
	}

	@Override
	public void notifyAllObservers() {
		for (AdminObserver adminObserver : listeners) {
			adminObserver.update(getFrequency());
		}
	}
	
	
}
