package com.mshz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.ProcessPriority;
import com.mshz.domain.enumeration.TaskType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mshz.domain.Task} entity. This class is used
 * in {@link com.mshz.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TaskStatus
     */
    public static class TaskStatusFilter extends Filter<TaskStatus> {

        public TaskStatusFilter() {
        }

        public TaskStatusFilter(TaskStatusFilter filter) {
            super(filter);
        }

        @Override
        public TaskStatusFilter copy() {
            return new TaskStatusFilter(this);
        }

    }
    /**
     * Class for filtering ProcessPriority
     */
    public static class ProcessPriorityFilter extends Filter<ProcessPriority> {

        public ProcessPriorityFilter() {
        }

        public ProcessPriorityFilter(ProcessPriorityFilter filter) {
            super(filter);
        }

        @Override
        public ProcessPriorityFilter copy() {
            return new ProcessPriorityFilter(this);
        }

    }
    /**
     * Class for filtering TaskType
     */
    public static class TaskTypeFilter extends Filter<TaskType> {

        public TaskTypeFilter() {
        }

        public TaskTypeFilter(TaskTypeFilter filter) {
            super(filter);
        }

        @Override
        public TaskTypeFilter copy() {
            return new TaskTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter nbMinuites;

    private IntegerFilter nbHours;

    private IntegerFilter nbDays;

    private IntegerFilter nbMonths;

    private IntegerFilter nbYears;

    private InstantFilter startAt;

    private TaskStatusFilter status;

    private ProcessPriorityFilter priorityLevel;

    private TaskTypeFilter type;

    private BooleanFilter valid;

    private InstantFilter finishAt;

    private BooleanFilter startWithProcess;

    private LongFilter processId;

    private LongFilter parentId;

    private LongFilter taskModelId;

    private InstantFilter pauseAt;

    private IntegerFilter nbPause;

    private DoubleFilter logigramPosX;

    private DoubleFilter logigramPosY;

    private LongFilter groupId;

    private LongFilter riskId;

    private BooleanFilter manualMode;

    private LocalDateFilter sheduledStartAt;

    private IntegerFilter sheduledStartHour;

    private IntegerFilter sheduledStartMinute;

    private BooleanFilter checked;

    private LongFilter currentPauseHistoryId;

    private BooleanFilter exceceed;

    private LongFilter startupTaskId;

    public TaskCriteria() {
    }

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nbMinuites = other.nbMinuites == null ? null : other.nbMinuites.copy();
        this.nbHours = other.nbHours == null ? null : other.nbHours.copy();
        this.nbDays = other.nbDays == null ? null : other.nbDays.copy();
        this.nbMonths = other.nbMonths == null ? null : other.nbMonths.copy();
        this.nbYears = other.nbYears == null ? null : other.nbYears.copy();
        this.startAt = other.startAt == null ? null : other.startAt.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.priorityLevel = other.priorityLevel == null ? null : other.priorityLevel.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.finishAt = other.finishAt == null ? null : other.finishAt.copy();
        this.startWithProcess = other.startWithProcess == null ? null : other.startWithProcess.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.taskModelId = other.taskModelId == null ? null : other.taskModelId.copy();
        this.pauseAt = other.pauseAt == null ? null : other.pauseAt.copy();
        this.nbPause = other.nbPause == null ? null : other.nbPause.copy();
        this.logigramPosX = other.logigramPosX == null ? null : other.logigramPosX.copy();
        this.logigramPosY = other.logigramPosY == null ? null : other.logigramPosY.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
        this.riskId = other.riskId == null ? null : other.riskId.copy();
        this.manualMode = other.manualMode == null ? null : other.manualMode.copy();
        this.sheduledStartAt = other.sheduledStartAt == null ? null : other.sheduledStartAt.copy();
        this.sheduledStartHour = other.sheduledStartHour == null ? null : other.sheduledStartHour.copy();
        this.sheduledStartMinute = other.sheduledStartMinute == null ? null : other.sheduledStartMinute.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
        this.currentPauseHistoryId = other.currentPauseHistoryId == null ? null : other.currentPauseHistoryId.copy();
        this.exceceed = other.exceceed == null ? null : other.exceceed.copy();
        this.startupTaskId = other.startupTaskId == null ? null : other.startupTaskId.copy();
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
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

    public IntegerFilter getNbMinuites() {
        return nbMinuites;
    }

    public void setNbMinuites(IntegerFilter nbMinuites) {
        this.nbMinuites = nbMinuites;
    }

    public IntegerFilter getNbHours() {
        return nbHours;
    }

    public void setNbHours(IntegerFilter nbHours) {
        this.nbHours = nbHours;
    }

    public IntegerFilter getNbDays() {
        return nbDays;
    }

    public void setNbDays(IntegerFilter nbDays) {
        this.nbDays = nbDays;
    }

    public IntegerFilter getNbMonths() {
        return nbMonths;
    }

    public void setNbMonths(IntegerFilter nbMonths) {
        this.nbMonths = nbMonths;
    }

    public IntegerFilter getNbYears() {
        return nbYears;
    }

    public void setNbYears(IntegerFilter nbYears) {
        this.nbYears = nbYears;
    }

    public InstantFilter getStartAt() {
        return startAt;
    }

    public void setStartAt(InstantFilter startAt) {
        this.startAt = startAt;
    }

    public TaskStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TaskStatusFilter status) {
        this.status = status;
    }

    public ProcessPriorityFilter getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(ProcessPriorityFilter priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public TaskTypeFilter getType() {
        return type;
    }

    public void setType(TaskTypeFilter type) {
        this.type = type;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }

    public InstantFilter getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(InstantFilter finishAt) {
        this.finishAt = finishAt;
    }

    public BooleanFilter getStartWithProcess() {
        return startWithProcess;
    }

    public void setStartWithProcess(BooleanFilter startWithProcess) {
        this.startWithProcess = startWithProcess;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getTaskModelId() {
        return taskModelId;
    }

    public void setTaskModelId(LongFilter taskModelId) {
        this.taskModelId = taskModelId;
    }

    public InstantFilter getPauseAt() {
        return pauseAt;
    }

    public void setPauseAt(InstantFilter pauseAt) {
        this.pauseAt = pauseAt;
    }

    public IntegerFilter getNbPause() {
        return nbPause;
    }

    public void setNbPause(IntegerFilter nbPause) {
        this.nbPause = nbPause;
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

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }

    public LongFilter getRiskId() {
        return riskId;
    }

    public void setRiskId(LongFilter riskId) {
        this.riskId = riskId;
    }

    public BooleanFilter getManualMode() {
        return manualMode;
    }

    public void setManualMode(BooleanFilter manualMode) {
        this.manualMode = manualMode;
    }

    public LocalDateFilter getSheduledStartAt() {
        return sheduledStartAt;
    }

    public void setSheduledStartAt(LocalDateFilter sheduledStartAt) {
        this.sheduledStartAt = sheduledStartAt;
    }

    public IntegerFilter getSheduledStartHour() {
        return sheduledStartHour;
    }

    public void setSheduledStartHour(IntegerFilter sheduledStartHour) {
        this.sheduledStartHour = sheduledStartHour;
    }

    public IntegerFilter getSheduledStartMinute() {
        return sheduledStartMinute;
    }

    public void setSheduledStartMinute(IntegerFilter sheduledStartMinute) {
        this.sheduledStartMinute = sheduledStartMinute;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public LongFilter getCurrentPauseHistoryId() {
        return currentPauseHistoryId;
    }

    public void setCurrentPauseHistoryId(LongFilter currentPauseHistoryId) {
        this.currentPauseHistoryId = currentPauseHistoryId;
    }

    public BooleanFilter getExceceed() {
        return exceceed;
    }

    public void setExceceed(BooleanFilter exceceed) {
        this.exceceed = exceceed;
    }

    public LongFilter getStartupTaskId() {
        return startupTaskId;
    }

    public void setStartupTaskId(LongFilter startupTaskId) {
        this.startupTaskId = startupTaskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nbMinuites, that.nbMinuites) &&
            Objects.equals(nbHours, that.nbHours) &&
            Objects.equals(nbDays, that.nbDays) &&
            Objects.equals(nbMonths, that.nbMonths) &&
            Objects.equals(nbYears, that.nbYears) &&
            Objects.equals(startAt, that.startAt) &&
            Objects.equals(status, that.status) &&
            Objects.equals(priorityLevel, that.priorityLevel) &&
            Objects.equals(type, that.type) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(finishAt, that.finishAt) &&
            Objects.equals(startWithProcess, that.startWithProcess) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(taskModelId, that.taskModelId) &&
            Objects.equals(pauseAt, that.pauseAt) &&
            Objects.equals(nbPause, that.nbPause) &&
            Objects.equals(logigramPosX, that.logigramPosX) &&
            Objects.equals(logigramPosY, that.logigramPosY) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(riskId, that.riskId) &&
            Objects.equals(manualMode, that.manualMode) &&
            Objects.equals(sheduledStartAt, that.sheduledStartAt) &&
            Objects.equals(sheduledStartHour, that.sheduledStartHour) &&
            Objects.equals(sheduledStartMinute, that.sheduledStartMinute) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(currentPauseHistoryId, that.currentPauseHistoryId) &&
            Objects.equals(exceceed, that.exceceed) &&
            Objects.equals(startupTaskId, that.startupTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        nbMinuites,
        nbHours,
        nbDays,
        nbMonths,
        nbYears,
        startAt,
        status,
        priorityLevel,
        type,
        valid,
        finishAt,
        startWithProcess,
        processId,
        parentId,
        taskModelId,
        pauseAt,
        nbPause,
        logigramPosX,
        logigramPosY,
        groupId,
        riskId,
        manualMode,
        sheduledStartAt,
        sheduledStartHour,
        sheduledStartMinute,
        checked,
        currentPauseHistoryId,
        exceceed,
        startupTaskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (nbMinuites != null ? "nbMinuites=" + nbMinuites + ", " : "") +
                (nbHours != null ? "nbHours=" + nbHours + ", " : "") +
                (nbDays != null ? "nbDays=" + nbDays + ", " : "") +
                (nbMonths != null ? "nbMonths=" + nbMonths + ", " : "") +
                (nbYears != null ? "nbYears=" + nbYears + ", " : "") +
                (startAt != null ? "startAt=" + startAt + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (priorityLevel != null ? "priorityLevel=" + priorityLevel + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (finishAt != null ? "finishAt=" + finishAt + ", " : "") +
                (startWithProcess != null ? "startWithProcess=" + startWithProcess + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (taskModelId != null ? "taskModelId=" + taskModelId + ", " : "") +
                (pauseAt != null ? "pauseAt=" + pauseAt + ", " : "") +
                (nbPause != null ? "nbPause=" + nbPause + ", " : "") +
                (logigramPosX != null ? "logigramPosX=" + logigramPosX + ", " : "") +
                (logigramPosY != null ? "logigramPosY=" + logigramPosY + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (riskId != null ? "riskId=" + riskId + ", " : "") +
                (manualMode != null ? "manualMode=" + manualMode + ", " : "") +
                (sheduledStartAt != null ? "sheduledStartAt=" + sheduledStartAt + ", " : "") +
                (sheduledStartHour != null ? "sheduledStartHour=" + sheduledStartHour + ", " : "") +
                (sheduledStartMinute != null ? "sheduledStartMinute=" + sheduledStartMinute + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
                (currentPauseHistoryId != null ? "currentPauseHistoryId=" + currentPauseHistoryId + ", " : "") +
                (exceceed != null ? "exceceed=" + exceceed + ", " : "") +
                (startupTaskId != null ? "startupTaskId=" + startupTaskId + ", " : "") +
            "}";
    }

}
