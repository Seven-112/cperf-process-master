package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TaskSubmission.
 */
@Entity
@Table(name = "task_submission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskSubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "submitor_id", nullable = false)
    private Long submitorId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "store_up")
    private Instant storeUp;

    @Column(name = "valid")
    private Boolean valid;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskSubmissions", allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitorId() {
        return submitorId;
    }

    public TaskSubmission submitorId(Long submitorId) {
        this.submitorId = submitorId;
        return this;
    }

    public void setSubmitorId(Long submitorId) {
        this.submitorId = submitorId;
    }

    public String getComment() {
        return comment;
    }

    public TaskSubmission comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getStoreUp() {
        return storeUp;
    }

    public TaskSubmission storeUp(Instant storeUp) {
        this.storeUp = storeUp;
        return this;
    }

    public void setStoreUp(Instant storeUp) {
        this.storeUp = storeUp;
    }

    public Boolean isValid() {
        return valid;
    }

    public TaskSubmission valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Task getTask() {
        return task;
    }

    public TaskSubmission task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSubmission)) {
            return false;
        }
        return id != null && id.equals(((TaskSubmission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSubmission{" +
            "id=" + getId() +
            ", submitorId=" + getSubmitorId() +
            ", comment='" + getComment() + "'" +
            ", storeUp='" + getStoreUp() + "'" +
            ", valid='" + isValid() + "'" +
            "}";
    }
}
