package com.example.timetable.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "train_companies")
public class TrainCompany {
  @PrimaryKey(autoGenerate = true)
  private int id;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "nickname")
  private String nickname;

  public TrainCompany(String name, String nickname) {
    this.name = name;
    this.nickname = nickname;
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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}
