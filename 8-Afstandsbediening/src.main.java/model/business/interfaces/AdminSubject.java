package model.business.interfaces;

import model.business.User;

public interface AdminSubject {
	void addObserver(User o);
	void removeObserver(User o);
}
