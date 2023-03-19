package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.JustifcationReason;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mshz.domain.Justification} entity. This class is used
 * in {@link com.mshz.web.rest.JustificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /justifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JustificationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering JustifcationReason
     */
    public static class JustifcationReasonFilter extends Filter<JustifcationReason> {

        public JustifcationReasonFilter() {
        }

        public JustifcationReasonFilter(JustifcationReasonFilter filter) {
            super(filter);
        }

        @Override
        public JustifcationReasonFilter copy() {
            return new JustifcationReasonFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter content;

    private LongFilter fileId;

    private LongFilter taskId;

    private LongFilter processId;

    private JustifcationReasonFilter reason;

    private BooleanFilter accepted;

    private LongFilter editorId;

    public JustificationCriteria() {
    }

    public JustificationCriteria(JustificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.accepted = other.accepted == null ? null : other.accepted.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
    }

    @Override
    public JustificationCriteria copy() {
        return new JustificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public JustifcationReasonFilter getReason() {
        return reason;
    }

    public void setReason(JustifcationReasonFilter reason) {
        this.reason = reason;
    }

    public BooleanFilter getAccepted() {
        return accepted;
    }

    public void setAccepted(BooleanFilter accepted) {
        this.accepted = accepted;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JustificationCriteria that = (JustificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(content, that.content) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(accepted, that.accepted) &&
            Objects.equals(editorId, that.editorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        content,
        fileId,
        taskId,
        processId,
        reason,
        accepted,
        editorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JustificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (accepted != null ? "accepted=" + accepted + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
            "}";
    }

}
