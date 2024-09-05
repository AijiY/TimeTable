package com.example.timetable.model.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import java.io.File;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TimeTable.class, TrainLine.class, TrainCompany.class, Station.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TimeTableDatabase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();

    private static volatile TimeTableDatabase INSTANCE;  // シングルトン用に volatile 修飾子を追加

    public static TimeTableDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TimeTableDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeTableDatabase.class, "time_table_database")
                        .fallbackToDestructiveMigration()
                        .createFromAsset("databases/time_table.db")
                        .build();
                }
            }
        } else {
            Log.d("TimeTableDatabase", "Returning existing database instance");
        }
        return INSTANCE;
    }



}
