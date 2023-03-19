package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProcessCategoryUser.class)
public abstract class ProcessCategoryUser_ {

	public static volatile SingularAttribute<ProcessCategoryUser, Long> processId;
	public static volatile SingularAttribute<ProcessCategoryUser, String> userFullName;
	public static volatile SingularAttribute<ProcessCategoryUser, String> userEmail;
	public static volatile SingularAttribute<ProcessCategoryUser, Long> id;
	public static volatile SingularAttribute<ProcessCategoryUser, Long> userId;
	public static volatile SingularAttribute<ProcessCategoryUser, Long> categoryId;

	public static final String PROCESS_ID = "processId";
	public static final String USER_FULL_NAME = "userFullName";
	public static final String USER_EMAIL = "userEmail";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String CATEGORY_ID = "categoryId";

}

