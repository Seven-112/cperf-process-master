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
 * Criteria class for the {@link com.mshz.domain.EdgeInfo} entity. This class is used
 * in {@link com.mshz.web.rest.EdgeInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /edge-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EdgeInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter source;

    private StringFilter target;

    private StringFilter sourceHandle;

    private StringFilter targetHandle;

    private LongFilter processId;

    private BooleanFilter valid;

    public EdgeInfoCriteria() {
    }

    public EdgeInfoCriteria(EdgeInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.target = other.target == null ? null : other.target.copy();
        this.sourceHandle = other.sourceHandle == null ? null : other.sourceHandle.copy();
        this.targetHandle = other.targetHandle == null ? null : other.targetHandle.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
    }

    @Override
    public EdgeInfoCriteria copy() {
        return new EdgeInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getTarget() {
        return target;
    }

    public void setTarget(StringFilter target) {
        this.target = target;
    }

    public StringFilter getSourceHandle() {
        return sourceHandle;
    }

    public void setSourceHandle(StringFilter sourceHandle) {
        this.sourceHandle = sourceHandle;
    }

    public StringFilter getTargetHandle() {
        return targetHandle;
    }

    public void setTargetHandle(StringFilter targetHandle) {
        this.targetHandle = targetHandle;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EdgeInfoCriteria that = (EdgeInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(source, that.source) &&
            Objects.equals(target, that.target) &&
            Objects.equals(sourceHandle, that.sourceHandle) &&
            Objects.equals(targetHandle, that.targetHandle) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(valid, that.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        source,
        target,
        sourceHandle,
        targetHandle,
        processId,
        valid
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EdgeInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (target != null ? "target=" + target + ", " : "") +
                (sourceHandle != null ? "sourceHandle=" + sourceHandle + ", " : "") +
                (targetHandle != null ? "targetHandle=" + targetHandle + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
            "}";
    }

}
