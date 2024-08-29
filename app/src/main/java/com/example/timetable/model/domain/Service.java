package com.example.timetable.model.domain;

import android.content.Context;
import com.example.timetable.model.data.Station;
import com.example.timetable.model.data.TimeTable;
import com.example.timetable.model.data.TrainCompany;
import com.example.timetable.model.data.TrainLine;
import com.example.timetable.model.database.TimeTableDao;
import com.example.timetable.model.database.TimeTableDatabase;
import com.example.timetable.utils.DateUtils;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
  private TimeTableDatabase db;
  private TimeTableDao dao;
  private Context context;

  public Service(Context context) {
    db = TimeTableDatabase.getDatabase(context);
    dao = db.timeTableDao();
    this.context = context;
  }

  public int getDepartureStationId(boolean isInBound, int trainLineId) {
    int departureStationId;
    if (isInBound) {
      departureStationId = getStationById(getTrainLineById(trainLineId).getOutBoundTerminalStationId()).getId();
    } else {
      departureStationId = getStationById(getTrainLineById(trainLineId).getInBoundTerminalStationId()).getId();
    }
    return departureStationId;
  }

  public int getArrivalStationId(boolean isInBound, int trainLineId) {
    int arrivalStationId;
    if (isInBound) {
      arrivalStationId = getStationById(getTrainLineById(trainLineId).getInBoundTerminalStationId()).getId();
    } else {
      arrivalStationId = getStationById(getTrainLineById(trainLineId).getOutBoundTerminalStationId()).getId();
    }
    return arrivalStationId;
  }

  public TimeTableDetail getTimeTableDetailById(int id) {
    TimeTable timeTable = getTimeTableById(id);
    return new TimeTableDetail(timeTable, context);
  }

  public List<TimeTableDetail> getTimeTableDetails() {
    List<TimeTable> timeTables = getTimeTables();
    List<TimeTableDetail> timeTableDetails = new ArrayList<>();
    for (TimeTable timeTable : timeTables) {
      timeTableDetails.add(new TimeTableDetail(timeTable, context));
    }
    return timeTableDetails;
  }

  public TimeTableDetail getTimeTableDetailFromTimeTable(TimeTable timeTable) {
    return new TimeTableDetail(timeTable, context);
  }

  public TimeTableDetail getNextTimeTableDetail(LocalDateTime dateTime, boolean isInBound, int trainLineId) {
    boolean isTodayWeekday = DateUtils.isWeekday(dateTime);
    TimeTable nextTimeTable = getNextTimeTable(isInBound, isTodayWeekday, trainLineId, dateTime.toLocalTime());
    if (nextTimeTable == null) {
//      次の日が休日がどうか判断
      boolean isNextDayWeekday = DateUtils.isWeekday(dateTime.plusDays(1));
      nextTimeTable = getFirstTrainTimeTable(isInBound, isNextDayWeekday, trainLineId);
    }
    return new TimeTableDetail(nextTimeTable, context);
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
  public TimeTable getNextTimeTable(boolean isInBound, boolean isWeekday, int trainLineId, LocalTime time) {
    return dao.getNextTimeTable(isInBound, isWeekday, trainLineId, time);
  }

  public TimeTable getFirstTrainTimeTable(boolean isInBound, boolean isWeekday, int trainLineId) {
    return dao.getFirstTrainTimeTable(isInBound, isWeekday, trainLineId);
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
