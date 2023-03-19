package com.mshz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A KPI.
 */
@Entity
@Table(name = "kpis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KPI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dte")
    private LocalDate dte;

    @Column(name = "executed")
    private Long executed;

    @Column(name = "executed_rate")
    private Float executedRate;

    @Column(name = "executed_late")
    private Long executedLate;

    @Column(name = "executed_late_rate")
    private Float executedLateRate;

    @Column(name = "total_executed")
    private Long totalExecuted;

    @Column(name = "total_executed_rate")
    private Float totalExecutedRate;

    @Column(name = "started")
    private Long started;

    @Column(name = "started_rate")
    private Float startedRate;

    @Column(name = "started_late")
    private Long startedLate;

    @Column(name = "started_late_rate")
    private Float startedLateRate;

    @Column(name = "total_started")
    private Long totalStarted;

    @Column(name = "total_started_rate")
    private Float totalStartedRate;

    @Column(name = "no_started")
    private Long noStarted;

    @Column(name = "no_started_rate")
    private Float noStartedRate;

    @Column(name = "execution_level")
    private Float executionLevel;

    @Column(name = "execution_level_rate")
    private Float executionLevelRate;

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

    public KPI userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDte() {
        return dte;
    }

    public KPI dte(LocalDate dte) {
        this.dte = dte;
        return this;
    }

    public void setDte(LocalDate dte) {
        this.dte = dte;
    }

    public Long getExecuted() {
        return executed;
    }

    public KPI executed(Long executed) {
        this.executed = executed;
        return this;
    }

    public void setExecuted(Long executed) {
        this.executed = executed;
    }

    public Float getExecutedRate() {
        return executedRate;
    }

    public KPI executedRate(Float executedRate) {
        this.executedRate = executedRate;
        return this;
    }

    public void setExecutedRate(Float executedRate) {
        this.executedRate = executedRate;
    }

    public Long getExecutedLate() {
        return executedLate;
    }

    public KPI executedLate(Long executedLate) {
        this.executedLate = executedLate;
        return this;
    }

    public void setExecutedLate(Long executedLate) {
        this.executedLate = executedLate;
    }

    public Float getExecutedLateRate() {
        return executedLateRate;
    }

    public KPI executedLateRate(Float executedLateRate) {
        this.executedLateRate = executedLateRate;
        return this;
    }

    public void setExecutedLateRate(Float executedLateRate) {
        this.executedLateRate = executedLateRate;
    }

    public Long getTotalExecuted() {
        return totalExecuted;
    }

    public KPI totalExecuted(Long totalExecuted) {
        this.totalExecuted = totalExecuted;
        return this;
    }

    public void setTotalExecuted(Long totalExecuted) {
        this.totalExecuted = totalExecuted;
    }

    public Float getTotalExecutedRate() {
        return totalExecutedRate;
    }

    public KPI totalExecutedRate(Float totalExecutedRate) {
        this.totalExecutedRate = totalExecutedRate;
        return this;
    }

    public void setTotalExecutedRate(Float totalExecutedRate) {
        this.totalExecutedRate = totalExecutedRate;
    }

    public Long getStarted() {
        return started;
    }

    public KPI started(Long started) {
        this.started = started;
        return this;
    }

    public void setStarted(Long started) {
        this.started = started;
    }

    public Float getStartedRate() {
        return startedRate;
    }

    public KPI startedRate(Float startedRate) {
        this.startedRate = startedRate;
        return this;
    }

    public void setStartedRate(Float startedRate) {
        this.startedRate = startedRate;
    }

    public Long getStartedLate() {
        return startedLate;
    }

    public KPI startedLate(Long startedLate) {
        this.startedLate = startedLate;
        return this;
    }

    public void setStartedLate(Long startedLate) {
        this.startedLate = startedLate;
    }

    public Float getStartedLateRate() {
        return startedLateRate;
    }

    public KPI startedLateRate(Float startedLateRate) {
        this.startedLateRate = startedLateRate;
        return this;
    }

    public void setStartedLateRate(Float startedLateRate) {
        this.startedLateRate = startedLateRate;
    }

    public Long getTotalStarted() {
        return totalStarted;
    }

    public KPI totalStarted(Long totalStarted) {
        this.totalStarted = totalStarted;
        return this;
    }

    public void setTotalStarted(Long totalStarted) {
        this.totalStarted = totalStarted;
    }

    public Float getTotalStartedRate() {
        return totalStartedRate;
    }

    public KPI totalStartedRate(Float totalStartedRate) {
        this.totalStartedRate = totalStartedRate;
        return this;
    }

    public void setTotalStartedRate(Float totalStartedRate) {
        this.totalStartedRate = totalStartedRate;
    }

    public Long getNoStarted() {
        return noStarted;
    }

    public KPI noStarted(Long noStarted) {
        this.noStarted = noStarted;
        return this;
    }

    public void setNoStarted(Long noStarted) {
        this.noStarted = noStarted;
    }

    public Float getNoStartedRate() {
        return noStartedRate;
    }

    public KPI noStartedRate(Float noStartedRate) {
        this.noStartedRate = noStartedRate;
        return this;
    }

    public void setNoStartedRate(Float noStartedRate) {
        this.noStartedRate = noStartedRate;
    }

    public Float getExecutionLevel() {
        return executionLevel;
    }

    public KPI executionLevel(Float executionLevel) {
        this.executionLevel = executionLevel;
        return this;
    }

    public void setExecutionLevel(Float executionLevel) {
        this.executionLevel = executionLevel;
    }

    public Float getExecutionLevelRate() {
        return executionLevelRate;
    }

    public KPI executionLevelRate(Float executionLevelRate) {
        this.executionLevelRate = executionLevelRate;
        return this;
    }

    public void setExecutionLevelRate(Float executionLevelRate) {
        this.executionLevelRate = executionLevelRate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KPI)) {
            return false;
        }
        return id != null && id.equals(((KPI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KPI{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", dte='" + getDte() + "'" +
            ", executed=" + getExecuted() +
            ", executedRate=" + getExecutedRate() +
            ", executedLate=" + getExecutedLate() +
            ", executedLateRate=" + getExecutedLateRate() +
            ", totalExecuted=" + getTotalExecuted() +
            ", totalExecutedRate=" + getTotalExecutedRate() +
            ", started=" + getStarted() +
            ", startedRate=" + getStartedRate() +
            ", startedLate=" + getStartedLate() +
            ", startedLateRate=" + getStartedLateRate() +
            ", totalStarted=" + getTotalStarted() +
            ", totalStartedRate=" + getTotalStartedRate() +
            ", noStarted=" + getNoStarted() +
            ", noStartedRate=" + getNoStartedRate() +
            ", executionLevel=" + getExecutionLevel() +
            ", executionLevelRate=" + getExecutionLevelRate() +
            "}";
    }
}
