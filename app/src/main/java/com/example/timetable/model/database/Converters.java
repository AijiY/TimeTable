package com.example.timetable.model.database;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Converters {
  private static final String TIME_FORMAT = "HH:mm:ss";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault());

  @TypeConverter
  public static String fromLocalTime(LocalTime localTime) {
    return localTime != null ? formatter.format(localTime) : null;
  }

  @TypeConverter
  public static LocalTime toLocalTime(String timeString) {
    return timeString != null ? LocalTime.parse(timeString, formatter) : null;
  }
}
