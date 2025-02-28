package com.example.sp_subjectmanager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Lession {

    public int Id;
    public Session Session;
    public String Name;
    public boolean Completed;

    public Lession(int Id, Session Session, String Name, boolean Completed) {
        this.Name = Name;
        this.Id = Id;
        this.Session = Session;
        this.Completed = Completed;
    }

    public int getAssocialIndexOfSession() {
       Session[] subject_sessions =  Data.Sessions.stream().filter(x->x.Subject == this.Session.Subject).toArray(Session[]::new);
        return Arrays.asList(subject_sessions).indexOf(this.Session) +1 ;
    }
}
