package com.mshz.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemCheckJustification.class)
public abstract class ItemCheckJustification_ {

	public static volatile SingularAttribute<ItemCheckJustification, Instant> date;
	public static volatile SingularAttribute<ItemCheckJustification, Long> taskItemId;
	public static volatile SingularAttribute<ItemCheckJustification, Boolean> checked;
	public static volatile SingularAttribute<ItemCheckJustification, Long> id;
	public static volatile SingularAttribute<ItemCheckJustification, String> justification;

	public static final String DATE = "date";
	public static final String TASK_ITEM_ID = "taskItemId";
	public static final String CHECKED = "checked";
	public static final String ID = "id";
	public static final String JUSTIFICATION = "justification";

}

