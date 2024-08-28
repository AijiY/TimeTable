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

  private TimeTableDatabase db;
  private TimeTableDao dao;

  public TimeTableDetail(TimeTable timeTable) {
    this.timeTable = timeTable;
    db = TimeTableDatabase.getDatabase(null);
    dao = db.timeTableDao();

    TrainLine trainLine = dao.getTrainLineById(timeTable.getTrainLineId());

    trainCompanyNickname = dao.getTrainCompanyById(trainLine.getCompanyId()).getNickname();
    trainLineName = trainLine.getName();

    departureStationId = Service.getDepartureStationId(timeTable.isInBound(), timeTable.getTrainLineId(), dao);
    arrivalStationId = Service.getArrivalStationId(timeTable.isInBound(), timeTable.getTrainLineId(), dao);

    departureStationName = dao.getStationById(departureStationId).getName();
    arrivalStationName = dao.getStationById(arrivalStationId).getName();

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
