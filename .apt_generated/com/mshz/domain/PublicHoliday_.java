package com.mshz.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PublicHoliday.class)
public abstract class PublicHoliday_ {

	public static volatile SingularAttribute<PublicHoliday, LocalDate> ofDate;
	public static volatile SingularAttribute<PublicHoliday, String> name;
	public static volatile SingularAttribute<PublicHoliday, Long> id;

	public static final String OF_DATE = "ofDate";
	public static final String NAME = "name";
	public static final String ID = "id";

}

