package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.mshz.domain.enumeration.JustifcationReason;

/**
 * A Justification.
 */
@Entity
@Table(name = "justification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Justification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "process_id")
    private Long processId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private JustifcationReason reason;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "editor_id")
    private Long editorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Justification content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFileId() {
        return fileId;
    }

    public Justification fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Justification taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getProcessId() {
        return processId;
    }

    public Justification processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public JustifcationReason getReason() {
        return reason;
    }

    public Justification reason(JustifcationReason reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(JustifcationReason reason) {
        this.reason = reason;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public Justification accepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Long getEditorId() {
        return editorId;
    }

    public Justification editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Justification)) {
            return false;
        }
        return id != null && id.equals(((Justification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Justification{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", fileId=" + getFileId() +
            ", taskId=" + getTaskId() +
            ", processId=" + getProcessId() +
            ", reason='" + getReason() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", editorId=" + getEditorId() +
            "}";
    }
}
