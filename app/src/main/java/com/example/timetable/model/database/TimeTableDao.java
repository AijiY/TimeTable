package com.example.timetable.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TimeTableDao {
//  Insert
  @Insert
  void insertStation(Station station);

  @Insert
  void insertTrainLine(TrainLine trainLine);

  @Insert
  void insertTrainCompany(TrainCompany trainCompany);

  @Insert
  void insertTimeTable(TimeTable timeTable);

// Query(all)
  @Query("SELECT * FROM stations")
  List<Station> getStations();

  @Query("SELECT * FROM train_lines")
  List<TrainLine> getTrainLines();

  @Query("SELECT * FROM train_companies")
  List<TrainCompany> getTrainCompanies();

  @Query("SELECT * FROM time_tables")
  List<TimeTable> getTimeTables();

// Query(by id)
  @Query("SELECT * FROM stations WHERE id = :id")
  Station getStationById(int id);

  @Query("SELECT * FROM train_lines WHERE id = :id")
  TrainLine getTrainLineById(int id);

  @Query("SELECT * FROM train_companies WHERE id = :id")
  TrainCompany getTrainCompanyById(int id);

  @Query("SELECT * FROM time_tables WHERE id = :id")
  TimeTable getTimeTableById(int id);

//  Query(extra)
//  time_tablesからin_boundとis_weekdayとtrain_line_idを指定して、departureDateTimeの直後のtime_tableを取得
  @Query("SELECT * FROM time_tables WHERE is_in_bound = :isInBound AND is_weekday = :isWeekday AND train_line_id = :trainLineId AND departure_time > :departureDateTime ORDER BY departure_time LIMIT 1")
  TimeTable getNextTimeTable(boolean isInBound, boolean isWeekday, int trainLineId, LocalDateTime departureDateTime);

//  time_tablesからin_boundとis_weekdayとtrain_line_idを指定して、始発を取得
  @Query("SELECT * FROM time_tables WHERE is_in_bound = :isInBound AND is_weekday = :isWeekday AND train_line_id = :trainLineId ORDER BY departure_time LIMIT 1")
  TimeTable getFirstTrainTimeTable(boolean isInBound, boolean isWeekday, int trainLineId);

//  Update
  @Update
  void updateStation(Station station);

  @Update
  void updateTrainLine(TrainLine trainLine);

  @Update
  void updateTrainCompany(TrainCompany trainCompany);

  @Update
  void updateTimeTable(TimeTable timeTable);

//  Delete
  @Delete
  void deleteStation(Station station);

  @Delete
  void deleteTrainLine(TrainLine trainLine);

  @Delete
  void deleteTrainCompany(TrainCompany trainCompany);

  @Delete
  void deleteTimeTable(TimeTable timeTable);
}
