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
 * Criteria class for the {@link com.mshz.domain.ProcessCategoryUser} entity. This class is used
 * in {@link com.mshz.web.rest.ProcessCategoryUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /process-category-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessCategoryUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userId;

    private StringFilter userFullName;

    private StringFilter userEmail;

    private LongFilter categoryId;

    private LongFilter processId;

    public ProcessCategoryUserCriteria() {
    }

    public ProcessCategoryUserCriteria(ProcessCategoryUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userFullName = other.userFullName == null ? null : other.userFullName.copy();
        this.userEmail = other.userEmail == null ? null : other.userEmail.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
    }

    @Override
    public ProcessCategoryUserCriteria copy() {
        return new ProcessCategoryUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(StringFilter userFullName) {
        this.userFullName = userFullName;
    }

    public StringFilter getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(StringFilter userEmail) {
        this.userEmail = userEmail;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessCategoryUserCriteria that = (ProcessCategoryUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userFullName, that.userFullName) &&
            Objects.equals(userEmail, that.userEmail) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(processId, that.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        userFullName,
        userEmail,
        categoryId,
        processId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessCategoryUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userFullName != null ? "userFullName=" + userFullName + ", " : "") +
                (userEmail != null ? "userEmail=" + userEmail + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
            "}";
    }

}
