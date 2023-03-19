package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CondNode.
 */
@Entity
@Table(name = "cond_node")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CondNode implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "logigram_pos_x")
    private Double logigramPosX;

    @Column(name = "logigram_pos_y")
    private Double logigramPosY;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "model_id")
    private Long modelId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public CondNode() {
    }

    public CondNode(Long id, Double logigramPosX, Double logigramPosY, Long processId, Long modelId) {
        this.id = id;
        this.logigramPosX = logigramPosX;
        this.logigramPosY = logigramPosY;
        this.processId = processId;
        this.modelId = modelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLogigramPosX() {
        return logigramPosX;
    }

    public CondNode logigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
        return this;
    }

    public void setLogigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public Double getLogigramPosY() {
        return logigramPosY;
    }

    public CondNode logigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
        return this;
    }

    public void setLogigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public Long getProcessId() {
        return processId;
    }

    public CondNode processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getModelId() {
        return modelId;
    }

    public CondNode modelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CondNode)) {
            return false;
        }
        return id != null && id.equals(((CondNode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CondNode{" +
            "id=" + getId() +
            ", logigramPosX=" + getLogigramPosX() +
            ", logigramPosY=" + getLogigramPosY() +
            ", processId=" + getProcessId() +
            ", modelId=" + getModelId() +
            "}";
    }

    public CondNode clone() throws CloneNotSupportedException{
        return new CondNode(this.id, this.logigramPosX, this.logigramPosY, this.processId, this.modelId);
    }
}
