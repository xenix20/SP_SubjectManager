package com.example.sp_subjectmanager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Data {
    public static List<Subject> Subjects = new ArrayList<>();
    public  static List<Session> Sessions = new ArrayList<>();

    public static List<Lession> Lessions  = new ArrayList<>();
    public static Subject SelectedSubject = null;

    public static String getCurrentFormatedDate() {
        LocalDate currentDate = LocalDate.now();

        // Форматируем в "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    public static String getCurrentDayOfWeek() {
        // Получаем текущий день недели
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        // Карта соответствий (англ. -> рус.)
        Map<DayOfWeek, String> daysMap = Map.of(
                DayOfWeek.MONDAY, "пн",
                DayOfWeek.TUESDAY, "вт",
                DayOfWeek.WEDNESDAY, "ср",
                DayOfWeek.THURSDAY, "чт",
                DayOfWeek.FRIDAY, "пт",
                DayOfWeek.SATURDAY, "сб",
                DayOfWeek.SUNDAY, "вс"
        );

        return daysMap.get(dayOfWeek);
    }

}
