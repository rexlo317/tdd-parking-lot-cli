package com.oocl.cultivation;

public class ParkingTicket {
    boolean isUsed;
    public ParkingTicket(){
        isUsed = false;
    };

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
