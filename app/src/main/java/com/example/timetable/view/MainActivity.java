package com.example.timetable.view;

import android.os.Bundle;

import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.timetable.R;
import com.example.timetable.model.domain.Service;
import com.example.timetable.model.domain.TimeTableDetail;
import com.google.android.material.tabs.TabLayout;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
  private Service service;

  private LocalDateTime presentTime = LocalDateTime.now();
  private LocalDateTime showingTime;
  private boolean isOutward = true;
  private int TRANSFER_MINUTES = 5;

  // 各種Widgetの定義
  private Button reverseButton;
  private TabLayout afterTimeTab;
  private TextView firstLineDepartureTime;
  private TextView firstLineDepartureStation;
  private TextView firstLineName;
  private TextView firstLineArrivalTime;
  private TextView firstLineArrivalStation;
  private TextView secondLineDepartureTime;
  private TextView secondLineDepartureStation;
  private TextView secondLineName;
  private TextView secondLineArrivalTime;
  private TextView secondLineArrivalStation;

  // DB操作用
  private ExecutorService executorService;
  private Handler mainHandler;

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


    // 定数への代入全般
    assignToConstants();

    // 初期の表示
    showTimeTable(isOutward);

////    以下dbテスト
//    ExecutorService executorService = Executors.newSingleThreadExecutor();
//    // LinearLayoutのIDを取得
//    LinearLayout layout = findViewById(R.id.test);
//
//    executorService.execute(() -> {
//      service = new Service(this);
//
//      List<Station> stations = service.getStations();
//
//      List<TimeTable> timeTables = service.getTimeTables();
//      TimeTableDetail timeTableDetail = service.getTimeTableDetailFromTimeTable(timeTables.get(300));
//
//      new Handler(Looper.getMainLooper()).post(() -> {
//        for (Station station : stations) {
//          TextView textView = new TextView(this);
//          textView.setText(station.getName());
//          LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
//              LinearLayout.LayoutParams.MATCH_PARENT,
//              LinearLayout.LayoutParams.WRAP_CONTENT
//          );
//          textView.setLayoutParams(params1);
//          textView.setTextColor(Color.BLACK);
//          textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//          layout.addView(textView);
//        }
//
//        List<String> strings = new ArrayList<>(List.of(
//            timeTableDetail.getTrainCompanyNickname(),
//            timeTableDetail.getTrainLineName(),
//            timeTableDetail.getDepartureStationName(),
//            timeTableDetail.getArrivalStationName(),
//            timeTableDetail.getTimeTable().getDepartureTime().toString(),
//            timeTableDetail.getTimeTable().getArrivalTime().toString()
//        ));
//
//        for (String string : strings) {
//          System.out.println(string);
//        }
//
//        // TextViewを作成
//        for (String string : strings) {
//          TextView textView = new TextView(this);
//          textView.setText(string);
//
//          // TextViewのレイアウトパラメータを設定
//          LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
//              LinearLayout.LayoutParams.MATCH_PARENT,
//              LinearLayout.LayoutParams.WRAP_CONTENT
//          );
//          textView.setLayoutParams(params1);
//
//          // TextViewのスタイルを設定
//          textView.setTextColor(Color.BLACK);
//          textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//
//          // TextViewをLinearLayoutに追加
//          layout.addView(textView);
//
//        }
//
//      });
//
//      executorService.shutdown();
//
//    });

  }

  private void assignToConstants() {
    service = new Service(this);
    
    reverseButton = findViewById(R.id.reverseButton);
    afterTimeTab = findViewById(R.id.afterTimeTab);
    firstLineDepartureTime = findViewById(R.id.firstLineDepartureTime);
    firstLineDepartureStation = findViewById(R.id.firstLineDepartureStation);
    firstLineName = findViewById(R.id.firstLineName);
    firstLineArrivalTime = findViewById(R.id.firstLineArrivalTime);
    firstLineArrivalStation = findViewById(R.id.firstLineArrivalStation);
    secondLineDepartureTime = findViewById(R.id.secondLineDepartureTime);
    secondLineDepartureStation = findViewById(R.id.secondLineDepartureStation);
    secondLineName = findViewById(R.id.secondLineName);
    secondLineArrivalTime = findViewById(R.id.secondLineArrivalTime);
    secondLineArrivalStation = findViewById(R.id.secondLineArrivalStation);

    executorService = Executors.newSingleThreadExecutor();
    mainHandler = new Handler(getMainLooper());
  }

  private void showTimeTable(final boolean isOutward) {
    showingTime = presentTime;

    // isOutwardに応じた定数の設定

    executorService.execute(() -> {
      // データベース操作の実行
      final TimeTableDetail firstLineTimeTableDetail = service.getNextTimeTableDetail(showingTime, true, 1);

      LocalTime secondLineDepartureLocalTime = firstLineTimeTableDetail.getTimeTable().getArrivalTime().plusMinutes(TRANSFER_MINUTES);
      LocalDateTime secondLineDepartureLocalDateTime = LocalDateTime.of(showingTime.toLocalDate(), secondLineDepartureLocalTime);
      final TimeTableDetail secondLineTimeTableDetail = service.getNextTimeTableDetail(secondLineDepartureLocalDateTime, false, 2);

      // UI の更新をメインスレッドで実行
      mainHandler.post(() -> {
        firstLineDepartureTime.setText(firstLineTimeTableDetail.getTimeTable().getDepartureTime().toString());
        firstLineDepartureStation.setText(firstLineTimeTableDetail.getDepartureStationName());
        firstLineName.setText(firstLineTimeTableDetail.getTrainCompanyNickname() + " " + firstLineTimeTableDetail.getTrainLineName());
        firstLineArrivalTime.setText(firstLineTimeTableDetail.getTimeTable().getArrivalTime().toString());
        firstLineArrivalStation.setText(firstLineTimeTableDetail.getArrivalStationName());
        secondLineDepartureTime.setText(secondLineTimeTableDetail.getTimeTable().getDepartureTime().toString());
        secondLineDepartureStation.setText(secondLineTimeTableDetail.getDepartureStationName());
        secondLineName.setText(secondLineTimeTableDetail.getTrainCompanyNickname() + " " + secondLineTimeTableDetail.getTrainLineName());
        secondLineArrivalTime.setText(secondLineTimeTableDetail.getTimeTable().getArrivalTime().toString());
        secondLineArrivalStation.setText(secondLineTimeTableDetail.getArrivalStationName());
      });
    });
  }

}