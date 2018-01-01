package model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-01-01T12:37:54.332+0100")
@StaticMetamodel(Remote.class)
public class Remote_ {
	public static volatile SingularAttribute<Remote, Integer> id;
	public static volatile SingularAttribute<Remote, String> serialNumber;
	public static volatile SingularAttribute<Remote, Boolean> isActive;
	public static volatile SingularAttribute<Remote, Long> frequency;
	public static volatile SingularAttribute<Remote, Person> person;
}
