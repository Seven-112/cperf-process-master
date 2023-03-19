package com.mshz.domain;

import com.mshz.domain.enumeration.ProcessPriority;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskType;
import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, Boolean> manualMode;
	public static volatile SingularAttribute<Task, Long> groupId;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, TaskType> type;
	public static volatile SingularAttribute<Task, Long> riskId;
	public static volatile SingularAttribute<Task, Boolean> valid;
	public static volatile SingularAttribute<Task, Integer> nbMinuites;
	public static volatile SingularAttribute<Task, Integer> nbDays;
	public static volatile SingularAttribute<Task, ProcessPriority> priorityLevel;
	public static volatile SingularAttribute<Task, Long> processId;
	public static volatile SingularAttribute<Task, Integer> sheduledStartHour;
	public static volatile SingularAttribute<Task, Boolean> checked;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, Integer> sheduledStartMinute;
	public static volatile SingularAttribute<Task, Integer> nbMonths;
	public static volatile SingularAttribute<Task, Instant> startAt;
	public static volatile SingularAttribute<Task, Integer> nbPause;
	public static volatile SingularAttribute<Task, Task> startupTask;
	public static volatile SingularAttribute<Task, Double> logigramPosX;
	public static volatile SingularAttribute<Task, Double> logigramPosY;
	public static volatile SingularAttribute<Task, Integer> nbHours;
	public static volatile SingularAttribute<Task, Boolean> startWithProcess;
	public static volatile SingularAttribute<Task, Long> currentPauseHistoryId;
	public static volatile SingularAttribute<Task, Long> parentId;
	public static volatile SingularAttribute<Task, Instant> pauseAt;
	public static volatile SingularAttribute<Task, LocalDate> sheduledStartAt;
	public static volatile SingularAttribute<Task, Long> taskModelId;
	public static volatile SingularAttribute<Task, String> name;
	public static volatile SingularAttribute<Task, Instant> finishAt;
	public static volatile SingularAttribute<Task, Integer> nbYears;
	public static volatile SingularAttribute<Task, TaskStatus> status;

	public static final String MANUAL_MODE = "manualMode";
	public static final String GROUP_ID = "groupId";
	public static final String DESCRIPTION = "description";
	public static final String TYPE = "type";
	public static final String RISK_ID = "riskId";
	public static final String VALID = "valid";
	public static final String NB_MINUITES = "nbMinuites";
	public static final String NB_DAYS = "nbDays";
	public static final String PRIORITY_LEVEL = "priorityLevel";
	public static final String PROCESS_ID = "processId";
	public static final String SHEDULED_START_HOUR = "sheduledStartHour";
	public static final String CHECKED = "checked";
	public static final String ID = "id";
	public static final String SHEDULED_START_MINUTE = "sheduledStartMinute";
	public static final String NB_MONTHS = "nbMonths";
	public static final String START_AT = "startAt";
	public static final String NB_PAUSE = "nbPause";
	public static final String STARTUP_TASK = "startupTask";
	public static final String LOGIGRAM_POS_X = "logigramPosX";
	public static final String LOGIGRAM_POS_Y = "logigramPosY";
	public static final String NB_HOURS = "nbHours";
	public static final String START_WITH_PROCESS = "startWithProcess";
	public static final String CURRENT_PAUSE_HISTORY_ID = "currentPauseHistoryId";
	public static final String PARENT_ID = "parentId";
	public static final String PAUSE_AT = "pauseAt";
	public static final String SHEDULED_START_AT = "sheduledStartAt";
	public static final String TASK_MODEL_ID = "taskModelId";
	public static final String NAME = "name";
	public static final String FINISH_AT = "finishAt";
	public static final String NB_YEARS = "nbYears";
	public static final String STATUS = "status";

}

