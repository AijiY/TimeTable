package com.example.timetable.model.database;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Converters {

  @TypeConverter
  public static Integer fromLocalTime(LocalTime localTime) {
    return localTime != null ? localTime.toSecondOfDay() : null;
  }

  @TypeConverter
  public static LocalTime toLocalTime(Integer seconds) {
    return seconds != null ? LocalTime.ofSecondOfDay(seconds) : null;
  }
}
