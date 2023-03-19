package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.domain.enumeration.TaskStatus;

/**
 * A TaskPauseHistory.
 */
@Entity
@Table(name = "task_pause_histories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskPauseHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotNull
    @Column(name = "paused_at", nullable = false)
    private Instant pausedAt;

    @Column(name = "restarted_at")
    private Instant restartedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_taskstatus")
    private TaskStatus oldTaskstatus;

    @Column(name = "task_execution_deley_execeed")
    private Boolean taskExecutionDeleyExeceed;

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

    public TaskPauseHistory taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Instant getPausedAt() {
        return pausedAt;
    }

    public TaskPauseHistory pausedAt(Instant pausedAt) {
        this.pausedAt = pausedAt;
        return this;
    }

    public void setPausedAt(Instant pausedAt) {
        this.pausedAt = pausedAt;
    }

    public Instant getRestartedAt() {
        return restartedAt;
    }

    public TaskPauseHistory restartedAt(Instant restartedAt) {
        this.restartedAt = restartedAt;
        return this;
    }

    public void setRestartedAt(Instant restartedAt) {
        this.restartedAt = restartedAt;
    }

    public TaskStatus getOldTaskstatus() {
        return oldTaskstatus;
    }

    public TaskPauseHistory oldTaskstatus(TaskStatus oldTaskstatus) {
        this.oldTaskstatus = oldTaskstatus;
        return this;
    }

    public void setOldTaskstatus(TaskStatus oldTaskstatus) {
        this.oldTaskstatus = oldTaskstatus;
    }

    public Boolean isTaskExecutionDeleyExeceed() {
        return taskExecutionDeleyExeceed;
    }

    public TaskPauseHistory taskExecutionDeleyExeceed(Boolean taskExecutionDeleyExeceed) {
        this.taskExecutionDeleyExeceed = taskExecutionDeleyExeceed;
        return this;
    }

    public void setTaskExecutionDeleyExeceed(Boolean taskExecutionDeleyExeceed) {
        this.taskExecutionDeleyExeceed = taskExecutionDeleyExeceed;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskPauseHistory)) {
            return false;
        }
        return id != null && id.equals(((TaskPauseHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskPauseHistory{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", pausedAt='" + getPausedAt() + "'" +
            ", restartedAt='" + getRestartedAt() + "'" +
            ", oldTaskstatus='" + getOldTaskstatus() + "'" +
            ", taskExecutionDeleyExeceed='" + isTaskExecutionDeleyExeceed() + "'" +
            "}";
    }
}
