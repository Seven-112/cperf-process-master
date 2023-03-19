package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.ProcessEventRecurrence;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mshz.domain.EventTrigger} entity. This class is used
 * in {@link com.mshz.web.rest.EventTriggerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-triggers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventTriggerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProcessEventRecurrence
     */
    public static class ProcessEventRecurrenceFilter extends Filter<ProcessEventRecurrence> {

        public ProcessEventRecurrenceFilter() {
        }

        public ProcessEventRecurrenceFilter(ProcessEventRecurrenceFilter filter) {
            super(filter);
        }

        @Override
        public ProcessEventRecurrenceFilter copy() {
            return new ProcessEventRecurrenceFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter editorId;

    private LocalDateFilter createdAt;

    private StringFilter name;

    private ProcessEventRecurrenceFilter recurrence;

    private BooleanFilter disabled;

    private StringFilter editorName;

    private LocalDateFilter firstStartedAt;

    private LocalDateFilter nextStartAt;

    private IntegerFilter startCount;

    private LongFilter processId;

    public EventTriggerCriteria() {
    }

    public EventTriggerCriteria(EventTriggerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.recurrence = other.recurrence == null ? null : other.recurrence.copy();
        this.disabled = other.disabled == null ? null : other.disabled.copy();
        this.editorName = other.editorName == null ? null : other.editorName.copy();
        this.firstStartedAt = other.firstStartedAt == null ? null : other.firstStartedAt.copy();
        this.nextStartAt = other.nextStartAt == null ? null : other.nextStartAt.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
    }

    @Override
    public EventTriggerCriteria copy() {
        return new EventTriggerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }

    public LocalDateFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateFilter createdAt) {
        this.createdAt = createdAt;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ProcessEventRecurrenceFilter getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(ProcessEventRecurrenceFilter recurrence) {
        this.recurrence = recurrence;
    }

    public BooleanFilter getDisabled() {
        return disabled;
    }

    public void setDisabled(BooleanFilter disabled) {
        this.disabled = disabled;
    }

    public StringFilter getEditorName() {
        return editorName;
    }

    public void setEditorName(StringFilter editorName) {
        this.editorName = editorName;
    }

    public LocalDateFilter getFirstStartedAt() {
        return firstStartedAt;
    }

    public void setFirstStartedAt(LocalDateFilter firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
    }

    public LocalDateFilter getNextStartAt() {
        return nextStartAt;
    }

    public void setNextStartAt(LocalDateFilter nextStartAt) {
        this.nextStartAt = nextStartAt;
    }

    public IntegerFilter getStartCount() {
        return startCount;
    }

    public void setStartCount(IntegerFilter startCount) {
        this.startCount = startCount;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventTriggerCriteria that = (EventTriggerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(editorId, that.editorId) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(name, that.name) &&
            Objects.equals(recurrence, that.recurrence) &&
            Objects.equals(disabled, that.disabled) &&
            Objects.equals(editorName, that.editorName) &&
            Objects.equals(firstStartedAt, that.firstStartedAt) &&
            Objects.equals(nextStartAt, that.nextStartAt) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(processId, that.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        editorId,
        createdAt,
        name,
        recurrence,
        disabled,
        editorName,
        firstStartedAt,
        nextStartAt,
        startCount,
        processId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventTriggerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (recurrence != null ? "recurrence=" + recurrence + ", " : "") +
                (disabled != null ? "disabled=" + disabled + ", " : "") +
                (editorName != null ? "editorName=" + editorName + ", " : "") +
                (firstStartedAt != null ? "firstStartedAt=" + firstStartedAt + ", " : "") +
                (nextStartAt != null ? "nextStartAt=" + nextStartAt + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
            "}";
    }

}
