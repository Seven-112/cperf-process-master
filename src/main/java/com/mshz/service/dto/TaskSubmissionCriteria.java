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
 * Criteria class for the {@link com.mshz.domain.TaskSubmission} entity. This class is used
 * in {@link com.mshz.web.rest.TaskSubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskSubmissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter submitorId;

    private StringFilter comment;

    private InstantFilter storeUp;

    private BooleanFilter valid;

    private LongFilter taskId;

    public TaskSubmissionCriteria() {
    }

    public TaskSubmissionCriteria(TaskSubmissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.submitorId = other.submitorId == null ? null : other.submitorId.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.storeUp = other.storeUp == null ? null : other.storeUp.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public TaskSubmissionCriteria copy() {
        return new TaskSubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSubmitorId() {
        return submitorId;
    }

    public void setSubmitorId(LongFilter submitorId) {
        this.submitorId = submitorId;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public InstantFilter getStoreUp() {
        return storeUp;
    }

    public void setStoreUp(InstantFilter storeUp) {
        this.storeUp = storeUp;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskSubmissionCriteria that = (TaskSubmissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(submitorId, that.submitorId) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(storeUp, that.storeUp) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        submitorId,
        comment,
        storeUp,
        valid,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSubmissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (submitorId != null ? "submitorId=" + submitorId + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (storeUp != null ? "storeUp=" + storeUp + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
