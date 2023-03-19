package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mshz.domain.CondNode} entity. This class is used
 * in {@link com.mshz.web.rest.CondNodeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cond-nodes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CondNodeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter logigramPosX;

    private DoubleFilter logigramPosY;

    private LongFilter processId;

    private LongFilter modelId;

    public CondNodeCriteria() {
    }

    public CondNodeCriteria(CondNodeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.logigramPosX = other.logigramPosX == null ? null : other.logigramPosX.copy();
        this.logigramPosY = other.logigramPosY == null ? null : other.logigramPosY.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.modelId = other.modelId == null ? null : other.modelId.copy();
    }

    @Override
    public CondNodeCriteria copy() {
        return new CondNodeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getLogigramPosX() {
        return logigramPosX;
    }

    public void setLogigramPosX(DoubleFilter logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public DoubleFilter getLogigramPosY() {
        return logigramPosY;
    }

    public void setLogigramPosY(DoubleFilter logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public LongFilter getModelId() {
        return modelId;
    }

    public void setModelId(LongFilter modelId) {
        this.modelId = modelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CondNodeCriteria that = (CondNodeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(logigramPosX, that.logigramPosX) &&
            Objects.equals(logigramPosY, that.logigramPosY) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(modelId, that.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        logigramPosX,
        logigramPosY,
        processId,
        modelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CondNodeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (logigramPosX != null ? "logigramPosX=" + logigramPosX + ", " : "") +
                (logigramPosY != null ? "logigramPosY=" + logigramPosY + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (modelId != null ? "modelId=" + modelId + ", " : "") +
            "}";
    }

}
