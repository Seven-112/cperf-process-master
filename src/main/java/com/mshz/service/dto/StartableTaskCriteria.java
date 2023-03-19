package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.StartableTaskCond;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.domain.StartableTask} entity. This class is used
 * in {@link com.mshz.web.rest.StartableTaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /startable-tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StartableTaskCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StartableTaskCond
     */
    public static class StartableTaskCondFilter extends Filter<StartableTaskCond> {

        public StartableTaskCondFilter() {
        }

        public StartableTaskCondFilter(StartableTaskCondFilter filter) {
            super(filter);
        }

        @Override
        public StartableTaskCondFilter copy() {
            return new StartableTaskCondFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter triggerTaskId;

    private LongFilter startableTaskId;

    private StringFilter triggerTaskName;

    private StringFilter startableTaskName;

    private StringFilter triggerProcessName;

    private StringFilter startableProcessName;

    private LongFilter userId;

    private InstantFilter createdAt;

    private StartableTaskCondFilter startCond;

    public StartableTaskCriteria() {
    }

    public StartableTaskCriteria(StartableTaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.triggerTaskId = other.triggerTaskId == null ? null : other.triggerTaskId.copy();
        this.startableTaskId = other.startableTaskId == null ? null : other.startableTaskId.copy();
        this.triggerTaskName = other.triggerTaskName == null ? null : other.triggerTaskName.copy();
        this.startableTaskName = other.startableTaskName == null ? null : other.startableTaskName.copy();
        this.triggerProcessName = other.triggerProcessName == null ? null : other.triggerProcessName.copy();
        this.startableProcessName = other.startableProcessName == null ? null : other.startableProcessName.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.startCond = other.startCond == null ? null : other.startCond.copy();
    }

    @Override
    public StartableTaskCriteria copy() {
        return new StartableTaskCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTriggerTaskId() {
        return triggerTaskId;
    }

    public void setTriggerTaskId(LongFilter triggerTaskId) {
        this.triggerTaskId = triggerTaskId;
    }

    public LongFilter getStartableTaskId() {
        return startableTaskId;
    }

    public void setStartableTaskId(LongFilter startableTaskId) {
        this.startableTaskId = startableTaskId;
    }

    public StringFilter getTriggerTaskName() {
        return triggerTaskName;
    }

    public void setTriggerTaskName(StringFilter triggerTaskName) {
        this.triggerTaskName = triggerTaskName;
    }

    public StringFilter getStartableTaskName() {
        return startableTaskName;
    }

    public void setStartableTaskName(StringFilter startableTaskName) {
        this.startableTaskName = startableTaskName;
    }

    public StringFilter getTriggerProcessName() {
        return triggerProcessName;
    }

    public void setTriggerProcessName(StringFilter triggerProcessName) {
        this.triggerProcessName = triggerProcessName;
    }

    public StringFilter getStartableProcessName() {
        return startableProcessName;
    }

    public void setStartableProcessName(StringFilter startableProcessName) {
        this.startableProcessName = startableProcessName;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public StartableTaskCondFilter getStartCond() {
        return startCond;
    }

    public void setStartCond(StartableTaskCondFilter startCond) {
        this.startCond = startCond;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StartableTaskCriteria that = (StartableTaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(triggerTaskId, that.triggerTaskId) &&
            Objects.equals(startableTaskId, that.startableTaskId) &&
            Objects.equals(triggerTaskName, that.triggerTaskName) &&
            Objects.equals(startableTaskName, that.startableTaskName) &&
            Objects.equals(triggerProcessName, that.triggerProcessName) &&
            Objects.equals(startableProcessName, that.startableProcessName) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(startCond, that.startCond);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        triggerTaskId,
        startableTaskId,
        triggerTaskName,
        startableTaskName,
        triggerProcessName,
        startableProcessName,
        userId,
        createdAt,
        startCond
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StartableTaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (triggerTaskId != null ? "triggerTaskId=" + triggerTaskId + ", " : "") +
                (startableTaskId != null ? "startableTaskId=" + startableTaskId + ", " : "") +
                (triggerTaskName != null ? "triggerTaskName=" + triggerTaskName + ", " : "") +
                (startableTaskName != null ? "startableTaskName=" + startableTaskName + ", " : "") +
                (triggerProcessName != null ? "triggerProcessName=" + triggerProcessName + ", " : "") +
                (startableProcessName != null ? "startableProcessName=" + startableProcessName + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (startCond != null ? "startCond=" + startCond + ", " : "") +
            "}";
    }

}
