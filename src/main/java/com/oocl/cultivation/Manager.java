package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class Manager extends ParkingBoy {
    private Map<String, ParkingBoy> parkingBoys = new HashMap<String, ParkingBoy>();
    private String errorMessage;

    public Manager(ParkingLot parkingLot) {
        super(parkingLot);
    }

    public Manager(ParkingLot[] parkingLots) {
        super(parkingLots);
    }

    public void addParkingBoy(String boyType, ParkingBoy parkingBoy){
        parkingBoys.put(boyType, parkingBoy);
    }

    public void removeParkingBoy(String boyType, ParkingBoy parkingBoy){
        parkingBoys.remove(boyType, parkingBoy);
    }

    public ParkingBoy getParkingBoy(String boyType){
        return parkingBoys.get(boyType);
    }

}
