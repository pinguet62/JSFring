package fr.pinguet62.dictionary.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Right.class)
public abstract class Right_ {

	public static volatile SingularAttribute<Right, String> code;
	public static volatile SetAttribute<Right, Profile> profiles;
	public static volatile SingularAttribute<Right, String> title;

}

