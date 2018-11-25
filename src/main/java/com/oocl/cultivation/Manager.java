package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class Manager extends ParkingBoy {
    private Map<String, ParkingBoy> parkingBoys = new HashMap<String, ParkingBoy>();
    private ParkingLot[] parkingLots;
    private String errorMessage;

    public Manager(ParkingLot parkingLot) {
        super(parkingLot);
        this.parkingLots = new ParkingLot[1];
        this.parkingLots[0] = parkingLot;
    }

    public Manager(ParkingLot[] parkingLots) {
        super(parkingLots);
        this.parkingLots = parkingLots;
    }

    public void addParkingBoy(String boyType){
        ParkingBoy parkingBoy = null;
        if (boyType == "Parking Boy")
            parkingBoy = new ParkingBoy(this.parkingLots, this);
        else if(boyType == "Smart Parking Boy")
            parkingBoy = new SmartParkingBoy(this.parkingLots, this);
        else if(boyType == "Super Smart Parking Boy")
            parkingBoy = new SuperSmartParkingBoy(this.parkingLots, this);
        else
            errorMessage = "No such parking boy";
        parkingBoys.put(boyType, parkingBoy);
    }

    public void removeParkingBoy(String boyType, ParkingBoy parkingBoy){
        parkingBoys.remove(boyType, parkingBoy);
    }

    public ParkingBoy getParkingBoy(String boyType){
        return parkingBoys.get(boyType);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
