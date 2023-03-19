package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TaskStatusTrakingFile.
 */
@Entity
@Table(name = "task_status_traking_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskStatusTrakingFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskStatusTrakingFiles", allowSetters = true)
    private TaskStatusTraking track;

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

    public TaskStatusTrakingFile fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public TaskStatusTrakingFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TaskStatusTraking getTrack() {
        return track;
    }

    public TaskStatusTrakingFile track(TaskStatusTraking taskStatusTraking) {
        this.track = taskStatusTraking;
        return this;
    }

    public void setTrack(TaskStatusTraking taskStatusTraking) {
        this.track = taskStatusTraking;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskStatusTrakingFile)) {
            return false;
        }
        return id != null && id.equals(((TaskStatusTrakingFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskStatusTrakingFile{" +
            "id=" + getId() +
            ", fileId=" + getFileId() +
            ", fileName='" + getFileName() + "'" +
            "}";
    }
}
