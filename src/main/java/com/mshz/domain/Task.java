package com.mshz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.mshz.domain.enumeration.TaskStatus;

import com.mshz.domain.enumeration.ProcessPriority;

import com.mshz.domain.enumeration.TaskType;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "nb_minuites")
    private Integer nbMinuites;

    @Column(name = "nb_hours")
    private Integer nbHours;

    @Column(name = "nb_days")
    private Integer nbDays;

    @Column(name = "nb_months")
    private Integer nbMonths;

    @Column(name = "nb_years")
    private Integer nbYears;

    @Column(name = "start_at")
    private Instant startAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    private ProcessPriority priorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "finish_at")
    private Instant finishAt;

    @Column(name = "start_with_process")
    private Boolean startWithProcess;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "task_model_id")
    private Long taskModelId;

    @Column(name = "pause_at")
    private Instant pauseAt;

    @Column(name = "nb_pause")
    private Integer nbPause;

    @Column(name = "logigram_pos_x")
    private Double logigramPosX;

    @Column(name = "logigram_pos_y")
    private Double logigramPosY;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "risk_id")
    private Long riskId;

    @Column(name = "manual_mode")
    private Boolean manualMode;

    @Column(name = "sheduled_start_at")
    private LocalDate sheduledStartAt;

    @Column(name = "sheduled_start_hour")
    private Integer sheduledStartHour;

    @Column(name = "sheduled_start_minute")
    private Integer sheduledStartMinute;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "current_pause_history_id")
    private Long currentPauseHistoryId;

    @Column(name = "exceceed")
    private Boolean exceceed;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Task startupTask;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbMinuites() {
        return nbMinuites;
    }

    public Task nbMinuites(Integer nbMinuites) {
        this.nbMinuites = nbMinuites;
        return this;
    }

    public void setNbMinuites(Integer nbMinuites) {
        this.nbMinuites = nbMinuites;
    }

    public Integer getNbHours() {
        return nbHours;
    }

    public Task nbHours(Integer nbHours) {
        this.nbHours = nbHours;
        return this;
    }

    public void setNbHours(Integer nbHours) {
        this.nbHours = nbHours;
    }

    public Integer getNbDays() {
        return nbDays;
    }

    public Task nbDays(Integer nbDays) {
        this.nbDays = nbDays;
        return this;
    }

    public void setNbDays(Integer nbDays) {
        this.nbDays = nbDays;
    }

    public Integer getNbMonths() {
        return nbMonths;
    }

    public Task nbMonths(Integer nbMonths) {
        this.nbMonths = nbMonths;
        return this;
    }

    public void setNbMonths(Integer nbMonths) {
        this.nbMonths = nbMonths;
    }

    public Integer getNbYears() {
        return nbYears;
    }

    public Task nbYears(Integer nbYears) {
        this.nbYears = nbYears;
        return this;
    }

    public void setNbYears(Integer nbYears) {
        this.nbYears = nbYears;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Task startAt(Instant startAt) {
        this.startAt = startAt;
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public ProcessPriority getPriorityLevel() {
        return priorityLevel;
    }

    public Task priorityLevel(ProcessPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public void setPriorityLevel(ProcessPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public TaskType getType() {
        return type;
    }

    public Task type(TaskType type) {
        this.type = type;
        return this;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Boolean isValid() {
        return valid;
    }

    public Task valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getFinishAt() {
        return finishAt;
    }

    public Task finishAt(Instant finishAt) {
        this.finishAt = finishAt;
        return this;
    }

    public void setFinishAt(Instant finishAt) {
        this.finishAt = finishAt;
    }

    public Boolean isStartWithProcess() {
        return startWithProcess;
    }

    public Task startWithProcess(Boolean startWithProcess) {
        this.startWithProcess = startWithProcess;
        return this;
    }

    public void setStartWithProcess(Boolean startWithProcess) {
        this.startWithProcess = startWithProcess;
    }

    public Long getProcessId() {
        return processId;
    }

    public Task processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getParentId() {
        return parentId;
    }

    public Task parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTaskModelId() {
        return taskModelId;
    }

    public Task taskModelId(Long taskModelId) {
        this.taskModelId = taskModelId;
        return this;
    }

    public void setTaskModelId(Long taskModelId) {
        this.taskModelId = taskModelId;
    }

    public Instant getPauseAt() {
        return pauseAt;
    }

    public Task pauseAt(Instant pauseAt) {
        this.pauseAt = pauseAt;
        return this;
    }

    public void setPauseAt(Instant pauseAt) {
        this.pauseAt = pauseAt;
    }

    public Integer getNbPause() {
        return nbPause;
    }

    public Task nbPause(Integer nbPause) {
        this.nbPause = nbPause;
        return this;
    }

    public void setNbPause(Integer nbPause) {
        this.nbPause = nbPause;
    }

    public Double getLogigramPosX() {
        return logigramPosX;
    }

    public Task logigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
        return this;
    }

    public void setLogigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public Double getLogigramPosY() {
        return logigramPosY;
    }

    public Task logigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
        return this;
    }

    public void setLogigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Task groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRiskId() {
        return riskId;
    }

    public Task riskId(Long riskId) {
        this.riskId = riskId;
        return this;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public Boolean isManualMode() {
        return manualMode;
    }

    public Task manualMode(Boolean manualMode) {
        this.manualMode = manualMode;
        return this;
    }

    public void setManualMode(Boolean manualMode) {
        this.manualMode = manualMode;
    }

    public LocalDate getSheduledStartAt() {
        return sheduledStartAt;
    }

    public Task sheduledStartAt(LocalDate sheduledStartAt) {
        this.sheduledStartAt = sheduledStartAt;
        return this;
    }

    public void setSheduledStartAt(LocalDate sheduledStartAt) {
        this.sheduledStartAt = sheduledStartAt;
    }

    public Integer getSheduledStartHour() {
        return sheduledStartHour;
    }

    public Task sheduledStartHour(Integer sheduledStartHour) {
        this.sheduledStartHour = sheduledStartHour;
        return this;
    }

    public void setSheduledStartHour(Integer sheduledStartHour) {
        this.sheduledStartHour = sheduledStartHour;
    }

    public Integer getSheduledStartMinute() {
        return sheduledStartMinute;
    }

    public Task sheduledStartMinute(Integer sheduledStartMinute) {
        this.sheduledStartMinute = sheduledStartMinute;
        return this;
    }

    public void setSheduledStartMinute(Integer sheduledStartMinute) {
        this.sheduledStartMinute = sheduledStartMinute;
    }

    public Boolean isChecked() {
        return checked;
    }

    public Task checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getCurrentPauseHistoryId() {
        return currentPauseHistoryId;
    }

    public Task currentPauseHistoryId(Long currentPauseHistoryId) {
        this.currentPauseHistoryId = currentPauseHistoryId;
        return this;
    }

    public void setCurrentPauseHistoryId(Long currentPauseHistoryId) {
        this.currentPauseHistoryId = currentPauseHistoryId;
    }

    public Boolean isExceceed() {
        return exceceed;
    }

    public Task exceceed(Boolean exceceed) {
        this.exceceed = exceceed;
        return this;
    }

    public void setExceceed(Boolean exceceed) {
        this.exceceed = exceceed;
    }

    public Task getStartupTask() {
        return startupTask;
    }

    public Task startupTask(Task task) {
        this.startupTask = task;
        return this;
    }

    public void setStartupTask(Task task) {
        this.startupTask = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", nbMinuites=" + getNbMinuites() +
            ", nbHours=" + getNbHours() +
            ", nbDays=" + getNbDays() +
            ", nbMonths=" + getNbMonths() +
            ", nbYears=" + getNbYears() +
            ", startAt='" + getStartAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", type='" + getType() + "'" +
            ", valid='" + isValid() + "'" +
            ", finishAt='" + getFinishAt() + "'" +
            ", startWithProcess='" + isStartWithProcess() + "'" +
            ", processId=" + getProcessId() +
            ", parentId=" + getParentId() +
            ", taskModelId=" + getTaskModelId() +
            ", pauseAt='" + getPauseAt() + "'" +
            ", nbPause=" + getNbPause() +
            ", logigramPosX=" + getLogigramPosX() +
            ", logigramPosY=" + getLogigramPosY() +
            ", groupId=" + getGroupId() +
            ", riskId=" + getRiskId() +
            ", manualMode='" + isManualMode() + "'" +
            ", sheduledStartAt='" + getSheduledStartAt() + "'" +
            ", sheduledStartHour=" + getSheduledStartHour() +
            ", sheduledStartMinute=" + getSheduledStartMinute() +
            ", checked='" + isChecked() + "'" +
            ", currentPauseHistoryId=" + getCurrentPauseHistoryId() +
            ", exceceed='" + isExceceed() + "'" +
            "}";
    }
}
