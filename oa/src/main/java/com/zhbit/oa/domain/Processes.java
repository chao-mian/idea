package com.zhbit.oa.domain;

public class Processes {
    String processesName;
    String processesStartUser;
    String processesStartTime;
    String processesTask;

    public String getProcessesName() {
        return processesName;
    }

    public void setProcessesName(String processesName) {
        this.processesName = processesName;
    }

    public String getProcessesStartUser() {
        return processesStartUser;
    }

    public void setProcessesStartUser(String processesStartUser) {
        this.processesStartUser = processesStartUser;
    }

    public String getProcessesStartTime() {
        return processesStartTime;
    }

    public void setProcessesStartTime(String processesStartTime) {
        this.processesStartTime = processesStartTime;
    }

    public String getProcessesTask() {
        return processesTask;
    }

    public void setProcessesTask(String processesTask) {
        this.processesTask = processesTask;
    }

    @Override
    public String toString() {
        return "Processes{" +
                "processesName='" + processesName + '\'' +
                ", processesStartUser='" + processesStartUser + '\'' +
                ", processesStartTime='" + processesStartTime + '\'' +
                ", processesTask='" + processesTask + '\'' +
                '}';
    }
}
