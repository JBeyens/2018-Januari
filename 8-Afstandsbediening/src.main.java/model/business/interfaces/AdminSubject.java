package model.business.interfaces;

import model.business.PersonWrapper;
import values.UserDeactivationResult;
import values.UserRegistrationResult;

public interface AdminSubject {
	UserRegistrationResult registerUser(PersonWrapper observer);
	UserDeactivationResult deactivateUser(PersonWrapper observer);
}
