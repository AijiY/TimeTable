package com.example.timetable.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.timetable.R;
import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import com.example.timetable.model.domain.Service;
import com.example.timetable.model.domain.TimeTableDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
  private Service service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

//    以下dbテスト
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    // LinearLayoutのIDを取得
    LinearLayout layout = findViewById(R.id.test);

    executorService.execute(() -> {
      service = new Service(this);

      List<Station> stations = service.getStations();

      List<TimeTable> timeTables = service.getTimeTables();
      TimeTableDetail timeTableDetail = service.getTimeTableDetailFromTimeTable(timeTables.get(300));

      new Handler(Looper.getMainLooper()).post(() -> {
        for (Station station : stations) {
          TextView textView = new TextView(this);
          textView.setText(station.getName());
          LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.WRAP_CONTENT
          );
          textView.setLayoutParams(params1);
          textView.setTextColor(Color.BLACK);
          textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
          layout.addView(textView);
        }

        List<String> strings = new ArrayList<>(List.of(
            timeTableDetail.getTrainCompanyNickname(),
            timeTableDetail.getTrainLineName(),
            timeTableDetail.getDepartureStationName(),
            timeTableDetail.getArrivalStationName(),
            timeTableDetail.getTimeTable().getDepartureTime().toString(),
            timeTableDetail.getTimeTable().getArrivalTime().toString()
        ));

        for (String string : strings) {
          System.out.println(string);
        }

        // TextViewを作成
        for (String string : strings) {
          TextView textView = new TextView(this);
          textView.setText(string);

          // TextViewのレイアウトパラメータを設定
          LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.WRAP_CONTENT
          );
          textView.setLayoutParams(params1);

          // TextViewのスタイルを設定
          textView.setTextColor(Color.BLACK);
          textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

          // TextViewをLinearLayoutに追加
          layout.addView(textView);

        }

      });

      executorService.shutdown();

    });

  }

}