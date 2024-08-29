package com.example.timetable.model.domain;

import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainLine;
import com.example.timetable.model.database.TimeTableDao;
import com.example.timetable.model.database.TimeTableDatabase;

public final class TimeTableDetail {
  private TimeTable timeTable;
  private String trainCompanyNickname;
  private String trainLineName;
  private int departureStationId;
  private int arrivalStationId;
  private String departureStationName;
  private String arrivalStationName;

  private Service service;

  public TimeTableDetail(TimeTable timeTable) {
    this.timeTable = timeTable;
    service = new Service();

    TrainLine trainLine = service.getTrainLineById(timeTable.getTrainLineId());

    trainCompanyNickname = service.getTrainCompanyById(trainLine.getCompanyId()).getNickname();
    trainLineName = trainLine.getName();

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
