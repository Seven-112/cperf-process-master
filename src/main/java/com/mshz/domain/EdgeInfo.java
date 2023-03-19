package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EdgeInfo.
 */
@Entity
@Table(name = "edge_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EdgeInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "target")
    private String target;

    @Column(name = "source_handle")
    private String sourceHandle;

    @Column(name = "target_handle")
    private String targetHandle;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "valid")
    private Boolean valid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public EdgeInfo() {
    }

    public EdgeInfo(Long id, String source, String target, String sourceHandle, String targetHandle, Long processId, Boolean valid) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.sourceHandle = sourceHandle;
        this.targetHandle = targetHandle;
        this.processId = processId;
        this.valid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public EdgeInfo source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public EdgeInfo target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSourceHandle() {
        return sourceHandle;
    }

    public EdgeInfo sourceHandle(String sourceHandle) {
        this.sourceHandle = sourceHandle;
        return this;
    }

    public void setSourceHandle(String sourceHandle) {
        this.sourceHandle = sourceHandle;
    }

    public String getTargetHandle() {
        return targetHandle;
    }

    public EdgeInfo targetHandle(String targetHandle) {
        this.targetHandle = targetHandle;
        return this;
    }

    public void setTargetHandle(String targetHandle) {
        this.targetHandle = targetHandle;
    }

    public Long getProcessId() {
        return processId;
    }

    public EdgeInfo processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Boolean isValid() {
        return valid;
    }

    public EdgeInfo valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EdgeInfo)) {
            return false;
        }
        return id != null && id.equals(((EdgeInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EdgeInfo{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", target='" + getTarget() + "'" +
            ", sourceHandle='" + getSourceHandle() + "'" +
            ", targetHandle='" + getTargetHandle() + "'" +
            ", processId=" + getProcessId() +
            ", valid='" + isValid() + "'" +
            "}";
    }

    public EdgeInfo clone() throws CloneNotSupportedException{
        return new EdgeInfo(this.id, this.source, this.target, this.sourceHandle, this.targetHandle, this.processId, this.valid);
    }
}
