package model.entities;

import database.GenericDAO;

public final class EntityDAO {
	public static GenericDAO<Person> PERSON_DAO = new GenericDAO<>(Person.class);
	public static GenericDAO<Remote> REMOTE_DAO = new GenericDAO<>(Remote.class);
	public static GenericDAO<Address> ADDRESS_DAO = new GenericDAO<>(Address.class);
}
