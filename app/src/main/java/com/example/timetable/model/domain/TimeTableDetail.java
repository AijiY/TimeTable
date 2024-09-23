package com.example.timetable.model.domain;

import android.content.Context;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainLine;
import com.example.timetable.model.database.TimeTableDao;
import com.example.timetable.model.database.TimeTableDatabase;

public final class TimeTableDetail {
  private TimeTable timeTable;
  private String trainCompanyNickname;
  private String trainLineName;
  private String trainLineColor;
  private int departureStationId;
  private int arrivalStationId;
  private String departureStationName;
  private String arrivalStationName;

  private Service service;

  public TimeTableDetail(TimeTable timeTable, Context context) {
    this.timeTable = timeTable;
    service = new Service(context);

    TrainLine trainLine = service.getTrainLineById(timeTable.getTrainLineId());

    trainCompanyNickname = service.getTrainCompanyById(trainLine.getCompanyId()).getNickname();
    trainLineName = trainLine.getName();
    trainLineColor = trainLine.getLineColor();

    departureStationId = service.getDepartureStationId(timeTable.isInBound(), timeTable.getTrainLineId());
    arrivalStationId = service.getArrivalStationId(timeTable.isInBound(), timeTable.getTrainLineId());

    departureStationName = service.getStationById(departureStationId).getName();
    arrivalStationName = service.getStationById(arrivalStationId).getName();

  }

  public TimeTable getTimeTable() {
    return timeTable;
  }

  public String getTrainCompanyNickname() {
    return trainCompanyNickname;
  }

  public String getTrainLineName() {
    return trainLineName;
  }

  public String getTrainLineColor() {
    return trainLineColor;
  }

  public String getDepartureStationName() {
    return departureStationName;
  }

  public String getArrivalStationName() {
    return arrivalStationName;
  }

  public int getDepartureStationId() {
    return departureStationId;
  }

  public int getArrivalStationId() {
    return arrivalStationId;
  }
}
