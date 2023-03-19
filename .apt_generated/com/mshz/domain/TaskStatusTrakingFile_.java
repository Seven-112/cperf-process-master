package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskStatusTrakingFile.class)
public abstract class TaskStatusTrakingFile_ {

	public static volatile SingularAttribute<TaskStatusTrakingFile, String> fileName;
	public static volatile SingularAttribute<TaskStatusTrakingFile, Long> id;
	public static volatile SingularAttribute<TaskStatusTrakingFile, TaskStatusTraking> track;
	public static volatile SingularAttribute<TaskStatusTrakingFile, Long> fileId;

	public static final String FILE_NAME = "fileName";
	public static final String ID = "id";
	public static final String TRACK = "track";
	public static final String FILE_ID = "fileId";

}

