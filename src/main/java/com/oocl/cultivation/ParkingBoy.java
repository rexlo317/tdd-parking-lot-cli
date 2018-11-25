package com.oocl.cultivation;

public class ParkingBoy {

    private ParkingLot[] parkingLots ;
    private String lastErrorMessage;
    private int parkingLotNumber;
    private Manager manager;

    public ParkingBoy(ParkingLot parkingLot){
        this.parkingLots = new ParkingLot[1];
        this.parkingLots[0] = parkingLot;
        this.parkingLotNumber = 1;
    }

    public ParkingBoy(ParkingLot[] parkingLots){
        this.parkingLots = parkingLots;
        this.parkingLotNumber = parkingLots.length;
    }

    public ParkingBoy(ParkingLot parkingLot, Manager manager) {
        this.parkingLots = new ParkingLot[1];
        this.parkingLots[0] = parkingLot;
        this.parkingLotNumber = 1;
        this.manager = manager;
    }

    public ParkingBoy(ParkingLot[] parkingLots, Manager manager) {
        this.parkingLots = parkingLots;
        this.parkingLotNumber = parkingLots.length;
        this.manager = manager;
    }

    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        for (int index = 0; index<this.parkingLotNumber; index++) {
            if (this.parkingLots[index].getAvailableParkingPosition() >= 1) {
                ParkingTicket parkingTicket = new ParkingTicket();
                this.parkingLots[index].getCars().put(parkingTicket, car);
                if(manager!=null)
                    this.manager.setErrorMessage(null);
                this.lastErrorMessage = null;
                return parkingTicket;
            } else {
                if (index >= this.parkingLotNumber-1){
                    this.lastErrorMessage = "The parking lot is full.";
                    if(manager!=null) {
                        this.manager.setErrorMessage("The parking lot is full.");
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket != null) {
            for (int index=0; index<this.parkingLotNumber; index++) {
                if (this.parkingLots[index].getCars().containsKey(ticket)) {
                    Car tempCar = this.parkingLots[index].getCars().get(ticket);
                    this.parkingLots[index].getCars().remove(ticket);
                    ticket.setUsed(true);
                    if(manager!=null)
                        this.manager.setErrorMessage(null);
                    this.lastErrorMessage = null;
                    return tempCar;
                } else {
                    if (index >= this.parkingLotNumber-1) {
                        this.lastErrorMessage = "Unrecognized parking ticket.";
                        if(manager!=null)
                            this.manager.setErrorMessage("Unrecognized parking ticket.");
                        return null;
                    }
                }
            }
        }
        else{
            this.lastErrorMessage = "Please provide your parking ticket.";
            if(manager!=null)
                this.manager.setErrorMessage("Please provide your parking ticket.");
            return null;
        }
        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
