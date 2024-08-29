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

                INSTANCE.runInTransaction(() -> {
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
                    // JR中央線・上り・平日
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(5, 15, 0), LocalTime.of(5, 34, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(5, 47, 0), LocalTime.of(6, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(5, 56, 0), LocalTime.of(6, 16, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 6, 0), LocalTime.of(6, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 16, 0), LocalTime.of(6, 36, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 26, 0), LocalTime.of(6, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 36, 0), LocalTime.of(6, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 44, 0), LocalTime.of(7, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(6, 53, 0), LocalTime.of(7, 15, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 1, 0), LocalTime.of(7, 23, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 10, 0), LocalTime.of(7, 32, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 18, 0), LocalTime.of(7, 40, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 23, 0), LocalTime.of(7, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 28, 0), LocalTime.of(7, 50, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 33, 0), LocalTime.of(7, 55, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 37, 0), LocalTime.of(7, 59, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 42, 0), LocalTime.of(8, 4, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 46, 0), LocalTime.of(8, 8, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 50, 0), LocalTime.of(8, 12, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 54, 0), LocalTime.of(8, 16, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(7, 58, 0), LocalTime.of(8, 20, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 2, 0), LocalTime.of(8, 26, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 7, 0), LocalTime.of(8, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 11, 0), LocalTime.of(8, 34, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 16, 0), LocalTime.of(8, 38, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 20, 0), LocalTime.of(8, 42, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 24, 0), LocalTime.of(8, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 29, 0), LocalTime.of(8, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 34, 0), LocalTime.of(8, 56, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 42, 0), LocalTime.of(9, 4, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 49, 0), LocalTime.of(9, 8, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(8, 55, 0), LocalTime.of(9, 15, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 1, 0), LocalTime.of(9, 23, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 11, 0), LocalTime.of(9, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 21, 0), LocalTime.of(9, 39, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 26, 0), LocalTime.of(9, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 32, 0), LocalTime.of(9, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 39, 0), LocalTime.of(9, 58, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 47, 0), LocalTime.of(10, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(9, 56, 0), LocalTime.of(10, 13, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 2, 0), LocalTime.of(10, 21, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 10, 0), LocalTime.of(10, 29, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 18, 0), LocalTime.of(10, 37, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 27, 0), LocalTime.of(10, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 34, 0), LocalTime.of(10, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 47, 0), LocalTime.of(11, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(10, 57, 0), LocalTime.of(11, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 7, 0), LocalTime.of(11, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 17, 0), LocalTime.of(11, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 27, 0), LocalTime.of(11, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 37, 0), LocalTime.of(11, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 45, 0), LocalTime.of(12, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(11, 57, 0), LocalTime.of(12, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 7, 0), LocalTime.of(12, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 17, 0), LocalTime.of(12, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 27, 0), LocalTime.of(12, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 37, 0), LocalTime.of(12, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 45, 0), LocalTime.of(13, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(12, 57, 0), LocalTime.of(13, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 7, 0), LocalTime.of(13, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 17, 0), LocalTime.of(13, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 27, 0), LocalTime.of(13, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 37, 0), LocalTime.of(13, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 45, 0), LocalTime.of(14, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(13, 57, 0), LocalTime.of(14, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 7, 0), LocalTime.of(14, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 17, 0), LocalTime.of(14, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 27, 0), LocalTime.of(14, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 37, 0), LocalTime.of(14, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 45, 0), LocalTime.of(15, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(14, 56, 0), LocalTime.of(15, 15, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 4, 0), LocalTime.of(15, 23, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 12, 0), LocalTime.of(15, 31, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 19, 0), LocalTime.of(15, 39, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 28, 0), LocalTime.of(15, 47, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 37, 0), LocalTime.of(15, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 43, 0), LocalTime.of(16, 5, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(15, 48, 0), LocalTime.of(16, 12, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 0, 0), LocalTime.of(16, 21, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 10, 0), LocalTime.of(16, 29, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 18, 0), LocalTime.of(16, 37, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 24, 0), LocalTime.of(16, 43, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 31, 0), LocalTime.of(16, 50, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 37, 0), LocalTime.of(16, 56, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 43, 0), LocalTime.of(17, 3, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 48, 0), LocalTime.of(17, 12, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(16, 57, 0), LocalTime.of(17, 16, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 3, 0), LocalTime.of(17, 24, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 10, 0), LocalTime.of(17, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 18, 0), LocalTime.of(17, 37, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 25, 0), LocalTime.of(17, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 31, 0), LocalTime.of(17, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 36, 0), LocalTime.of(17, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 43, 0), LocalTime.of(18, 4, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 48, 0), LocalTime.of(18, 12, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(17, 57, 0), LocalTime.of(18, 16, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 3, 0), LocalTime.of(18, 24, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 10, 0), LocalTime.of(18, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 18, 0), LocalTime.of(18, 37, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 25, 0), LocalTime.of(18, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 31, 0), LocalTime.of(18, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 38, 0), LocalTime.of(18, 57, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 43, 0), LocalTime.of(19, 4, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(18, 48, 0), LocalTime.of(19, 12, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 1, 0), LocalTime.of(19, 19, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 5, 0), LocalTime.of(19, 25, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 12, 0), LocalTime.of(19, 32, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 18, 0), LocalTime.of(19, 37, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 25, 0), LocalTime.of(19, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 31, 0), LocalTime.of(19, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 38, 0), LocalTime.of(19, 57, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 43, 0), LocalTime.of(20, 4, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(19, 48, 0), LocalTime.of(20, 12, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 1, 0), LocalTime.of(20, 19, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 5, 0), LocalTime.of(20, 25, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 12, 0), LocalTime.of(20, 32, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 18, 0), LocalTime.of(20, 37, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 25, 0), LocalTime.of(20, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 31, 0), LocalTime.of(20, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 38, 0), LocalTime.of(20, 57, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 43, 0), LocalTime.of(21, 3, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 51, 0), LocalTime.of(21, 11, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(20, 58, 0), LocalTime.of(21, 17, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 6, 0), LocalTime.of(21, 29, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 16, 0), LocalTime.of(21, 36, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 25, 0), LocalTime.of(21, 45, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 36, 0), LocalTime.of(21, 55, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 43, 0), LocalTime.of(22, 3, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(21, 51, 0), LocalTime.of(22, 11, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 0, 0), LocalTime.of(22, 20, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 10, 0), LocalTime.of(22, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 20, 0), LocalTime.of(22, 40, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 27, 0), LocalTime.of(22, 50, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 42, 0), LocalTime.of(23, 1, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(22, 52, 0), LocalTime.of(23, 12, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(23, 2, 0), LocalTime.of(23, 22, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(23, 12, 0), LocalTime.of(23, 32, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(23, 22, 0), LocalTime.of(23, 41, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, true, LocalTime.of(23, 32, 0), LocalTime.of(23, 52, 0), "普通"));

                    // JR中央線・上り・休日
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(5, 15, 0), LocalTime.of(5, 34, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(5, 47, 0), LocalTime.of(6, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(5, 56, 0), LocalTime.of(6, 16, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 6, 0), LocalTime.of(6, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 16, 0), LocalTime.of(6, 36, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 26, 0), LocalTime.of(6, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 36, 0), LocalTime.of(6, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 45, 0), LocalTime.of(7, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(6, 55, 0), LocalTime.of(7, 16, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 3, 0), LocalTime.of(7, 24, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 11, 0), LocalTime.of(7, 32, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 19, 0), LocalTime.of(7, 40, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 27, 0), LocalTime.of(7, 48, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 35, 0), LocalTime.of(7, 55, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 41, 0), LocalTime.of(8, 2, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 48, 0), LocalTime.of(8, 8, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(7, 54, 0), LocalTime.of(8, 14, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 0, 0), LocalTime.of(8, 21, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 7, 0), LocalTime.of(8, 27, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 12, 0), LocalTime.of(8, 33, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 21, 0), LocalTime.of(8, 41, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 28, 0), LocalTime.of(8, 48, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 35, 0), LocalTime.of(8, 55, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 41, 0), LocalTime.of(9, 2, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 49, 0), LocalTime.of(9, 8, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(8, 55, 0), LocalTime.of(9, 15, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 1, 0), LocalTime.of(9, 23, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 11, 0), LocalTime.of(9, 30, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 21, 0), LocalTime.of(9, 39, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 26, 0), LocalTime.of(9, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 32, 0), LocalTime.of(9, 51, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 39, 0), LocalTime.of(9, 58, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 47, 0), LocalTime.of(10, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(9, 56, 0), LocalTime.of(10, 13, 0), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 2, 0), LocalTime.of(10, 21, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 10, 0), LocalTime.of(10, 29, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 18, 0), LocalTime.of(10, 37, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 27, 0), LocalTime.of(10, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 34, 0), LocalTime.of(10, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 47, 0), LocalTime.of(11, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(10, 57, 0), LocalTime.of(11, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 7, 0), LocalTime.of(11, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 17, 0), LocalTime.of(11, 36, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 27, 0), LocalTime.of(11, 46, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 37, 0), LocalTime.of(11, 56, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 45, 0), LocalTime.of(12, 6, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(11, 57, 0), LocalTime.of(12, 16, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 7, 0), LocalTime.of(12, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 17, 0), LocalTime.of(12, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 27, 0), LocalTime.of(12, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 37, 0), LocalTime.of(12, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 45, 0), LocalTime.of(13, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(12, 57, 0), LocalTime.of(13, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 7, 0), LocalTime.of(13, 26, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 17, 0), LocalTime.of(13, 36, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 27, 0), LocalTime.of(13, 46, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 37, 0), LocalTime.of(13, 56, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 45, 0), LocalTime.of(14, 6, 0), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(13, 57, 0), LocalTime.of(14, 16, 0), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 7), LocalTime.of(14, 26), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 17), LocalTime.of(14, 36), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 27), LocalTime.of(14, 46), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 37), LocalTime.of(14, 56), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 45), LocalTime.of(15, 6), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(14, 56), LocalTime.of(15, 15), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 4), LocalTime.of(15, 23), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 12), LocalTime.of(15, 31), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 19), LocalTime.of(15, 39), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 28), LocalTime.of(15, 47), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 37), LocalTime.of(15, 56), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 43), LocalTime.of(16, 5), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(15, 48), LocalTime.of(16, 12), "区間快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 0), LocalTime.of(16, 21), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 10), LocalTime.of(16, 29), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 18), LocalTime.of(16, 37), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 24), LocalTime.of(16, 43), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 31), LocalTime.of(16, 50), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 37), LocalTime.of(16, 56), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 43), LocalTime.of(17, 3), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 48), LocalTime.of(17, 12), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(16, 57), LocalTime.of(17, 16), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 3), LocalTime.of(17, 24), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 10), LocalTime.of(17, 30), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 18), LocalTime.of(17, 37), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 25), LocalTime.of(17, 45), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 31), LocalTime.of(17, 51), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 36), LocalTime.of(17, 56), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 43), LocalTime.of(18, 4), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 48), LocalTime.of(18, 12), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(17, 57), LocalTime.of(18, 16), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 3), LocalTime.of(18, 24), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 10), LocalTime.of(18, 30), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 18), LocalTime.of(18, 37), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 25), LocalTime.of(18, 45), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 31), LocalTime.of(18, 51), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 38), LocalTime.of(18, 57), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 43), LocalTime.of(19, 4), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(18, 48), LocalTime.of(19, 12), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 01), LocalTime.of(19, 19), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 05), LocalTime.of(19, 25), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 12), LocalTime.of(19, 32), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 18), LocalTime.of(19, 37), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 25), LocalTime.of(19, 45), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 31), LocalTime.of(19, 51), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 38), LocalTime.of(19, 57), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 43), LocalTime.of(20, 04), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(19, 48), LocalTime.of(20, 12), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 01), LocalTime.of(20, 19), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 05), LocalTime.of(20, 25), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 12), LocalTime.of(20, 32), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 18), LocalTime.of(20, 37), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 25), LocalTime.of(20, 45), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 31), LocalTime.of(20, 51), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 38), LocalTime.of(20, 57), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 43), LocalTime.of(21, 03), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 51), LocalTime.of(21, 11), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(20, 58), LocalTime.of(21, 17), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 06), LocalTime.of(21, 29), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 16), LocalTime.of(21, 36), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 25), LocalTime.of(21, 45), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 36), LocalTime.of(21, 55), "快速"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 43), LocalTime.of(22, 03), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(21, 51), LocalTime.of(22, 11), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 00), LocalTime.of(22, 20), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 10), LocalTime.of(22, 30), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 20), LocalTime.of(22, 40), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 27), LocalTime.of(22, 50), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 42), LocalTime.of(23, 01), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(22, 52), LocalTime.of(23, 12), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(23, 02), LocalTime.of(23, 22), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(23, 12), LocalTime.of(23, 32), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(23, 22), LocalTime.of(23, 41), "普通"));
                    dao.insertTimeTable(new TimeTable(1, true, false, LocalTime.of(23, 32), LocalTime.of(23, 52), "普通"));

                    // JR中央線・下り・平日


                    // JR中央線・下り・休日


                    // 近鉄名古屋線・上り・平日


                    // 近鉄名古屋線・上り・休日


                    // 近鉄名古屋線・下り・平日


                    // 近鉄名古屋線・下り・休日
                });




            });
        }
    };



}
