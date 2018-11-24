package com.oocl.cultivation;

public class SuperSmartParkingBoy extends ParkingBoy {

    private ParkingLot[] parkingLots ;
    private String lastErrorMessage;
    private int parkingLotNumber;

    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
        this.parkingLots[0] = parkingLot;
        this.parkingLotNumber = 1;
    }

    public SuperSmartParkingBoy(ParkingLot[] parkingLots) {
        super(parkingLots);
        this.parkingLots = parkingLots;
        this.parkingLotNumber = parkingLots.length;
    }
    @Override
    public ParkingTicket park(Car car) {
        for (int index = 0; index<this.parkingLotNumber; index++) {
            if (this.parkingLots[index].getAvailableParkingPosition() >= 1) {
                break;
            }
            if (index >= this.parkingLotNumber-1){
                this.lastErrorMessage = "The parking lot is full.";
                return null;
            }
        }
        int maxEmptyCapacityLotNumber = getMax();
        ParkingTicket parkingTicket = new ParkingTicket();
        this.parkingLots[maxEmptyCapacityLotNumber].getCars().put(parkingTicket, car);
        this.lastErrorMessage = null;
        return parkingTicket;
    }

    private int getMax(){
        int maxNumber = 0;
        for (int index = 1; index<this.parkingLotNumber; index++){
            if(getEmptyRatio(maxNumber)< getEmptyRatio(index))
                maxNumber = index;
        }
        return maxNumber;
    }

    private float getEmptyRatio(int arrayNum){
        return this.parkingLots[arrayNum].getAvailableParkingPosition() / this.parkingLots[arrayNum].getCapacity();
    }
}
