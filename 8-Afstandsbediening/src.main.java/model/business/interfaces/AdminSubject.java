package model.business.interfaces;

import model.business.User;
import values.UserDeactivationResult;
import values.UserRegistrationResult;

public interface AdminSubject {
	UserRegistrationResult registerUser(User observer);
	UserDeactivationResult deactivateUser(User observer);
}
