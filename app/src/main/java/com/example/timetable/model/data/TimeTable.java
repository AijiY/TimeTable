package com.example.timetable.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(tableName = "time_tables")
public class TimeTable {
  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "train_line_id")
  private int trainLineId;

  @ColumnInfo(name = "is_in_bound")
  private boolean isInBound;

  @ColumnInfo(name = "is_weekday")
  private boolean isWeekday;

  @ColumnInfo(name = "departure_time")
  private LocalTime departureTime;

  @ColumnInfo(name = "arrival_time")
  private LocalTime arrivalTime;

  @ColumnInfo(name = "train_type")
  private String trainType;

  public TimeTable(int trainLineId, boolean isInBound, boolean isWeekday, LocalTime departureTime, LocalTime arrivalTime, String trainType) {
    this.trainLineId = trainLineId;
    this.isInBound = isInBound;
    this.isWeekday = isWeekday;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.trainType = trainType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTrainLineId() {
    return trainLineId;
  }

  public void setTrainLineId(int trainLineId) {
    this.trainLineId = trainLineId;
  }

  public boolean isInBound() {
    return isInBound;
  }

  public void setInBound(boolean inBound) {
    isInBound = inBound;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public String getTrainType() {
    return trainType;
  }

  public void setTrainType(String trainType) {
    this.trainType = trainType;
  }

  public boolean isWeekday() {
    return isWeekday;
  }

  public void setWeekday(boolean weekday) {
    isWeekday = weekday;
  }
}
