package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.domain.enumeration.TaskStatus;

/**
 * A TaskStatusTraking.
 */
@Entity
@Table(name = "task_status_traking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskStatusTraking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "tracing_at")
    private Instant tracingAt;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "justification")
    private String justification;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "editable")
    private Boolean editable;

    @Column(name = "execeed")
    private Boolean execeed;

    @Column(name = "perf_indicator")
    private Boolean perfIndicator;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public TaskStatusTraking taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskStatusTraking status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getTracingAt() {
        return tracingAt;
    }

    public TaskStatusTraking tracingAt(Instant tracingAt) {
        this.tracingAt = tracingAt;
        return this;
    }

    public void setTracingAt(Instant tracingAt) {
        this.tracingAt = tracingAt;
    }

    public String getJustification() {
        return justification;
    }

    public TaskStatusTraking justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getUserId() {
        return userId;
    }

    public TaskStatusTraking userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean isEditable() {
        return editable;
    }

    public TaskStatusTraking editable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean isExeceed() {
        return execeed;
    }

    public TaskStatusTraking execeed(Boolean execeed) {
        this.execeed = execeed;
        return this;
    }

    public void setExeceed(Boolean execeed) {
        this.execeed = execeed;
    }

    public Boolean isPerfIndicator() {
        return perfIndicator;
    }

    public TaskStatusTraking perfIndicator(Boolean perfIndicator) {
        this.perfIndicator = perfIndicator;
        return this;
    }

    public void setPerfIndicator(Boolean perfIndicator) {
        this.perfIndicator = perfIndicator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskStatusTraking)) {
            return false;
        }
        return id != null && id.equals(((TaskStatusTraking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskStatusTraking{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", status='" + getStatus() + "'" +
            ", tracingAt='" + getTracingAt() + "'" +
            ", justification='" + getJustification() + "'" +
            ", userId=" + getUserId() +
            ", editable='" + isEditable() + "'" +
            ", execeed='" + isExeceed() + "'" +
            ", perfIndicator='" + isPerfIndicator() + "'" +
            "}";
    }
}
