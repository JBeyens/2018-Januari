package model.business.interfaces;

public interface AdminObserver {
	void update(long frequency);
	
	String getSerial();
}
