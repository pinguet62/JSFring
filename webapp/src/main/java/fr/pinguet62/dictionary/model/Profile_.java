package fr.pinguet62.dictionary.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ {

	public static volatile SetAttribute<Profile, Right> rights;
	public static volatile SingularAttribute<Profile, Integer> id;
	public static volatile SingularAttribute<Profile, String> title;
	public static volatile SetAttribute<Profile, User> users;

}

