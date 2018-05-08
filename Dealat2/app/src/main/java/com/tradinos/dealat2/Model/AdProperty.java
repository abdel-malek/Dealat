package com.tradinos.dealat2.Model;

/**
 * Created by developer on 08.03.18.
 */

public class AdProperty extends Ad {
    private int roomNum, floorNum, floorsCount; // floorNum indicates in which floor an apartment is
    // floorsCount indicates how many floors a building has
    private double space;
    private boolean furnished;
    private String state;

    public AdProperty(){
        state = "";
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
