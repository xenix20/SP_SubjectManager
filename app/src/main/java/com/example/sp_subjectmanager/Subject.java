package com.example.sp_subjectmanager;

import java.util.List;
import java.util.stream.Stream;

public class Subject {
    public int Id;
    public String Name;

    public Subject(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public String getName(){
        return Name;
    }

    public Session getLastSession() {
        return getAllSessions().reduce((a, b) -> b).orElse(null);
    }

    public Stream<Session> getAllSessions() {
        return Data.Sessions.stream()
                .filter(x -> x.Subject == this);
    }


    public int CalculateAllHours() {

        return getAllSessions()
                .mapToInt(x -> x.EllapsedTime)
                .sum() ;

    }
}
