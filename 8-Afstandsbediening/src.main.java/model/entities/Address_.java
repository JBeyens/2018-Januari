package model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-12-31T12:38:38.368+0100")
@StaticMetamodel(Address.class)
public class Address_ {
	public static volatile SingularAttribute<Address, Integer> id;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, Integer> nr;
	public static volatile SingularAttribute<Address, Integer> mailBox;
	public static volatile SingularAttribute<Address, Integer> postalCode;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> country;
}
