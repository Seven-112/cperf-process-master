package com.mshz.model;

import java.io.Serializable;

public class PonctualTaskUtil implements Serializable{
    private Long taskId;

    private Integer nbMinutes;

    private Integer nbHours;

    private Integer nbDays;

    private Integer nbMonths;

    private Integer nbYears;

    private Long instanceId;

    public PonctualTaskUtil(){}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getNbMinutes() {
        return nbMinutes;
    }

    public void setNbMinutes(Integer nbMinutes) {
        this.nbMinutes = nbMinutes;
    }

    public Integer getNbHours() {
        return nbHours;
    }

    public void setNbHours(Integer nbHours) {
        this.nbHours = nbHours;
    }

    public Integer getNbDays() {
        return nbDays;
    }

    public void setNbDays(Integer nbDays) {
        this.nbDays = nbDays;
    }

    public Integer getNbMonths() {
        return nbMonths;
    }

    public void setNbMonths(Integer nbMonths) {
        this.nbMonths = nbMonths;
    }

    public Integer getNbYears() {
        return nbYears;
    }

    public void setNbYears(Integer nbYears) {
        this.nbYears = nbYears;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QPonctualTaskInfo{" +
            "id=" + getTaskId() +
            ", nbMinutes=" + getNbMinutes() +
            ", nbHours=" + getNbHours() +
            ", nbDays=" + getNbDays() +
            ", nbMonths=" + getNbMonths() +
            ", nbYears=" + getNbYears() +
            ", instanceId=" + getInstanceId() +
            "}";
    }
}
