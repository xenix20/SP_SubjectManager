package com.example.sp_subjectmanager;

import com.example.sp_subjectmanager.Subject;

import java.util.Arrays;

public class Session {
    public int Id;
    public Subject Subject;
    public int EllapsedTime;
    public String Date;

    public Session(int id, Subject subject, int ellapsedTime, String date) {
        this.Id =id;
        this.Subject = subject;
        this.EllapsedTime = ellapsedTime;
        this.Date = date;
    }

    public int getAssocialIndexOfSession() {
        Session[] subject_sessions =  Data.Sessions.stream().filter(x->x.Subject == this.Subject).toArray(Session[]::new);
        return Arrays.asList(subject_sessions).indexOf(this) +1 ;
    }
}
