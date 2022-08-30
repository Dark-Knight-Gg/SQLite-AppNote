package com.example.app_note;

public class Job {
    private String jobName;
    private int jobID;

    public Job(String jobName, int jobID) {
        this.jobName = jobName;
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }
}
