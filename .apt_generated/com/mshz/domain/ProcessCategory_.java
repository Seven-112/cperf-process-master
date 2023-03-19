package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProcessCategory.class)
public abstract class ProcessCategory_ {

	public static volatile SingularAttribute<ProcessCategory, String> name;
	public static volatile SingularAttribute<ProcessCategory, String> description;
	public static volatile SingularAttribute<ProcessCategory, Long> id;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

