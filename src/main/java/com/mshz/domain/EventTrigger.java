package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mshz.domain.enumeration.ProcessEventRecurrence;

/**
 * A EventTrigger.
 */
@Entity
@Table(name = "event_trigger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventTrigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "editor_id", nullable = false)
    private Long editorId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private ProcessEventRecurrence recurrence;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "editor_name")
    private String editorName;

    @Column(name = "first_started_at")
    private LocalDate firstStartedAt;

    @Column(name = "next_start_at")
    private LocalDate nextStartAt;

    @Column(name = "start_count")
    private Integer startCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "eventTriggers", allowSetters = true)
    private Process process;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditorId() {
        return editorId;
    }

    public EventTrigger editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public EventTrigger createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public EventTrigger name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcessEventRecurrence getRecurrence() {
        return recurrence;
    }

    public EventTrigger recurrence(ProcessEventRecurrence recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    public void setRecurrence(ProcessEventRecurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public EventTrigger disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getEditorName() {
        return editorName;
    }

    public EventTrigger editorName(String editorName) {
        this.editorName = editorName;
        return this;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public LocalDate getFirstStartedAt() {
        return firstStartedAt;
    }

    public EventTrigger firstStartedAt(LocalDate firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
        return this;
    }

    public void setFirstStartedAt(LocalDate firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
    }

    public LocalDate getNextStartAt() {
        return nextStartAt;
    }

    public EventTrigger nextStartAt(LocalDate nextStartAt) {
        this.nextStartAt = nextStartAt;
        return this;
    }

    public void setNextStartAt(LocalDate nextStartAt) {
        this.nextStartAt = nextStartAt;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public EventTrigger startCount(Integer startCount) {
        this.startCount = startCount;
        return this;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Process getProcess() {
        return process;
    }

    public EventTrigger process(Process process) {
        this.process = process;
        return this;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventTrigger)) {
            return false;
        }
        return id != null && id.equals(((EventTrigger) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventTrigger{" +
            "id=" + getId() +
            ", editorId=" + getEditorId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", name='" + getName() + "'" +
            ", recurrence='" + getRecurrence() + "'" +
            ", disabled='" + isDisabled() + "'" +
            ", editorName='" + getEditorName() + "'" +
            ", firstStartedAt='" + getFirstStartedAt() + "'" +
            ", nextStartAt='" + getNextStartAt() + "'" +
            ", startCount=" + getStartCount() +
            "}";
    }
}
