package model.business.interfaces;

import model.business.User;
import values.DeactivatePersonResult;
import values.RegisterPersonResult;

public interface AdminSubject {
	RegisterPersonResult registerUser(User observer);
	DeactivatePersonResult deactivateUser(User observer);
}
