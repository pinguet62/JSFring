package fr.pinguet62.dictionary.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Description.class)
public abstract class Description_ {

	public static volatile SingularAttribute<Description, Language> language;
	public static volatile SingularAttribute<Description, Integer> id;
	public static volatile SingularAttribute<Description, Keyword> keyword;
	public static volatile SingularAttribute<Description, String> title;
	public static volatile SingularAttribute<Description, String> content;

}

