package com.example.timetable.utils;


import android.util.Log;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

  public static boolean isWeekday(LocalDateTime dateTime) {
    List<LocalDate> holidayDates = fetchHolidays(dateTime.toLocalDate());
    return holidayDates.stream().noneMatch(holidayDate -> dateTime.toLocalDate().equals(holidayDate))
        && dateTime.getDayOfWeek().getValue() < 6;
  }

  public static List<LocalDate> fetchHolidays(LocalDate date) {
    int year = date.getYear();
    List<LocalDate> holidayDates = new ArrayList<>();
    // 元日
    holidayDates.add(LocalDate.of(year, 1, 1));
    // 成人の日（1月の第2月曜日）
    holidayDates.add(LocalDate.of(year, 1, 1).with(DayOfWeek.MONDAY).plusWeeks(1));
    // 建国記念の日
    holidayDates.add(LocalDate.of(year, 2, 11));
    // 天皇誕生日
    holidayDates.add(LocalDate.of(year, 2, 23));
    // 春分の日
    holidayDates.add(LocalDate.of(year, 3, 20));
    // 昭和の日
    holidayDates.add(LocalDate.of(year, 4, 29));
    // 憲法記念日
    holidayDates.add(LocalDate.of(year, 5, 3));
    // みどりの日
    holidayDates.add(LocalDate.of(year, 5, 4));
    // こどもの日
    holidayDates.add(LocalDate.of(year, 5, 5));
    // 海の日（7月の第3月曜日）
    holidayDates.add(LocalDate.of(year, 7, 1).with(DayOfWeek.MONDAY).plusWeeks(2));
    // 山の日
    holidayDates.add(LocalDate.of(year, 8, 11));
    // 敬老の日（9月の第3月曜日）
    holidayDates.add(LocalDate.of(year, 9, 1).with(DayOfWeek.MONDAY).plusWeeks(2));
    // 秋分の日
    holidayDates.add(LocalDate.of(year, 9, 23));
    // スポーツの日（10月の第2月曜日）
    holidayDates.add(LocalDate.of(year, 10, 1).with(DayOfWeek.MONDAY).plusWeeks(1));
    // 文化の日
    holidayDates.add(LocalDate.of(year, 11, 3));
    // 勤労感謝の日
    holidayDates.add(LocalDate.of(year, 11, 23));

    return holidayDates;
  }



//  // 暫定の祝日を考慮しない平日判定
//  public static boolean isWeekday(LocalDateTime date) {
//    return date.getDayOfWeek().getValue() < 6;
//  }

}
