package model.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.business.interfaces.AdminObserver;
import model.business.interfaces.AdminSubject;
import values.DefaultSettings;

public class Administrator implements AdminSubject{
	private Logger log;
	private long frequency;
	private ArrayList<AdminObserver> listeners;
	
	public Administrator(ArrayList<AdminObserver> list){
		log = DefaultSettings.getLogger(this.getClass().getSimpleName());
		this.setFrequency(DefaultSettings.RANDOM.nextLong());
		this.setListeners(list);
	}
	
	/**
	 * Getter & setter for list of subscribed listeners
	 **/
	public ArrayList<AdminObserver> getListeners() {
		log.debug("Getting list of Listeners");
		return listeners;
	}
	public void setListeners(ArrayList<AdminObserver> listeners) {
		log.debug("Setting list of Listeners");
		this.listeners = listeners;
	}
	
	/**
	 * Getter & setter for frequency
	 **/
	public long getFrequency() {
		log.debug("Getting frequency");
		return frequency;
	}
	public void setFrequency(long frequency) {
		log.debug("Setting frequency");
		this.frequency = frequency;
	}

	/**
	 * Add- & remove function for observer pattern
	 **/
	@Override
	public void addObserver(AdminObserver o) {
		this.listeners.add(o);
	}
	@Override
	public void removeObserver(AdminObserver o) {
		this.listeners.remove(o);
	}

	/**
	 * Notify function for observer pattern
	 **/
	@Override
	public void notifyAllObservers() {
		for (AdminObserver adminObserver : listeners) {
			adminObserver.update(getFrequency());
		}
	}
	
	
}
