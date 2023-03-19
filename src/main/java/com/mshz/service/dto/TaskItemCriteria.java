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

/**
 * Criteria class for the {@link com.mshz.domain.TaskItem} entity. This class is used
 * in {@link com.mshz.web.rest.TaskItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter taskId;

    private BooleanFilter checked;

    private LongFilter checkerId;

    private StringFilter checkerName;

    private StringFilter checkerEmail;

    private LongFilter editorId;

    private StringFilter editorEmail;

    private StringFilter editorName;

    private BooleanFilter required;

    public TaskItemCriteria() {
    }

    public TaskItemCriteria(TaskItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
        this.checkerId = other.checkerId == null ? null : other.checkerId.copy();
        this.checkerName = other.checkerName == null ? null : other.checkerName.copy();
        this.checkerEmail = other.checkerEmail == null ? null : other.checkerEmail.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
        this.editorEmail = other.editorEmail == null ? null : other.editorEmail.copy();
        this.editorName = other.editorName == null ? null : other.editorName.copy();
        this.required = other.required == null ? null : other.required.copy();
    }

    @Override
    public TaskItemCriteria copy() {
        return new TaskItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public LongFilter getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(LongFilter checkerId) {
        this.checkerId = checkerId;
    }

    public StringFilter getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(StringFilter checkerName) {
        this.checkerName = checkerName;
    }

    public StringFilter getCheckerEmail() {
        return checkerEmail;
    }

    public void setCheckerEmail(StringFilter checkerEmail) {
        this.checkerEmail = checkerEmail;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }

    public StringFilter getEditorEmail() {
        return editorEmail;
    }

    public void setEditorEmail(StringFilter editorEmail) {
        this.editorEmail = editorEmail;
    }

    public StringFilter getEditorName() {
        return editorName;
    }

    public void setEditorName(StringFilter editorName) {
        this.editorName = editorName;
    }

    public BooleanFilter getRequired() {
        return required;
    }

    public void setRequired(BooleanFilter required) {
        this.required = required;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskItemCriteria that = (TaskItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(checkerId, that.checkerId) &&
            Objects.equals(checkerName, that.checkerName) &&
            Objects.equals(checkerEmail, that.checkerEmail) &&
            Objects.equals(editorId, that.editorId) &&
            Objects.equals(editorEmail, that.editorEmail) &&
            Objects.equals(editorName, that.editorName) &&
            Objects.equals(required, that.required);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        taskId,
        checked,
        checkerId,
        checkerName,
        checkerEmail,
        editorId,
        editorEmail,
        editorName,
        required
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
                (checkerId != null ? "checkerId=" + checkerId + ", " : "") +
                (checkerName != null ? "checkerName=" + checkerName + ", " : "") +
                (checkerEmail != null ? "checkerEmail=" + checkerEmail + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
                (editorEmail != null ? "editorEmail=" + editorEmail + ", " : "") +
                (editorName != null ? "editorName=" + editorName + ", " : "") +
                (required != null ? "required=" + required + ", " : "") +
            "}";
    }

}
