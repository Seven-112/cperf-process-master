package com.mshz.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Procedure.class)
public abstract class Procedure_ {

	public static volatile SingularAttribute<Procedure, LocalDate> storeAt;
	public static volatile SingularAttribute<Procedure, String> name;
	public static volatile SingularAttribute<Procedure, Long> id;
	public static volatile SingularAttribute<Procedure, Long> fileId;

	public static final String STORE_AT = "storeAt";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String FILE_ID = "fileId";

}

