package com.mshz.domain;

import com.mshz.domain.enumeration.ProcessPriority;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Process.class)
public abstract class Process_ {

	public static volatile SingularAttribute<Process, Long> editorId;
	public static volatile SingularAttribute<Process, Integer> startCount;
	public static volatile SingularAttribute<Process, Long> modelId;
	public static volatile SingularAttribute<Process, String> description;
	public static volatile SingularAttribute<Process, String> label;
	public static volatile SingularAttribute<Process, Long> procedureId;
	public static volatile SingularAttribute<Process, Instant> finishedAt;
	public static volatile SingularAttribute<Process, Long> queryId;
	public static volatile SingularAttribute<Process, Boolean> valid;
	public static volatile SingularAttribute<Process, Instant> previewFinishAt;
	public static volatile SingularAttribute<Process, Instant> createdAt;
	public static volatile SingularAttribute<Process, ProcessPriority> priorityLevel;
	public static volatile SingularAttribute<Process, Long> id;
	public static volatile SingularAttribute<Process, ProcessCategory> category;
	public static volatile SingularAttribute<Process, Instant> previewStartAt;
	public static volatile SingularAttribute<Process, Long> runnableProcessId;
	public static volatile SingularAttribute<Process, Instant> startAt;

	public static final String EDITOR_ID = "editorId";
	public static final String START_COUNT = "startCount";
	public static final String MODEL_ID = "modelId";
	public static final String DESCRIPTION = "description";
	public static final String LABEL = "label";
	public static final String PROCEDURE_ID = "procedureId";
	public static final String FINISHED_AT = "finishedAt";
	public static final String QUERY_ID = "queryId";
	public static final String VALID = "valid";
	public static final String PREVIEW_FINISH_AT = "previewFinishAt";
	public static final String CREATED_AT = "createdAt";
	public static final String PRIORITY_LEVEL = "priorityLevel";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String PREVIEW_START_AT = "previewStartAt";
	public static final String RUNNABLE_PROCESS_ID = "runnableProcessId";
	public static final String START_AT = "startAt";

}

