package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskItem.class)
public abstract class TaskItem_ {

	public static volatile SingularAttribute<TaskItem, String> checkerEmail;
	public static volatile SingularAttribute<TaskItem, String> editorName;
	public static volatile SingularAttribute<TaskItem, Long> editorId;
	public static volatile SingularAttribute<TaskItem, String> name;
	public static volatile SingularAttribute<TaskItem, Boolean> checked;
	public static volatile SingularAttribute<TaskItem, Long> checkerId;
	public static volatile SingularAttribute<TaskItem, String> checkerName;
	public static volatile SingularAttribute<TaskItem, Long> id;
	public static volatile SingularAttribute<TaskItem, Long> taskId;
	public static volatile SingularAttribute<TaskItem, Boolean> required;
	public static volatile SingularAttribute<TaskItem, String> editorEmail;

	public static final String CHECKER_EMAIL = "checkerEmail";
	public static final String EDITOR_NAME = "editorName";
	public static final String EDITOR_ID = "editorId";
	public static final String NAME = "name";
	public static final String CHECKED = "checked";
	public static final String CHECKER_ID = "checkerId";
	public static final String CHECKER_NAME = "checkerName";
	public static final String ID = "id";
	public static final String TASK_ID = "taskId";
	public static final String REQUIRED = "required";
	public static final String EDITOR_EMAIL = "editorEmail";

}

