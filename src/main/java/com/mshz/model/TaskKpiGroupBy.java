package com.mshz.model;

import com.mshz.domain.enumeration.TaskStatus;

public class TaskKpiGroupBy {
    private Long count;
    private TaskStatus status;
    private Long userId;
    private Boolean exceceed;

    public TaskKpiGroupBy(Long count,TaskStatus status, Long userId, Boolean execeed){
        this.count = count;
        this.status = status;
        this.userId = userId;
        this.exceceed = execeed;
    }
    
    public TaskKpiGroupBy(Long count,TaskStatus status, Boolean execeed){
        this.count = count;
        this.status = status;
        this.exceceed = execeed;
    }

    public Long getCount() {
        return count;
    }
    public TaskStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getExceceed() {
        return exceceed;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setExceceed(Boolean exceceed) {
        this.exceceed = exceceed;
    }

    @Override
    public String toString() {
        return "{count:"+count+", status:"+status+", userId:"+userId+", execeed:"+exceceed+"}";
    }
    
}
