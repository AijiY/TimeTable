package com.example.timetable.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "train_lines")
public class TrainLine {
  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "company_id")
  private int companyId;

  @ColumnInfo(name = "in_bound_terminal_station_id")
  private int inBoundTerminalStationId;

  @ColumnInfo(name = "out_bound_terminal_station_id")
  private int outBoundTerminalStationId;

  @ColumnInfo(name = "line_color")
  private String lineColor;

  public TrainLine(String name, int companyId, int inBoundTerminalStationId, int outBoundTerminalStationId) {
    this.name = name;
    this.companyId = companyId;
    this.inBoundTerminalStationId = inBoundTerminalStationId;
    this.outBoundTerminalStationId = outBoundTerminalStationId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public int getInBoundTerminalStationId() {
    return inBoundTerminalStationId;
  }

  public void setInBoundTerminalStationId(int inBoundTerminalStationId) {
    this.inBoundTerminalStationId = inBoundTerminalStationId;
  }

  public int getOutBoundTerminalStationId() {
    return outBoundTerminalStationId;
  }

  public void setOutBoundTerminalStationId(int outBoundTerminalStationId) {
    this.outBoundTerminalStationId = outBoundTerminalStationId;
  }

  public String getLineColor() {
    return lineColor;
  }

  public void setLineColor(String lineColor) {
    this.lineColor = lineColor;
  }
}
