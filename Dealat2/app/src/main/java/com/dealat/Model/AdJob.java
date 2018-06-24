package com.dealat.Model;

/**
 * Created by developer on 11.03.18.
 */

public class AdJob extends Ad {
    private String educationId, ScheduleId, certificateId;
    private String educationName, scheduleName, certificateName, experience;
    private double salary;
    private int gender;

    public AdJob(){
        educationName = "";
        scheduleName = "";
        experience = "";
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(String scheduleId) {
        ScheduleId = scheduleId;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
