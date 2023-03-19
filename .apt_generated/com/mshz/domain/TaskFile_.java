package com.mshz.domain;

import com.mshz.domain.enumeration.TaskFileType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskFile.class)
public abstract class TaskFile_ {

	public static volatile SingularAttribute<TaskFile, Task> task;
	public static volatile SingularAttribute<TaskFile, String> description;
	public static volatile SingularAttribute<TaskFile, Long> id;
	public static volatile SingularAttribute<TaskFile, TaskFileType> type;
	public static volatile SingularAttribute<TaskFile, Long> fileId;

	public static final String TASK = "task";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String FILE_ID = "fileId";

}

