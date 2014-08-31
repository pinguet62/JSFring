package fr.pinguet62.dictionary.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Language.class)
public abstract class Language_ {

	public static volatile SingularAttribute<Language, String> code;
	public static volatile SingularAttribute<Language, String> name;
	public static volatile SetAttribute<Language, Description> descriptions;

}

