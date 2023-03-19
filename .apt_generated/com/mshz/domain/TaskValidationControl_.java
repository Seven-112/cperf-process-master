package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskValidationControl.class)
public abstract class TaskValidationControl_ {

	public static volatile SingularAttribute<TaskValidationControl, Boolean> valid;
	public static volatile SingularAttribute<TaskValidationControl, Task> task;
	public static volatile SingularAttribute<TaskValidationControl, Long> id;
	public static volatile SingularAttribute<TaskValidationControl, String> label;
	public static volatile SingularAttribute<TaskValidationControl, Boolean> required;

	public static final String VALID = "valid";
	public static final String TASK = "task";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String REQUIRED = "required";

}

