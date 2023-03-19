package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.TaskUserRole;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mshz.domain.TaskUser} entity. This class is used
 * in {@link com.mshz.web.rest.TaskUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TaskUserRole
     */
    public static class TaskUserRoleFilter extends Filter<TaskUserRole> {

        public TaskUserRoleFilter() {
        }

        public TaskUserRoleFilter(TaskUserRoleFilter filter) {
            super(filter);
        }

        @Override
        public TaskUserRoleFilter copy() {
            return new TaskUserRoleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userId;

    private TaskUserRoleFilter role;

    private StringFilter userFullName;

    private StringFilter userEmail;

    private LongFilter taskId;

    public TaskUserCriteria() {
    }

    public TaskUserCriteria(TaskUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.userFullName = other.userFullName == null ? null : other.userFullName.copy();
        this.userEmail = other.userEmail == null ? null : other.userEmail.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public TaskUserCriteria copy() {
        return new TaskUserCriteria(this);
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

    public TaskUserRoleFilter getRole() {
        return role;
    }

    public void setRole(TaskUserRoleFilter role) {
        this.role = role;
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

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskUserCriteria that = (TaskUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(role, that.role) &&
            Objects.equals(userFullName, that.userFullName) &&
            Objects.equals(userEmail, that.userEmail) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        role,
        userFullName,
        userEmail,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (userFullName != null ? "userFullName=" + userFullName + ", " : "") +
                (userEmail != null ? "userEmail=" + userEmail + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
