package com.example.timetable.utils;



import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DateUtils {

  public static boolean isWeekday(LocalDateTime dateTime, Context context) {
    List<LocalDate> holidayDates = fetchHolidays(dateTime.toLocalDate().getYear(), context);
    return holidayDates.stream().noneMatch(holidayDate -> dateTime.toLocalDate().equals(holidayDate))
        && dateTime.getDayOfWeek().getValue() < 6;
  }

  public static List<LocalDate> fetchHolidays(int year, Context context) {
    List<LocalDate> holidayDates = new ArrayList<>();

    try {
      // Google Calendar APIのHttpTransportとJsonFactoryを設定
      HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
      JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

      // サービスアカウントの認証情報を読み込む
      InputStream inputStream = context.getAssets().open("calendar_api_reader.json");
      GoogleCredential credential = GoogleCredential.fromStream(inputStream)
          .createScoped(Collections.singleton(CalendarScopes.CALENDAR_READONLY));

      // Calendar APIのサービスオブジェクトを作成
      Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
          .setApplicationName("TimeTable")
          .build();

      // 日本の祝日のカレンダーID
      String calendarId = "ja.japanese#holiday@group.v.calendar.google.com";

      // 指定した年の1月1日から12月31日までのイベントを取得
      DateTime start = new DateTime(year + "-01-01T00:00:00Z");
      DateTime end = new DateTime(year + "-12-31T23:59:59Z");
      Events events = service.events().list(calendarId)
          .setTimeMin(start)
          .setTimeMax(end)
          .setSingleEvents(true)
          .execute();

      // 取得したイベントデータを処理する
      for (Event event : events.getItems()) {
        // 日付を取得（"date"は終日イベントの場合に使用されるフィールド）
        if (event.getStart().getDate() != null) {
          LocalDate holiday = LocalDate.parse(event.getStart().getDate().toStringRfc3339());
          holidayDates.add(holiday);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // 祝日ではないものを除外
    // 1/2, 1/3, 2/3, 3/3, 12/31
    List<LocalDate> removeDates = new ArrayList<>(List.of(
        LocalDate.of(year, 1, 2),
        LocalDate.of(year, 1, 3),
        LocalDate.of(year, 2, 3),
        LocalDate.of(year, 3, 3),
        LocalDate.of(year, 12, 31)
    ));
    holidayDates.removeAll(removeDates);

    Log.d("DateUtils", "holidayDates: " + holidayDates);
    return holidayDates;

  }

}
