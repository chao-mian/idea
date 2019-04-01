package com.zhbit.oa.domain;

public class Leave {
    private String name;
    private String zhiwei;
    private String bumen;
    private String Date;
    private String startDate;
    private String endDate;
    private String reason;
    private String leaveType;
    private String leaderOpinion;
    private String leaderApproval;
    private String hrOpinion;
    private String hrApproval;
    private String leaderAssignee;
    private String hrAssignee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhiwei() {
        return zhiwei;
    }

    public void setZhiwei(String zhiwei) {
        this.zhiwei = zhiwei;
    }

    public String getBumen() {
        return bumen;
    }

    public void setBumen(String bumen) {
        this.bumen = bumen;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaderAssignee() {
        return leaderAssignee;
    }

    public void setLeaderAssignee(String leaderAssignee) {
        this.leaderAssignee = leaderAssignee;
    }

    public String getHrAssignee() {
        return hrAssignee;
    }

    public void setHrAssignee(String hrAssignee) {
        this.hrAssignee = hrAssignee;
    }

    public String getLeaderOpinion() {
        return leaderOpinion;
    }

    public void setLeaderOpinion(String leaderOpinion) {
        this.leaderOpinion = leaderOpinion;
    }

    public String getLeaderApproval() {
        return leaderApproval;
    }

    public void setLeaderApproval(String leaderApproval) {
        this.leaderApproval = leaderApproval;
    }

    public String getHrOpinion() {
        return hrOpinion;
    }

    public void setHrOpinion(String hrOpinion) {
        this.hrOpinion = hrOpinion;
    }

    public String getHrApproval() {
        return hrApproval;
    }

    public void setHrApproval(String hrApproval) {
        this.hrApproval = hrApproval;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "name='" + name + '\'' +
                ", zhiwei='" + zhiwei + '\'' +
                ", bumen='" + bumen + '\'' +
                ", Date='" + Date + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", reason='" + reason + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", leaderOpinion='" + leaderOpinion + '\'' +
                ", leaderApproval='" + leaderApproval + '\'' +
                ", hrOpinion='" + hrOpinion + '\'' +
                ", hrApproval='" + hrApproval + '\'' +
                ", leaderAssignee='" + leaderAssignee + '\'' +
                ", hrAssignee='" + hrAssignee + '\'' +
                '}';
    }
}
