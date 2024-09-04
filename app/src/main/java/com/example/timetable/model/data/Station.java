package com.example.timetable.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stations")
public class Station {
  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "company_id")
  private int companyId;

  public Station(String name, int companyId) {
    this.name = name;
    this.companyId = companyId;
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
}
