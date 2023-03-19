package com.mshz.domain;

import com.mshz.domain.enumeration.TaskStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskPauseHistory.class)
public abstract class TaskPauseHistory_ {

	public static volatile SingularAttribute<TaskPauseHistory, TaskStatus> oldTaskstatus;
	public static volatile SingularAttribute<TaskPauseHistory, Boolean> taskExecutionDeleyExeceed;
	public static volatile SingularAttribute<TaskPauseHistory, Instant> pausedAt;
	public static volatile SingularAttribute<TaskPauseHistory, Long> id;
	public static volatile SingularAttribute<TaskPauseHistory, Long> taskId;
	public static volatile SingularAttribute<TaskPauseHistory, Instant> restartedAt;

	public static final String OLD_TASKSTATUS = "oldTaskstatus";
	public static final String TASK_EXECUTION_DELEY_EXECEED = "taskExecutionDeleyExeceed";
	public static final String PAUSED_AT = "pausedAt";
	public static final String ID = "id";
	public static final String TASK_ID = "taskId";
	public static final String RESTARTED_AT = "restartedAt";

}

