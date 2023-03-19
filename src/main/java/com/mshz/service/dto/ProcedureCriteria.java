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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mshz.domain.Procedure} entity. This class is used
 * in {@link com.mshz.web.rest.ProcedureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /procedures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcedureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter fileId;

    private LocalDateFilter storeAt;

    public ProcedureCriteria() {
    }

    public ProcedureCriteria(ProcedureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.storeAt = other.storeAt == null ? null : other.storeAt.copy();
    }

    @Override
    public ProcedureCriteria copy() {
        return new ProcedureCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
    }

    public LocalDateFilter getStoreAt() {
        return storeAt;
    }

    public void setStoreAt(LocalDateFilter storeAt) {
        this.storeAt = storeAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcedureCriteria that = (ProcedureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(storeAt, that.storeAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        fileId,
        storeAt
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcedureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (storeAt != null ? "storeAt=" + storeAt + ", " : "") +
            "}";
    }

}
