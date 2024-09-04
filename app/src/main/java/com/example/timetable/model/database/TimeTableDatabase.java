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

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            // stations
//            // 1. Rename the existing table to a temporary table name
//            database.execSQL("ALTER TABLE stations RENAME TO stations_old");
//
//            // 2. Create the new table with the desired schema
//            database.execSQL("CREATE TABLE stations (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "name TEXT NOT NULL DEFAULT '駅', " +
//                "company_id INTEGER NOT NULL DEFAULT 0)");
//
//            // 3. Drop the old table
//            database.execSQL("DROP TABLE stations_old");
//
//            // train_companies
//            // 1. Rename the existing table to a temporary table name
//            database.execSQL("ALTER TABLE train_companies RENAME TO train_companies_old");
//
//            // 2. Create the new table with the desired schema
//            database.execSQL("CREATE TABLE train_companies (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "name TEXT NOT NULL DEFAULT '会社', " +
//                "nickname TEXT NOT NULL DEFAULT '会社')");
//
//            // 3. Drop the old table
//            database.execSQL("DROP TABLE train_companies_old");
//
//            // train_lines
//            // 1. Rename the existing table to a temporary table name
//            database.execSQL("ALTER TABLE train_lines RENAME TO train_lines_old");
//
//            // 2. Create the new table with the desired schema
//            database.execSQL("CREATE TABLE train_lines (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "name TEXT NOT NULL DEFAULT '線', " +
//                "company_id INTEGER NOT NULL DEFAULT 0, " +
//                "in_bound_terminal_station_id INTEGER NOT NULL DEFAULT 0, " +
//                "out_bound_terminal_station_id INTEGER NOT NULL DEFAULT 0)");
//
//            // 3. Drop the old table
//            database.execSQL("DROP TABLE train_lines_old");
//
//            // time_tables
//            // 1. Rename the existing table to a temporary table name
//            database.execSQL("ALTER TABLE time_tables RENAME TO time_tables_old");
//
//            // 2. Create the new table with the desired schema
//            database.execSQL("CREATE TABLE time_tables (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "train_line_id INTEGER NOT NULL DEFAULT 0, " +
//                "is_in_bound INTEGER NOT NULL DEFAULT 0, " +
//                "is_weekday INTEGER NOT NULL DEFAULT 0, " +
//                "departure_time INTEGER NOT NULL DEFAULT 0, " +
//                "arrival_time INTEGER NOT NULL DEFAULT 0, " +
//                "train_type TEXT NOT NULL DEFAULT '普通')");
//
//            // 3. Drop the old table
//            database.execSQL("DROP TABLE time_tables_old");
//        }
//    };

}
