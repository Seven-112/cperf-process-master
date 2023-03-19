package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.domain.enumeration.ProcessPriority;

/**
 * A Process.
 */
@Entity
@Table(name = "process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "label", nullable = false)
    private String label;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    private ProcessPriority priorityLevel;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "preview_start_at")
    private Instant previewStartAt;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "preview_finish_at")
    private Instant previewFinishAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "start_count")
    private Integer startCount;

    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "editor_id")
    private Long editorId;

    @Column(name = "procedure_id")
    private Long procedureId;

    @Column(name = "runnable_process_id")
    private Long runnableProcessId;

    @Column(name = "query_id")
    private Long queryId;

    @Column(name = "canceled_at")
    private Instant canceledAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "processes", allowSetters = true)
    private ProcessCategory category;

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

    public Process label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Process description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcessPriority getPriorityLevel() {
        return priorityLevel;
    }

    public Process priorityLevel(ProcessPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public void setPriorityLevel(ProcessPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Boolean isValid() {
        return valid;
    }

    public Process valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getPreviewStartAt() {
        return previewStartAt;
    }

    public Process previewStartAt(Instant previewStartAt) {
        this.previewStartAt = previewStartAt;
        return this;
    }

    public void setPreviewStartAt(Instant previewStartAt) {
        this.previewStartAt = previewStartAt;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Process startAt(Instant startAt) {
        this.startAt = startAt;
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getPreviewFinishAt() {
        return previewFinishAt;
    }

    public Process previewFinishAt(Instant previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
        return this;
    }

    public void setPreviewFinishAt(Instant previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public Process finishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Process createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public Process startCount(Integer startCount) {
        this.startCount = startCount;
        return this;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Long getModelId() {
        return modelId;
    }

    public Process modelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getEditorId() {
        return editorId;
    }

    public Process editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public Process procedureId(Long procedureId) {
        this.procedureId = procedureId;
        return this;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public Long getRunnableProcessId() {
        return runnableProcessId;
    }

    public Process runnableProcessId(Long runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
        return this;
    }

    public void setRunnableProcessId(Long runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
    }

    public Long getQueryId() {
        return queryId;
    }

    public Process queryId(Long queryId) {
        this.queryId = queryId;
        return this;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public Instant getCanceledAt() {
        return canceledAt;
    }

    public Process canceledAt(Instant canceledAt) {
        this.canceledAt = canceledAt;
        return this;
    }

    public void setCanceledAt(Instant canceledAt) {
        this.canceledAt = canceledAt;
    }

    public ProcessCategory getCategory() {
        return category;
    }

    public Process category(ProcessCategory processCategory) {
        this.category = processCategory;
        return this;
    }

    public void setCategory(ProcessCategory processCategory) {
        this.category = processCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        return id != null && id.equals(((Process) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Process{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", valid='" + isValid() + "'" +
            ", previewStartAt='" + getPreviewStartAt() + "'" +
            ", startAt='" + getStartAt() + "'" +
            ", previewFinishAt='" + getPreviewFinishAt() + "'" +
            ", finishedAt='" + getFinishedAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", startCount=" + getStartCount() +
            ", modelId=" + getModelId() +
            ", editorId=" + getEditorId() +
            ", procedureId=" + getProcedureId() +
            ", runnableProcessId=" + getRunnableProcessId() +
            ", queryId=" + getQueryId() +
            ", canceledAt='" + getCanceledAt() + "'" +
            "}";
    }
}
