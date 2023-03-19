package com.mshz.domain;

import com.mshz.domain.enumeration.JustifcationReason;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Justification.class)
public abstract class Justification_ {

	public static volatile SingularAttribute<Justification, JustifcationReason> reason;
	public static volatile SingularAttribute<Justification, Long> editorId;
	public static volatile SingularAttribute<Justification, Long> processId;
	public static volatile SingularAttribute<Justification, Boolean> accepted;
	public static volatile SingularAttribute<Justification, Long> id;
	public static volatile SingularAttribute<Justification, String> content;
	public static volatile SingularAttribute<Justification, Long> taskId;
	public static volatile SingularAttribute<Justification, Long> fileId;

	public static final String REASON = "reason";
	public static final String EDITOR_ID = "editorId";
	public static final String PROCESS_ID = "processId";
	public static final String ACCEPTED = "accepted";
	public static final String ID = "id";
	public static final String CONTENT = "content";
	public static final String TASK_ID = "taskId";
	public static final String FILE_ID = "fileId";

}

