package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ItemCheckJustification.
 */
@Entity
@Table(name = "item_check_justification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemCheckJustification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "checked")
    private Boolean checked;

    @NotNull
    @Column(name = "task_item_id", nullable = false)
    private Long taskItemId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "justification")
    private String justification;

    @Column(name = "date")
    private Instant date;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isChecked() {
        return checked;
    }

    public ItemCheckJustification checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getTaskItemId() {
        return taskItemId;
    }

    public ItemCheckJustification taskItemId(Long taskItemId) {
        this.taskItemId = taskItemId;
        return this;
    }

    public void setTaskItemId(Long taskItemId) {
        this.taskItemId = taskItemId;
    }

    public String getJustification() {
        return justification;
    }

    public ItemCheckJustification justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Instant getDate() {
        return date;
    }

    public ItemCheckJustification date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemCheckJustification)) {
            return false;
        }
        return id != null && id.equals(((ItemCheckJustification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemCheckJustification{" +
            "id=" + getId() +
            ", checked='" + isChecked() + "'" +
            ", taskItemId=" + getTaskItemId() +
            ", justification='" + getJustification() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
