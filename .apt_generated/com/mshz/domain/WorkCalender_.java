package com.mshz.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(WorkCalender.class)
public abstract class WorkCalender_ {

	public static volatile SingularAttribute<WorkCalender, Integer> dayNumber;
	public static volatile SingularAttribute<WorkCalender, Instant> startTime;
	public static volatile SingularAttribute<WorkCalender, Long> id;
	public static volatile SingularAttribute<WorkCalender, Instant> endTime;

	public static final String DAY_NUMBER = "dayNumber";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";

}

