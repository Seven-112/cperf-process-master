package com.mshz.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EdgeInfo.class)
public abstract class EdgeInfo_ {

	public static volatile SingularAttribute<EdgeInfo, Boolean> valid;
	public static volatile SingularAttribute<EdgeInfo, String> sourceHandle;
	public static volatile SingularAttribute<EdgeInfo, Long> processId;
	public static volatile SingularAttribute<EdgeInfo, Long> id;
	public static volatile SingularAttribute<EdgeInfo, String> source;
	public static volatile SingularAttribute<EdgeInfo, String> targetHandle;
	public static volatile SingularAttribute<EdgeInfo, String> target;

	public static final String VALID = "valid";
	public static final String SOURCE_HANDLE = "sourceHandle";
	public static final String PROCESS_ID = "processId";
	public static final String ID = "id";
	public static final String SOURCE = "source";
	public static final String TARGET_HANDLE = "targetHandle";
	public static final String TARGET = "target";

}

