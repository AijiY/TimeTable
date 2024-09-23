package com.example.timetable.view;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
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
  private View firstLineRailway;
  private View secondLineRailway;

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
    showingTime = presentTime;
    showTimeTable(isOutward, showingTime);

    // 逆ボタンの処理
    reverseButton.setOnClickListener(v -> {
      isOutward = !isOutward;
      showTimeTable(isOutward, showingTime);
    });

    // タブの処理
    afterTimeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
          case 0:
            showingTime = presentTime;
            break;
          case 1:
            showingTime = presentTime.plusMinutes(5);
            break;
          case 2:
            showingTime = presentTime.plusMinutes(10);
            break;
          case 3:
            showingTime = presentTime.plusMinutes(30);
            break;
          default:
            showingTime = presentTime;
        }
        showTimeTable(isOutward, showingTime);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
      }
    });

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
    firstLineRailway = findViewById(R.id.firstLineRailway);
    secondLineRailway = findViewById(R.id.secondLineRailway);

    executorService = Executors.newSingleThreadExecutor();
    mainHandler = new Handler(getMainLooper());
  }

  private void showTimeTable(final boolean isOutward, LocalDateTime showingTime) {

    // isOutwardに応じた定数の設定
    int firstLineId = isOutward ? 1 : 2;
    boolean firstLineIsInBound = true;
    int secondLineId = isOutward ? 2 : 1;
    boolean secondLineIsInBound = false;

    executorService.execute(() -> {
      // データベース操作の実行
      final TimeTableDetail firstLineTimeTableDetail = service.getNextTimeTableDetail(showingTime, firstLineIsInBound, firstLineId);

      LocalTime secondLineDepartureLocalTime = firstLineTimeTableDetail.getTimeTable().getArrivalTime().plusMinutes(TRANSFER_MINUTES);
      LocalDateTime secondLineDepartureLocalDateTime = LocalDateTime.of(showingTime.toLocalDate(), secondLineDepartureLocalTime);
      final TimeTableDetail secondLineTimeTableDetail = service.getNextTimeTableDetail(secondLineDepartureLocalDateTime, secondLineIsInBound, secondLineId);

      // UI の更新をメインスレッドで実行
      mainHandler.post(() -> {
        firstLineDepartureTime.setText(firstLineTimeTableDetail.getTimeTable().getDepartureTime().toString() + " 発");
        firstLineDepartureStation.setText(firstLineTimeTableDetail.getDepartureStationName() + "駅");
        firstLineRailway.setBackgroundColor(Color.parseColor(firstLineTimeTableDetail.getTrainLineColor()));
        firstLineName.setText(firstLineTimeTableDetail.getTrainCompanyNickname() + " " + firstLineTimeTableDetail.getTrainLineName() + "（" + firstLineTimeTableDetail.getTimeTable().getTrainType() + "）");
        firstLineArrivalTime.setText(firstLineTimeTableDetail.getTimeTable().getArrivalTime().toString() + " 着");
        firstLineArrivalStation.setText(firstLineTimeTableDetail.getArrivalStationName() + "駅");
        secondLineDepartureTime.setText(secondLineTimeTableDetail.getTimeTable().getDepartureTime().toString() + " 発");
        secondLineDepartureStation.setText(secondLineTimeTableDetail.getDepartureStationName() + "駅");
        secondLineRailway.setBackgroundColor(Color.parseColor(secondLineTimeTableDetail.getTrainLineColor()));
        secondLineName.setText(secondLineTimeTableDetail.getTrainCompanyNickname() + " " + secondLineTimeTableDetail.getTrainLineName() + "（" + secondLineTimeTableDetail.getTimeTable().getTrainType() + "）");
        secondLineArrivalTime.setText(secondLineTimeTableDetail.getTimeTable().getArrivalTime().toString() + " 着");
        secondLineArrivalStation.setText(secondLineTimeTableDetail.getArrivalStationName() + "駅");
      });
    });
  }

}