package fr.pinguet62.dictionary.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Keyword.class)
public abstract class Keyword_ {

	public static volatile SetAttribute<Keyword, Keyword> keywordsForAssociatedKeyword;
	public static volatile SingularAttribute<Keyword, Integer> id;
	public static volatile SetAttribute<Keyword, Keyword> keywordsForKeyword;
	public static volatile SetAttribute<Keyword, Description> descriptions;

}

