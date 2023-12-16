package com.muqadisaejaz.smdproject;
// SessionModel.java
public class Session {
    private String name;
    private String email;
    private String topic;
    private String mode;
    private String time;

    public Session(){

    }
    public Session(String name, String email, String topic, String mode, String time) {
        this.name = name;
        this.email = email;
        this.topic = topic;
        this.mode = mode;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTopic() {
        return topic;
    }

    public String getMode() {
        return mode;
    }

    public String getTime() {
        return time;
    }
}
