package model.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-01-01T12:37:54.299+0100")
@StaticMetamodel(Person.class)
public class Person_ {
	public static volatile SingularAttribute<Person, Integer> id;
	public static volatile SingularAttribute<Person, String> firstname;
	public static volatile SingularAttribute<Person, String> lastname;
	public static volatile SingularAttribute<Person, Address> address;
	public static volatile SingularAttribute<Person, Date> endOfContract;
}
