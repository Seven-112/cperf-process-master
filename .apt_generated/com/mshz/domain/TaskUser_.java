package com.mshz.domain;

import com.mshz.domain.enumeration.TaskUserRole;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskUser.class)
public abstract class TaskUser_ {

	public static volatile SingularAttribute<TaskUser, TaskUserRole> role;
	public static volatile SingularAttribute<TaskUser, Task> task;
	public static volatile SingularAttribute<TaskUser, String> userFullName;
	public static volatile SingularAttribute<TaskUser, String> userEmail;
	public static volatile SingularAttribute<TaskUser, Long> id;
	public static volatile SingularAttribute<TaskUser, Long> userId;

	public static final String ROLE = "role";
	public static final String TASK = "task";
	public static final String USER_FULL_NAME = "userFullName";
	public static final String USER_EMAIL = "userEmail";
	public static final String ID = "id";
	public static final String USER_ID = "userId";

}

