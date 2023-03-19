package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.domain.ItemCheckJustification} entity. This class is used
 * in {@link com.mshz.web.rest.ItemCheckJustificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-check-justifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemCheckJustificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter checked;

    private LongFilter taskItemId;

    private InstantFilter date;

    public ItemCheckJustificationCriteria() {
    }

    public ItemCheckJustificationCriteria(ItemCheckJustificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
        this.taskItemId = other.taskItemId == null ? null : other.taskItemId.copy();
        this.date = other.date == null ? null : other.date.copy();
    }

    @Override
    public ItemCheckJustificationCriteria copy() {
        return new ItemCheckJustificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public LongFilter getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(LongFilter taskItemId) {
        this.taskItemId = taskItemId;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemCheckJustificationCriteria that = (ItemCheckJustificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(taskItemId, that.taskItemId) &&
            Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        checked,
        taskItemId,
        date
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemCheckJustificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
                (taskItemId != null ? "taskItemId=" + taskItemId + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
            "}";
    }

}
