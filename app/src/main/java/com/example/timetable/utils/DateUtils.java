package com.example.timetable.utils;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import java.time.LocalDateTime;

public class DateUtils {

  /**
   * 祝日を考慮して、平日かどうかを判定する
   * @param date
   * @return 土日祝日はfalse、平日はtrue
   */
  public static boolean isWeekday(LocalDateTime date) {
    HolidayManager holidayManager = HolidayManager.getInstance(HolidayCalendar.JAPAN);
    if (holidayManager.isHoliday(date.toLocalDate())) {
      return false;
    }
    return date.getDayOfWeek().getValue() < 6;
  }

}
