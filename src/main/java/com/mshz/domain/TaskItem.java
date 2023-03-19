package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TaskItem.
 */
@Entity
@Table(name = "task_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "checked")
    private Boolean checked;

    @NotNull
    @Column(name = "checker_id", nullable = false)
    private Long checkerId;

    @NotNull
    @Column(name = "checker_name", nullable = false)
    private String checkerName;

    @Column(name = "checker_email")
    private String checkerEmail;

    @Column(name = "editor_id")
    private Long editorId;

    @Column(name = "editor_email")
    private String editorEmail;

    @Column(name = "editor_name")
    private String editorName;

    @Column(name = "required")
    private Boolean required;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TaskItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTaskId() {
        return taskId;
    }

    public TaskItem taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean isChecked() {
        return checked;
    }

    public TaskItem checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getCheckerId() {
        return checkerId;
    }

    public TaskItem checkerId(Long checkerId) {
        this.checkerId = checkerId;
        return this;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public TaskItem checkerName(String checkerName) {
        this.checkerName = checkerName;
        return this;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckerEmail() {
        return checkerEmail;
    }

    public TaskItem checkerEmail(String checkerEmail) {
        this.checkerEmail = checkerEmail;
        return this;
    }

    public void setCheckerEmail(String checkerEmail) {
        this.checkerEmail = checkerEmail;
    }

    public Long getEditorId() {
        return editorId;
    }

    public TaskItem editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getEditorEmail() {
        return editorEmail;
    }

    public TaskItem editorEmail(String editorEmail) {
        this.editorEmail = editorEmail;
        return this;
    }

    public void setEditorEmail(String editorEmail) {
        this.editorEmail = editorEmail;
    }

    public String getEditorName() {
        return editorName;
    }

    public TaskItem editorName(String editorName) {
        this.editorName = editorName;
        return this;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Boolean isRequired() {
        return required;
    }

    public TaskItem required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskItem)) {
            return false;
        }
        return id != null && id.equals(((TaskItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", taskId=" + getTaskId() +
            ", checked='" + isChecked() + "'" +
            ", checkerId=" + getCheckerId() +
            ", checkerName='" + getCheckerName() + "'" +
            ", checkerEmail='" + getCheckerEmail() + "'" +
            ", editorId=" + getEditorId() +
            ", editorEmail='" + getEditorEmail() + "'" +
            ", editorName='" + getEditorName() + "'" +
            ", required='" + isRequired() + "'" +
            "}";
    }
}
