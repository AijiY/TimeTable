package com.example.timetable.model.domain;

import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import com.example.timetable.model.database.TimeTableDao;
import com.example.timetable.model.database.TimeTableDatabase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
  private TimeTableDatabase db;
  private TimeTableDao dao;

  public Service() {
    db = TimeTableDatabase.getDatabase(null);
    dao = db.timeTableDao();
  }

  public int getDepartureStationId(boolean isInBound, int trainLineId) {
    int departureStationId = 0;
    if (isInBound) {
      departureStationId = getStationById(getTrainLineById(trainLineId).getOutBoundTerminalStationId()).getId();
    } else {
      departureStationId = getStationById(getTrainLineById(trainLineId).getInBoundTerminalStationId()).getId();
    }
    return departureStationId;
  }

  public int getArrivalStationId(boolean isInBound, int trainLineId) {
    int arrivalStationId = 0;
    if (isInBound) {
      arrivalStationId = getStationById(getTrainLineById(trainLineId).getInBoundTerminalStationId()).getId();
    } else {
      arrivalStationId = getStationById(getTrainLineById(trainLineId).getOutBoundTerminalStationId()).getId();
    }
    return arrivalStationId;
  }

  public TimeTableDetail getTimeTableDetailById(int id) {
    TimeTable timeTable = getTimeTableById(id);
    return new TimeTableDetail(timeTable);
  }

  public List<TimeTableDetail> getTimeTableDetails() {
    List<TimeTable> timeTables = getTimeTables();
    List<TimeTableDetail> timeTableDetails = new ArrayList<>();
    for (TimeTable timeTable : timeTables) {
      timeTableDetails.add(new TimeTableDetail(timeTable));
    }
    return timeTableDetails;
  }

  public TimeTableDetail getTimeTableDetailFromTimeTable(TimeTable timeTable) {
    return new TimeTableDetail(timeTable);
  }

  public TimeTableDetail getNextTimeTableDetail(LocalDateTime departureDateTime, boolean isInBound, int trainLineId) {
    TimeTable nextTimeTable = getNextTimeTable(isInBound, trainLineId, departureDateTime);
    return new TimeTableDetail(nextTimeTable);
  }

//  以下、dao内の機能コピー
//  Insert
  public void insertStation(Station station) {
    dao.insertStation(station);
  }

  public void insertTimeTable(TimeTable timeTable) {
    dao.insertTimeTable(timeTable);
  }

  public void insertTrainLine(TrainLine trainLine) {
    dao.insertTrainLine(trainLine);
  }

  public void insertTrainCompany(TrainCompany trainCompany) {
    dao.insertTrainCompany(trainCompany);
  }

//  Query(all)
  public List<Station> getStations() {
    return dao.getStations();
  }

  public List<TimeTable> getTimeTables() {
    return dao.getTimeTables();
  }

  public List<TrainLine> getTrainLines() {
    return dao.getTrainLines();
  }

  public List<TrainCompany> getTrainCompanies() {
    return dao.getTrainCompanies();
  }

//  Query(by id)
  public Station getStationById(int id) {
    return dao.getStationById(id);
  }

  public TimeTable getTimeTableById(int id) {
    return dao.getTimeTableById(id);
  }

  public TrainLine getTrainLineById(int id) {
    return dao.getTrainLineById(id);
  }

  public TrainCompany getTrainCompanyById(int id) {
    return dao.getTrainCompanyById(id);
  }

//  Query(extra)
  public TimeTable getNextTimeTable(boolean isInBound, int trainLineId, LocalDateTime departureDateTime) {
    return dao.getNextTimeTable(isInBound, trainLineId, departureDateTime);
  }

//  Update
  public void updateStation(Station station) {
    dao.updateStation(station);
  }

  public void updateTimeTable(TimeTable timeTable) {
    dao.updateTimeTable(timeTable);
  }

  public void updateTrainLine(TrainLine trainLine) {
    dao.updateTrainLine(trainLine);
  }

  public void updateTrainCompany(TrainCompany trainCompany) {
    dao.updateTrainCompany(trainCompany);
  }

//  Delete
  public void deleteStation(Station station) {
    dao.deleteStation(station);
  }

  public void deleteTimeTable(TimeTable timeTable) {
    dao.deleteTimeTable(timeTable);
  }

  public void deleteTrainLine(TrainLine trainLine) {
    dao.deleteTrainLine(trainLine);
  }

  public void deleteTrainCompany(TrainCompany trainCompany) {
    dao.deleteTrainCompany(trainCompany);
  }

}
