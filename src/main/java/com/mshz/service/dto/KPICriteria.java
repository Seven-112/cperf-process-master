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
 * Criteria class for the {@link com.mshz.domain.KPI} entity. This class is used
 * in {@link com.mshz.web.rest.KPIResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kpis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KPICriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userId;

    private LocalDateFilter dte;

    private LongFilter executed;

    private FloatFilter executedRate;

    private LongFilter executedLate;

    private FloatFilter executedLateRate;

    private LongFilter totalExecuted;

    private FloatFilter totalExecutedRate;

    private LongFilter started;

    private FloatFilter startedRate;

    private LongFilter startedLate;

    private FloatFilter startedLateRate;

    private LongFilter totalStarted;

    private FloatFilter totalStartedRate;

    private LongFilter noStarted;

    private FloatFilter noStartedRate;

    private FloatFilter executionLevel;

    private FloatFilter executionLevelRate;

    public KPICriteria() {
    }

    public KPICriteria(KPICriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.dte = other.dte == null ? null : other.dte.copy();
        this.executed = other.executed == null ? null : other.executed.copy();
        this.executedRate = other.executedRate == null ? null : other.executedRate.copy();
        this.executedLate = other.executedLate == null ? null : other.executedLate.copy();
        this.executedLateRate = other.executedLateRate == null ? null : other.executedLateRate.copy();
        this.totalExecuted = other.totalExecuted == null ? null : other.totalExecuted.copy();
        this.totalExecutedRate = other.totalExecutedRate == null ? null : other.totalExecutedRate.copy();
        this.started = other.started == null ? null : other.started.copy();
        this.startedRate = other.startedRate == null ? null : other.startedRate.copy();
        this.startedLate = other.startedLate == null ? null : other.startedLate.copy();
        this.startedLateRate = other.startedLateRate == null ? null : other.startedLateRate.copy();
        this.totalStarted = other.totalStarted == null ? null : other.totalStarted.copy();
        this.totalStartedRate = other.totalStartedRate == null ? null : other.totalStartedRate.copy();
        this.noStarted = other.noStarted == null ? null : other.noStarted.copy();
        this.noStartedRate = other.noStartedRate == null ? null : other.noStartedRate.copy();
        this.executionLevel = other.executionLevel == null ? null : other.executionLevel.copy();
        this.executionLevelRate = other.executionLevelRate == null ? null : other.executionLevelRate.copy();
    }

    @Override
    public KPICriteria copy() {
        return new KPICriteria(this);
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

    public LocalDateFilter getDte() {
        return dte;
    }

    public void setDte(LocalDateFilter dte) {
        this.dte = dte;
    }

    public LongFilter getExecuted() {
        return executed;
    }

    public void setExecuted(LongFilter executed) {
        this.executed = executed;
    }

    public FloatFilter getExecutedRate() {
        return executedRate;
    }

    public void setExecutedRate(FloatFilter executedRate) {
        this.executedRate = executedRate;
    }

    public LongFilter getExecutedLate() {
        return executedLate;
    }

    public void setExecutedLate(LongFilter executedLate) {
        this.executedLate = executedLate;
    }

    public FloatFilter getExecutedLateRate() {
        return executedLateRate;
    }

    public void setExecutedLateRate(FloatFilter executedLateRate) {
        this.executedLateRate = executedLateRate;
    }

    public LongFilter getTotalExecuted() {
        return totalExecuted;
    }

    public void setTotalExecuted(LongFilter totalExecuted) {
        this.totalExecuted = totalExecuted;
    }

    public FloatFilter getTotalExecutedRate() {
        return totalExecutedRate;
    }

    public void setTotalExecutedRate(FloatFilter totalExecutedRate) {
        this.totalExecutedRate = totalExecutedRate;
    }

    public LongFilter getStarted() {
        return started;
    }

    public void setStarted(LongFilter started) {
        this.started = started;
    }

    public FloatFilter getStartedRate() {
        return startedRate;
    }

    public void setStartedRate(FloatFilter startedRate) {
        this.startedRate = startedRate;
    }

    public LongFilter getStartedLate() {
        return startedLate;
    }

    public void setStartedLate(LongFilter startedLate) {
        this.startedLate = startedLate;
    }

    public FloatFilter getStartedLateRate() {
        return startedLateRate;
    }

    public void setStartedLateRate(FloatFilter startedLateRate) {
        this.startedLateRate = startedLateRate;
    }

    public LongFilter getTotalStarted() {
        return totalStarted;
    }

    public void setTotalStarted(LongFilter totalStarted) {
        this.totalStarted = totalStarted;
    }

    public FloatFilter getTotalStartedRate() {
        return totalStartedRate;
    }

    public void setTotalStartedRate(FloatFilter totalStartedRate) {
        this.totalStartedRate = totalStartedRate;
    }

    public LongFilter getNoStarted() {
        return noStarted;
    }

    public void setNoStarted(LongFilter noStarted) {
        this.noStarted = noStarted;
    }

    public FloatFilter getNoStartedRate() {
        return noStartedRate;
    }

    public void setNoStartedRate(FloatFilter noStartedRate) {
        this.noStartedRate = noStartedRate;
    }

    public FloatFilter getExecutionLevel() {
        return executionLevel;
    }

    public void setExecutionLevel(FloatFilter executionLevel) {
        this.executionLevel = executionLevel;
    }

    public FloatFilter getExecutionLevelRate() {
        return executionLevelRate;
    }

    public void setExecutionLevelRate(FloatFilter executionLevelRate) {
        this.executionLevelRate = executionLevelRate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KPICriteria that = (KPICriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(dte, that.dte) &&
            Objects.equals(executed, that.executed) &&
            Objects.equals(executedRate, that.executedRate) &&
            Objects.equals(executedLate, that.executedLate) &&
            Objects.equals(executedLateRate, that.executedLateRate) &&
            Objects.equals(totalExecuted, that.totalExecuted) &&
            Objects.equals(totalExecutedRate, that.totalExecutedRate) &&
            Objects.equals(started, that.started) &&
            Objects.equals(startedRate, that.startedRate) &&
            Objects.equals(startedLate, that.startedLate) &&
            Objects.equals(startedLateRate, that.startedLateRate) &&
            Objects.equals(totalStarted, that.totalStarted) &&
            Objects.equals(totalStartedRate, that.totalStartedRate) &&
            Objects.equals(noStarted, that.noStarted) &&
            Objects.equals(noStartedRate, that.noStartedRate) &&
            Objects.equals(executionLevel, that.executionLevel) &&
            Objects.equals(executionLevelRate, that.executionLevelRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        dte,
        executed,
        executedRate,
        executedLate,
        executedLateRate,
        totalExecuted,
        totalExecutedRate,
        started,
        startedRate,
        startedLate,
        startedLateRate,
        totalStarted,
        totalStartedRate,
        noStarted,
        noStartedRate,
        executionLevel,
        executionLevelRate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KPICriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (dte != null ? "dte=" + dte + ", " : "") +
                (executed != null ? "executed=" + executed + ", " : "") +
                (executedRate != null ? "executedRate=" + executedRate + ", " : "") +
                (executedLate != null ? "executedLate=" + executedLate + ", " : "") +
                (executedLateRate != null ? "executedLateRate=" + executedLateRate + ", " : "") +
                (totalExecuted != null ? "totalExecuted=" + totalExecuted + ", " : "") +
                (totalExecutedRate != null ? "totalExecutedRate=" + totalExecutedRate + ", " : "") +
                (started != null ? "started=" + started + ", " : "") +
                (startedRate != null ? "startedRate=" + startedRate + ", " : "") +
                (startedLate != null ? "startedLate=" + startedLate + ", " : "") +
                (startedLateRate != null ? "startedLateRate=" + startedLateRate + ", " : "") +
                (totalStarted != null ? "totalStarted=" + totalStarted + ", " : "") +
                (totalStartedRate != null ? "totalStartedRate=" + totalStartedRate + ", " : "") +
                (noStarted != null ? "noStarted=" + noStarted + ", " : "") +
                (noStartedRate != null ? "noStartedRate=" + noStartedRate + ", " : "") +
                (executionLevel != null ? "executionLevel=" + executionLevel + ", " : "") +
                (executionLevelRate != null ? "executionLevelRate=" + executionLevelRate + ", " : "") +
            "}";
    }

}
