package com.example.yiyao.pojo;

import java.io.Serializable;

/**
 * Created by licae on 2016/6/19.
 */
public class KaQuan implements Serializable {
   private int id;
    private String name;
    private String startTime;
    private String EndTime;

    public KaQuan() {
    }

    public KaQuan(int id, String name, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        EndTime = endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
