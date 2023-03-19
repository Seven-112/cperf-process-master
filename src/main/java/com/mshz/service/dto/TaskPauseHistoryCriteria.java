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
 * Criteria class for the {@link com.mshz.domain.TaskPauseHistory} entity. This class is used
 * in {@link com.mshz.web.rest.TaskPauseHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-pause-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskPauseHistoryCriteria implements Serializable, Criteria {
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

    private InstantFilter pausedAt;

    private InstantFilter restartedAt;

    private TaskStatusFilter oldTaskstatus;

    private BooleanFilter taskExecutionDeleyExeceed;

    public TaskPauseHistoryCriteria() {
    }

    public TaskPauseHistoryCriteria(TaskPauseHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.pausedAt = other.pausedAt == null ? null : other.pausedAt.copy();
        this.restartedAt = other.restartedAt == null ? null : other.restartedAt.copy();
        this.oldTaskstatus = other.oldTaskstatus == null ? null : other.oldTaskstatus.copy();
        this.taskExecutionDeleyExeceed = other.taskExecutionDeleyExeceed == null ? null : other.taskExecutionDeleyExeceed.copy();
    }

    @Override
    public TaskPauseHistoryCriteria copy() {
        return new TaskPauseHistoryCriteria(this);
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

    public InstantFilter getPausedAt() {
        return pausedAt;
    }

    public void setPausedAt(InstantFilter pausedAt) {
        this.pausedAt = pausedAt;
    }

    public InstantFilter getRestartedAt() {
        return restartedAt;
    }

    public void setRestartedAt(InstantFilter restartedAt) {
        this.restartedAt = restartedAt;
    }

    public TaskStatusFilter getOldTaskstatus() {
        return oldTaskstatus;
    }

    public void setOldTaskstatus(TaskStatusFilter oldTaskstatus) {
        this.oldTaskstatus = oldTaskstatus;
    }

    public BooleanFilter getTaskExecutionDeleyExeceed() {
        return taskExecutionDeleyExeceed;
    }

    public void setTaskExecutionDeleyExeceed(BooleanFilter taskExecutionDeleyExeceed) {
        this.taskExecutionDeleyExeceed = taskExecutionDeleyExeceed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskPauseHistoryCriteria that = (TaskPauseHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(pausedAt, that.pausedAt) &&
            Objects.equals(restartedAt, that.restartedAt) &&
            Objects.equals(oldTaskstatus, that.oldTaskstatus) &&
            Objects.equals(taskExecutionDeleyExeceed, that.taskExecutionDeleyExeceed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taskId,
        pausedAt,
        restartedAt,
        oldTaskstatus,
        taskExecutionDeleyExeceed
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskPauseHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (pausedAt != null ? "pausedAt=" + pausedAt + ", " : "") +
                (restartedAt != null ? "restartedAt=" + restartedAt + ", " : "") +
                (oldTaskstatus != null ? "oldTaskstatus=" + oldTaskstatus + ", " : "") +
                (taskExecutionDeleyExeceed != null ? "taskExecutionDeleyExeceed=" + taskExecutionDeleyExeceed + ", " : "") +
            "}";
    }

}
