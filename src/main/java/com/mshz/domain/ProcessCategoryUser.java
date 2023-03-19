package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProcessCategoryUser.
 */
@Entity
@Table(name = "process_category_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessCategoryUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "process_id")
    private Long processId;

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

    public ProcessCategoryUser userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public ProcessCategoryUser userFullName(String userFullName) {
        this.userFullName = userFullName;
        return this;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProcessCategoryUser userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ProcessCategoryUser categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProcessId() {
        return processId;
    }

    public ProcessCategoryUser processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessCategoryUser)) {
            return false;
        }
        return id != null && id.equals(((ProcessCategoryUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessCategoryUser{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", userFullName='" + getUserFullName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", categoryId=" + getCategoryId() +
            ", processId=" + getProcessId() +
            "}";
    }
}
