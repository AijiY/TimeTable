package com.example.timetable.model.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TimeTable.class, TrainLine.class, TrainCompany.class, Station.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TimeTableDatabase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();

    private static volatile TimeTableDatabase INSTANCE;  // シングルトン用に volatile 修飾子を追加
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TimeTableDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TimeTableDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeTableDatabase.class, "time_table_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)  // 初期データのコールバックを追加
                            .build();
                }
            }
        } else {
            Log.d("TimeTableDatabase", "Returning existing database instance");
        }
        return INSTANCE;
    }

//    インスタンスがない場合にsingletonパターンでインスタンスを生成する、このときに初期値を入れる
//    TrainComppanyに初期値を入れる
//    1つ目：name: "JR", nickname: "JR"
//    2つ目：name: "近畿日本鉄道", nickname: "近鉄"

    private static final Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // データベースが作成された直後に初期データを挿入
            databaseWriteExecutor.execute(() -> {
                TimeTableDao dao = INSTANCE.timeTableDao();

                // TrainCompany テーブルに初期データを挿入
                dao.insertTrainCompany(new TrainCompany("JR", "JR"));
                dao.insertTrainCompany(new TrainCompany("近畿日本鉄道", "近鉄"));

                // Station テーブルに初期データを挿入
                dao.insertStation(new Station("勝川", 1));
                dao.insertStation(new Station("名古屋", 1));
                dao.insertStation(new Station("近鉄名古屋", 2));
                dao.insertStation(new Station("津", 2));

                // TrainLine テーブルに初期データを挿入
                dao.insertTrainLine(new TrainLine("中央線",1, 2, 1));
                dao.insertTrainLine(new TrainLine("名古屋線",2, 3, 4));

                // TimeTable テーブルに初期データを挿入
                // 中央線・上り・平日
                dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(05, 15, 00), LocalTime.of(05, 34, 00), "普通"));

            });
        }
    };



}
