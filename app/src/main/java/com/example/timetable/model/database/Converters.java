package com.example.timetable.model.database;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Converters {
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.getDefault());

  @TypeConverter
  public static String fromLocalDateTime(LocalDateTime localDateTime) {
    return localDateTime != null ? formatter.format(localDateTime) : null;
  }

  @TypeConverter
  public static LocalDateTime toLocalDateTime(String dateString) {
    return dateString != null ? LocalDateTime.parse(dateString, formatter) : null;
  }
}
