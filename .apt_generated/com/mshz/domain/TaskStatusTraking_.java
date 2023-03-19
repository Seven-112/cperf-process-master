package com.mshz.domain;

import com.mshz.domain.enumeration.TaskStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskStatusTraking.class)
public abstract class TaskStatusTraking_ {

	public static volatile SingularAttribute<TaskStatusTraking, Boolean> perfIndicator;
	public static volatile SingularAttribute<TaskStatusTraking, Boolean> editable;
	public static volatile SingularAttribute<TaskStatusTraking, Instant> tracingAt;
	public static volatile SingularAttribute<TaskStatusTraking, Long> id;
	public static volatile SingularAttribute<TaskStatusTraking, String> justification;
	public static volatile SingularAttribute<TaskStatusTraking, Boolean> execeed;
	public static volatile SingularAttribute<TaskStatusTraking, Long> userId;
	public static volatile SingularAttribute<TaskStatusTraking, Long> taskId;
	public static volatile SingularAttribute<TaskStatusTraking, TaskStatus> status;

	public static final String PERF_INDICATOR = "perfIndicator";
	public static final String EDITABLE = "editable";
	public static final String TRACING_AT = "tracingAt";
	public static final String ID = "id";
	public static final String JUSTIFICATION = "justification";
	public static final String EXECEED = "execeed";
	public static final String USER_ID = "userId";
	public static final String TASK_ID = "taskId";
	public static final String STATUS = "status";

}

