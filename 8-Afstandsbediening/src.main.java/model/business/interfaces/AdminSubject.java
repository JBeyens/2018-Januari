package model.business.interfaces;

public interface AdminSubject {
	void addObserver(AdminObserver o);
	void removeObserver(AdminObserver o);
}
