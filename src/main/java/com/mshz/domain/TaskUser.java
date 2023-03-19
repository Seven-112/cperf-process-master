package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mshz.domain.enumeration.TaskUserRole;

/**
 * A TaskUser.
 */
@Entity
@Table(name = "task_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private TaskUserRole role;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_email")
    private String userEmail;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskUsers", allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public TaskUser userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TaskUserRole getRole() {
        return role;
    }

    public TaskUser role(TaskUserRole role) {
        this.role = role;
        return this;
    }

    public void setRole(TaskUserRole role) {
        this.role = role;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public TaskUser userFullName(String userFullName) {
        this.userFullName = userFullName;
        return this;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public TaskUser userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Task getTask() {
        return task;
    }

    public TaskUser task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskUser)) {
            return false;
        }
        return id != null && id.equals(((TaskUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskUser{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", role='" + getRole() + "'" +
            ", userFullName='" + getUserFullName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            "}";
    }
}
