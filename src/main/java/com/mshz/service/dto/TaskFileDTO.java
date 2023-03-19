package com.mshz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.mshz.domain.enumeration.TaskFileType;

/**
 * A DTO for the {@link com.mshz.domain.TaskFile} entity.
 */
public class TaskFileDTO implements Serializable {
    
    private Long id;

    private Long fileId;

    private String description;

    private TaskFileType type;


    private Long taskId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskFileType getType() {
        return type;
    }

    public void setType(TaskFileType type) {
        this.type = type;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskFileDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskFileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskFileDTO{" +
            "id=" + getId() +
            ", fileId=" + getFileId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
