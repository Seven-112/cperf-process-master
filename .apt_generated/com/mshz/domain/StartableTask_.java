package com.mshz.domain;

import com.mshz.domain.enumeration.StartableTaskCond;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StartableTask.class)
public abstract class StartableTask_ {

	public static volatile SingularAttribute<StartableTask, Instant> createdAt;
	public static volatile SingularAttribute<StartableTask, String> startableProcessName;
	public static volatile SingularAttribute<StartableTask, Long> triggerTaskId;
	public static volatile SingularAttribute<StartableTask, String> startableTaskName;
	public static volatile SingularAttribute<StartableTask, StartableTaskCond> startCond;
	public static volatile SingularAttribute<StartableTask, Long> id;
	public static volatile SingularAttribute<StartableTask, String> triggerTaskName;
	public static volatile SingularAttribute<StartableTask, Long> startableTaskId;
	public static volatile SingularAttribute<StartableTask, Long> userId;
	public static volatile SingularAttribute<StartableTask, String> triggerProcessName;

	public static final String CREATED_AT = "createdAt";
	public static final String STARTABLE_PROCESS_NAME = "startableProcessName";
	public static final String TRIGGER_TASK_ID = "triggerTaskId";
	public static final String STARTABLE_TASK_NAME = "startableTaskName";
	public static final String START_COND = "startCond";
	public static final String ID = "id";
	public static final String TRIGGER_TASK_NAME = "triggerTaskName";
	public static final String STARTABLE_TASK_ID = "startableTaskId";
	public static final String USER_ID = "userId";
	public static final String TRIGGER_PROCESS_NAME = "triggerProcessName";

}

