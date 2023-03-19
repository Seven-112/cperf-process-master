package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mshz.domain.enumeration.TaskFileType;

/**
 * A TaskFile.
 */
@Entity
@Table(name = "task_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskFileType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskFiles", allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public TaskFile fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getDescription() {
        return description;
    }

    public TaskFile description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskFileType getType() {
        return type;
    }

    public TaskFile type(TaskFileType type) {
        this.type = type;
        return this;
    }

    public void setType(TaskFileType type) {
        this.type = type;
    }

    public Task getTask() {
        return task;
    }

    public TaskFile task(Task task) {
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
        if (!(o instanceof TaskFile)) {
            return false;
        }
        return id != null && id.equals(((TaskFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskFile{" +
            "id=" + getId() +
            ", fileId=" + getFileId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
