package com.example.clinicmanagement3;

public class SecretaryAppointment {
    private final String time;
    private final String service;
    private final String status;
    private final String fullName;

    public SecretaryAppointment(String time, String service, String status, String fullName) {
        this.time = time;
        this.service = service;
        this.status = status;
        this.fullName = fullName;
    }

    public String getTime() { return time; }
    public String getService() { return service; }
    public String getStatus() { return status; }
    public String getFullName() { return fullName; }
}