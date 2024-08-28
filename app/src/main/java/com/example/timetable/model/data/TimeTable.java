package com.example.timetable.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDateTime;

@Entity(tableName = "time_tables")
public class TimeTable {
  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "train_line_id")
  private int trainLineId;

  @ColumnInfo(name = "is_in_bound")
  private boolean isInBound;

  @ColumnInfo(name = "departure_time")
  private LocalDateTime departureTime;

  @ColumnInfo(name = "arrival_time")
  private LocalDateTime arrivalTime;

  public TimeTable(int trainLineId, boolean isInBound, LocalDateTime departureTime, LocalDateTime arrivalTime) {
    this.trainLineId = trainLineId;
    this.isInBound = isInBound;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
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

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }
}
