package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TaskValidationControl.
 */
@Entity
@Table(name = "task_validation_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskValidationControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "valid")
    private Boolean valid;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskValidationControls", allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public TaskValidationControl label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isRequired() {
        return required;
    }

    public TaskValidationControl required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isValid() {
        return valid;
    }

    public TaskValidationControl valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Task getTask() {
        return task;
    }

    public TaskValidationControl task(Task task) {
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
        if (!(o instanceof TaskValidationControl)) {
            return false;
        }
        return id != null && id.equals(((TaskValidationControl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskValidationControl{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", required='" + isRequired() + "'" +
            ", valid='" + isValid() + "'" +
            "}";
    }
}
