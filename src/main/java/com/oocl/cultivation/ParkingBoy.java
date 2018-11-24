package com.oocl.cultivation;

public class ParkingBoy {

    private ParkingLot[] parkingLots ;
    private ParkingLot parkingLot;
    private String lastErrorMessage;
    private int parkingLotNumber;

    public ParkingBoy(ParkingLot parkingLot) {
        parkingLots = new ParkingLot[1];
        parkingLots[0] = parkingLot;
        parkingLotNumber = 1;
        parkingLotNumber = parkingLots.length;
    }

    public ParkingBoy(ParkingLot[] parkingLots) {
        this.parkingLots = parkingLots;
        this.parkingLotNumber = parkingLots.length;
    }

    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        for (int index = 0; index<parkingLotNumber; index++) {
            if (parkingLots[index].getAvailableParkingPosition() >= 1) {
                ParkingTicket parkingTicket = new ParkingTicket();
                parkingLots[index].getCars().put(parkingTicket, car);
                lastErrorMessage = null;
                return parkingTicket;
            } else {
                if (index >= parkingLotNumber-1){
                    lastErrorMessage = "The parking lot is full.";
                    return null;
                }
            }
        }
        return null;
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket != null) {
            for (int index=0; index<parkingLotNumber; index++) {
                if (parkingLots[index].getCars().containsKey(ticket)) {
                    Car tempCar = parkingLots[index].getCars().get(ticket);
                    parkingLots[index].getCars().remove(ticket);
                    ticket.setUsed(true);
                    lastErrorMessage = null;
                    return tempCar;
                } else {
                    if (index >= parkingLotNumber-1) {
                        lastErrorMessage = "Unrecognized parking ticket.";
                        return null;
                    }
                }
            }
        }
        else{
            lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }
        return null;
        //throw new RuntimeException("Not implemented");
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
