package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.domain.enumeration.StartableTaskCond;

/**
 * A StartableTask.
 */
@Entity
@Table(name = "startable_tasks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StartableTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "trigger_task_id")
    private Long triggerTaskId;

    @Column(name = "startable_task_id")
    private Long startableTaskId;

    @Column(name = "trigger_task_name")
    private String triggerTaskName;

    @Column(name = "startable_task_name")
    private String startableTaskName;

    @Column(name = "trigger_process_name")
    private String triggerProcessName;

    @Column(name = "startable_process_name")
    private String startableProcessName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_cond")
    private StartableTaskCond startCond;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriggerTaskId() {
        return triggerTaskId;
    }

    public StartableTask triggerTaskId(Long triggerTaskId) {
        this.triggerTaskId = triggerTaskId;
        return this;
    }

    public void setTriggerTaskId(Long triggerTaskId) {
        this.triggerTaskId = triggerTaskId;
    }

    public Long getStartableTaskId() {
        return startableTaskId;
    }

    public StartableTask startableTaskId(Long startableTaskId) {
        this.startableTaskId = startableTaskId;
        return this;
    }

    public void setStartableTaskId(Long startableTaskId) {
        this.startableTaskId = startableTaskId;
    }

    public String getTriggerTaskName() {
        return triggerTaskName;
    }

    public StartableTask triggerTaskName(String triggerTaskName) {
        this.triggerTaskName = triggerTaskName;
        return this;
    }

    public void setTriggerTaskName(String triggerTaskName) {
        this.triggerTaskName = triggerTaskName;
    }

    public String getStartableTaskName() {
        return startableTaskName;
    }

    public StartableTask startableTaskName(String startableTaskName) {
        this.startableTaskName = startableTaskName;
        return this;
    }

    public void setStartableTaskName(String startableTaskName) {
        this.startableTaskName = startableTaskName;
    }

    public String getTriggerProcessName() {
        return triggerProcessName;
    }

    public StartableTask triggerProcessName(String triggerProcessName) {
        this.triggerProcessName = triggerProcessName;
        return this;
    }

    public void setTriggerProcessName(String triggerProcessName) {
        this.triggerProcessName = triggerProcessName;
    }

    public String getStartableProcessName() {
        return startableProcessName;
    }

    public StartableTask startableProcessName(String startableProcessName) {
        this.startableProcessName = startableProcessName;
        return this;
    }

    public void setStartableProcessName(String startableProcessName) {
        this.startableProcessName = startableProcessName;
    }

    public Long getUserId() {
        return userId;
    }

    public StartableTask userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public StartableTask createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public StartableTaskCond getStartCond() {
        return startCond;
    }

    public StartableTask startCond(StartableTaskCond startCond) {
        this.startCond = startCond;
        return this;
    }

    public void setStartCond(StartableTaskCond startCond) {
        this.startCond = startCond;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartableTask)) {
            return false;
        }
        return id != null && id.equals(((StartableTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StartableTask{" +
            "id=" + getId() +
            ", triggerTaskId=" + getTriggerTaskId() +
            ", startableTaskId=" + getStartableTaskId() +
            ", triggerTaskName='" + getTriggerTaskName() + "'" +
            ", startableTaskName='" + getStartableTaskName() + "'" +
            ", triggerProcessName='" + getTriggerProcessName() + "'" +
            ", startableProcessName='" + getStartableProcessName() + "'" +
            ", userId=" + getUserId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", startCond='" + getStartCond() + "'" +
            "}";
    }
}
