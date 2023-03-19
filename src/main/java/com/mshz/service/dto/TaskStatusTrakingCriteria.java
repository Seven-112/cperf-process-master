package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.TaskStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.domain.TaskStatusTraking} entity. This class is used
 * in {@link com.mshz.web.rest.TaskStatusTrakingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-status-trakings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskStatusTrakingCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TaskStatus
     */
    public static class TaskStatusFilter extends Filter<TaskStatus> {

        public TaskStatusFilter() {
        }

        public TaskStatusFilter(TaskStatusFilter filter) {
            super(filter);
        }

        @Override
        public TaskStatusFilter copy() {
            return new TaskStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter taskId;

    private TaskStatusFilter status;

    private InstantFilter tracingAt;

    private LongFilter userId;

    private BooleanFilter editable;

    private BooleanFilter execeed;

    private BooleanFilter perfIndicator;

    public TaskStatusTrakingCriteria() {
    }

    public TaskStatusTrakingCriteria(TaskStatusTrakingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.tracingAt = other.tracingAt == null ? null : other.tracingAt.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.editable = other.editable == null ? null : other.editable.copy();
        this.execeed = other.execeed == null ? null : other.execeed.copy();
        this.perfIndicator = other.perfIndicator == null ? null : other.perfIndicator.copy();
    }

    @Override
    public TaskStatusTrakingCriteria copy() {
        return new TaskStatusTrakingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public TaskStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TaskStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getTracingAt() {
        return tracingAt;
    }

    public void setTracingAt(InstantFilter tracingAt) {
        this.tracingAt = tracingAt;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public BooleanFilter getEditable() {
        return editable;
    }

    public void setEditable(BooleanFilter editable) {
        this.editable = editable;
    }

    public BooleanFilter getExeceed() {
        return execeed;
    }

    public void setExeceed(BooleanFilter execeed) {
        this.execeed = execeed;
    }

    public BooleanFilter getPerfIndicator() {
        return perfIndicator;
    }

    public void setPerfIndicator(BooleanFilter perfIndicator) {
        this.perfIndicator = perfIndicator;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskStatusTrakingCriteria that = (TaskStatusTrakingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(tracingAt, that.tracingAt) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(editable, that.editable) &&
            Objects.equals(execeed, that.execeed) &&
            Objects.equals(perfIndicator, that.perfIndicator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taskId,
        status,
        tracingAt,
        userId,
        editable,
        execeed,
        perfIndicator
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskStatusTrakingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (tracingAt != null ? "tracingAt=" + tracingAt + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (editable != null ? "editable=" + editable + ", " : "") +
                (execeed != null ? "execeed=" + execeed + ", " : "") +
                (perfIndicator != null ? "perfIndicator=" + perfIndicator + ", " : "") +
            "}";
    }

}
