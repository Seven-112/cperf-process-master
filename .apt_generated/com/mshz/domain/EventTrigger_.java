package com.mshz.domain;

import com.mshz.domain.enumeration.ProcessEventRecurrence;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EventTrigger.class)
public abstract class EventTrigger_ {

	public static volatile SingularAttribute<EventTrigger, ProcessEventRecurrence> recurrence;
	public static volatile SingularAttribute<EventTrigger, String> editorName;
	public static volatile SingularAttribute<EventTrigger, Long> editorId;
	public static volatile SingularAttribute<EventTrigger, Instant> createdAt;
	public static volatile SingularAttribute<EventTrigger, Integer> startCount;
	public static volatile SingularAttribute<EventTrigger, Process> process;
	public static volatile SingularAttribute<EventTrigger, String> name;
	public static volatile SingularAttribute<EventTrigger, Boolean> disabled;
	public static volatile SingularAttribute<EventTrigger, Long> id;
	public static volatile SingularAttribute<EventTrigger, Instant> nextStartAt;
	public static volatile SingularAttribute<EventTrigger, Instant> firstStartedAt;

	public static final String RECURRENCE = "recurrence";
	public static final String EDITOR_NAME = "editorName";
	public static final String EDITOR_ID = "editorId";
	public static final String CREATED_AT = "createdAt";
	public static final String START_COUNT = "startCount";
	public static final String PROCESS = "process";
	public static final String NAME = "name";
	public static final String DISABLED = "disabled";
	public static final String ID = "id";
	public static final String NEXT_START_AT = "nextStartAt";
	public static final String FIRST_STARTED_AT = "firstStartedAt";

}

