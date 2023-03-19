package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.ProcessPriority;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.domain.Process} entity. This class is used
 * in {@link com.mshz.web.rest.ProcessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProcessPriority
     */
    public static class ProcessPriorityFilter extends Filter<ProcessPriority> {

        public ProcessPriorityFilter() {
        }

        public ProcessPriorityFilter(ProcessPriorityFilter filter) {
            super(filter);
        }

        @Override
        public ProcessPriorityFilter copy() {
            return new ProcessPriorityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ProcessPriorityFilter priorityLevel;

    private BooleanFilter valid;

    private InstantFilter previewStartAt;

    private InstantFilter startAt;

    private InstantFilter previewFinishAt;

    private InstantFilter finishedAt;

    private InstantFilter createdAt;

    private IntegerFilter startCount;

    private LongFilter modelId;

    private LongFilter editorId;

    private LongFilter procedureId;

    private LongFilter runnableProcessId;

    private LongFilter queryId;

    private InstantFilter canceledAt;

    private LongFilter categoryId;

    public ProcessCriteria() {
    }

    public ProcessCriteria(ProcessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.priorityLevel = other.priorityLevel == null ? null : other.priorityLevel.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.previewStartAt = other.previewStartAt == null ? null : other.previewStartAt.copy();
        this.startAt = other.startAt == null ? null : other.startAt.copy();
        this.previewFinishAt = other.previewFinishAt == null ? null : other.previewFinishAt.copy();
        this.finishedAt = other.finishedAt == null ? null : other.finishedAt.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.modelId = other.modelId == null ? null : other.modelId.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
        this.procedureId = other.procedureId == null ? null : other.procedureId.copy();
        this.runnableProcessId = other.runnableProcessId == null ? null : other.runnableProcessId.copy();
        this.queryId = other.queryId == null ? null : other.queryId.copy();
        this.canceledAt = other.canceledAt == null ? null : other.canceledAt.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public ProcessCriteria copy() {
        return new ProcessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ProcessPriorityFilter getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(ProcessPriorityFilter priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }

    public InstantFilter getPreviewStartAt() {
        return previewStartAt;
    }

    public void setPreviewStartAt(InstantFilter previewStartAt) {
        this.previewStartAt = previewStartAt;
    }

    public InstantFilter getStartAt() {
        return startAt;
    }

    public void setStartAt(InstantFilter startAt) {
        this.startAt = startAt;
    }

    public InstantFilter getPreviewFinishAt() {
        return previewFinishAt;
    }

    public void setPreviewFinishAt(InstantFilter previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
    }

    public InstantFilter getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(InstantFilter finishedAt) {
        this.finishedAt = finishedAt;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public IntegerFilter getStartCount() {
        return startCount;
    }

    public void setStartCount(IntegerFilter startCount) {
        this.startCount = startCount;
    }

    public LongFilter getModelId() {
        return modelId;
    }

    public void setModelId(LongFilter modelId) {
        this.modelId = modelId;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }

    public LongFilter getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(LongFilter procedureId) {
        this.procedureId = procedureId;
    }

    public LongFilter getRunnableProcessId() {
        return runnableProcessId;
    }

    public void setRunnableProcessId(LongFilter runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
    }

    public LongFilter getQueryId() {
        return queryId;
    }

    public void setQueryId(LongFilter queryId) {
        this.queryId = queryId;
    }

    public InstantFilter getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(InstantFilter canceledAt) {
        this.canceledAt = canceledAt;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessCriteria that = (ProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(priorityLevel, that.priorityLevel) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(previewStartAt, that.previewStartAt) &&
            Objects.equals(startAt, that.startAt) &&
            Objects.equals(previewFinishAt, that.previewFinishAt) &&
            Objects.equals(finishedAt, that.finishedAt) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(modelId, that.modelId) &&
            Objects.equals(editorId, that.editorId) &&
            Objects.equals(procedureId, that.procedureId) &&
            Objects.equals(runnableProcessId, that.runnableProcessId) &&
            Objects.equals(queryId, that.queryId) &&
            Objects.equals(canceledAt, that.canceledAt) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        priorityLevel,
        valid,
        previewStartAt,
        startAt,
        previewFinishAt,
        finishedAt,
        createdAt,
        startCount,
        modelId,
        editorId,
        procedureId,
        runnableProcessId,
        queryId,
        canceledAt,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (priorityLevel != null ? "priorityLevel=" + priorityLevel + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (previewStartAt != null ? "previewStartAt=" + previewStartAt + ", " : "") +
                (startAt != null ? "startAt=" + startAt + ", " : "") +
                (previewFinishAt != null ? "previewFinishAt=" + previewFinishAt + ", " : "") +
                (finishedAt != null ? "finishedAt=" + finishedAt + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (modelId != null ? "modelId=" + modelId + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
                (procedureId != null ? "procedureId=" + procedureId + ", " : "") +
                (runnableProcessId != null ? "runnableProcessId=" + runnableProcessId + ", " : "") +
                (queryId != null ? "queryId=" + queryId + ", " : "") +
                (canceledAt != null ? "canceledAt=" + canceledAt + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
