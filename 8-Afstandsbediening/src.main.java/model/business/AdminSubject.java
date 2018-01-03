package model.business;

public interface AdminSubject {
	void addObserver(AdminObserver o);
	void removeObserver(AdminObserver o);
	void notifyAllObservers();
}
