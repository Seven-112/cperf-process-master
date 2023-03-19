package com.mshz.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskSubmission.class)
public abstract class TaskSubmission_ {

	public static volatile SingularAttribute<TaskSubmission, Boolean> valid;
	public static volatile SingularAttribute<TaskSubmission, Task> task;
	public static volatile SingularAttribute<TaskSubmission, Long> submitorId;
	public static volatile SingularAttribute<TaskSubmission, String> comment;
	public static volatile SingularAttribute<TaskSubmission, Long> id;
	public static volatile SingularAttribute<TaskSubmission, Instant> storeUp;

	public static final String VALID = "valid";
	public static final String TASK = "task";
	public static final String SUBMITOR_ID = "submitorId";
	public static final String COMMENT = "comment";
	public static final String ID = "id";
	public static final String STORE_UP = "storeUp";

}

