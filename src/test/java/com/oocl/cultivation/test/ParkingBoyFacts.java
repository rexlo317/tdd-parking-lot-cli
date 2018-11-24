package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);
        Car fetched = parkingBoy.fetch(ticket);

        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstTicket = parkingBoy.park(firstCar);
        ParkingTicket secondTicket = parkingBoy.park(secondCar);

        Car fetchedByFirstTicket = parkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = parkingBoy.fetch(secondTicket);

        assertSame(firstCar, fetchedByFirstTicket);
        assertSame(secondCar, fetchedBySecondTicket);
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket ticket = parkingBoy.park(car);

        assertNull(parkingBoy.fetch(wrongTicket));
        assertSame(car, parkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        parkingBoy.fetch(wrongTicket);
        String message = parkingBoy.getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        parkingBoy.fetch(wrongTicket);
        assertNotNull(parkingBoy.getLastErrorMessage());

        ParkingTicket ticket = parkingBoy.park(new Car());
        assertNotNull(ticket);
        assertNull(parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);

        assertNull(parkingBoy.fetch(null));
        assertSame(car, parkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.fetch(null);

        assertEquals(
            "Please provide your parking ticket.",
            parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);
        parkingBoy.fetch(ticket);

        assertNull(parkingBoy.fetch(ticket));
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);
        parkingBoy.fetch(ticket);
        parkingBoy.fetch(ticket);

        assertEquals(
            "Unrecognized parking ticket.",
            parkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.park(new Car());

        assertNull(parkingBoy.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.park(new Car());
        parkingBoy.park(new Car());

        assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
    }
    @Test
    void should_park_to_second_lot() {
        final int capacity = 1;
        final int lotNumbers = 2;
        Car car = new Car();
        Car car2 = new Car();
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        for (int index=0; index<lotNumbers; index++) {
            parkingLots[index] = new ParkingLot(capacity);
        }
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        ParkingTicket ticket = parkingBoy.park(car);
        ParkingTicket ticket2 = parkingBoy.park(car2);
        Car fetched = parkingBoy.fetch(ticket);
        Car fetched2 = parkingBoy.fetch(ticket2);

        assertSame(fetched, car);
        assertSame(fetched2, car2);
    }
    @Test
    void should_second_lot_full() {
        final int capacity = 1;
        final int lotNumbers = 2;
        Car car = new Car();
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        for (int index=0; index<lotNumbers; index++) {
            parkingLots[index] = new ParkingLot(capacity);
        }
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.park(new Car());
        parkingBoy.park(new Car());
        parkingBoy.park(new Car());

        assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_second_lot_2_cars() {
        final int capacity = 2;
        final int lotNumbers = 2;
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        for (int index=0; index<lotNumbers; index++) {
            parkingLots[index] = new ParkingLot(capacity);
        }
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        for (int index=0; index<lotNumbers*capacity; index++)
            parkingBoy.park(new Car());

        assertEquals(2, parkingLots[0].getCars().size());
        assertEquals(2, parkingLots[1].getCars().size());
    }

    @Test
    void should_all_car_park_to_lot_2() {
        final int capacity = 2;
        final int lotNumbers = 2;
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        parkingLots[0] = new ParkingLot(capacity);
        parkingLots[1] = new ParkingLot(10);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        for (int index=0; index<lotNumbers*capacity; index++)
            smartParkingBoy.park(new Car());

        assertEquals(0, parkingLots[0].getCars().size());
        assertEquals(4, parkingLots[1].getCars().size());
    }

    @Test
    void should_3_cars_park_to_lot_2_then_lot_1() {
        final int capacity = 2;
        final int lotNumbers = 2;
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        parkingLots[0] = new ParkingLot(capacity);
        parkingLots[1] = new ParkingLot(5);
        ParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        for (int index=0; index<lotNumbers*capacity; index++)
            smartParkingBoy.park(new Car());

        assertEquals(1, parkingLots[0].getCars().size());
        assertEquals(3, parkingLots[1].getCars().size());
    }

    @Test
    void should_park_to_lot1_then_lot2_even_lot1_has_more_empty_place() {
        final int lotNumbers = 2;
        ParkingLot[] parkingLots = new ParkingLot[lotNumbers];
        parkingLots[0] = new ParkingLot(10);
        parkingLots[1] = new ParkingLot(1);
        ParkingBoy smartParkingBoy = new SuperSmartParkingBoy(parkingLots);
        smartParkingBoy.park(new Car());
        //Now Lot1 has 9 empty space with 90% empty ratio.
        //Lot2 has 1 empty space with 100% empty ratio.
        //Should park to lot 2.
        smartParkingBoy.park(new Car());

        assertEquals(1, parkingLots[1].getCars().size());
    }

    @Test
    void should_manager_assign_normal_boy_to_work() {
        ParkingLot[] parkingLots = new ParkingLot[2];
        parkingLots[0] = new ParkingLot(10);
        parkingLots[1] = new ParkingLot(1);
        Manager manager = new Manager(parkingLots);
        manager.addParkingBoy("Parking Boy", new ParkingBoy(parkingLots));
        Car car = new Car();

        ParkingTicket ticket = manager.getParkingBoy("Parking Boy").park(car);
        Car fetched = manager.getParkingBoy("Parking Boy").fetch(ticket);

        assertSame(fetched, car);
    }

    @Test
    void should_manager_assign_smart_boy_to_work_and_fetch_last_car() {
        ParkingLot[] parkingLots = new ParkingLot[2];
        parkingLots[0] = new ParkingLot(2);
        parkingLots[1] = new ParkingLot(10);
        Manager manager = new Manager(parkingLots);
        manager.addParkingBoy("Smart Parking Boy", new SmartParkingBoy(parkingLots));
        Car car = new Car();
        ParkingTicket ticket = null;

        for (int index=0; index<4; index++)
            ticket = manager.getParkingBoy("Smart Parking Boy").park(car);

        Car fetched = manager.getParkingBoy("Smart Parking Boy").fetch(ticket);

        assertSame(fetched, car);
        assertEquals(0, parkingLots[0].getCars().size());
        assertEquals(3, parkingLots[1].getCars().size());
    }

    @Test
    void should_manager_assign_super_smart_boy_to_work_and_fetch_last_car() {
        ParkingLot[] parkingLots = new ParkingLot[2];
        parkingLots[0] = new ParkingLot(10);
        parkingLots[1] = new ParkingLot(1);
        Manager manager = new Manager(parkingLots);
        manager.addParkingBoy("Super Smart Parking Boy", new SuperSmartParkingBoy(parkingLots));
        Car car = new Car();
        ParkingTicket ticket = null;

        ticket = manager.getParkingBoy("Super Smart Parking Boy").park(car);

        //Now Lot1 has 9 empty space with 90% empty ratio.
        //Lot2 has 1 empty space with 100% empty ratio.
        //Should park to lot 2.

        ticket = manager.getParkingBoy("Super Smart Parking Boy").park(car);

        assertEquals(1, parkingLots[0].getCars().size());
        assertEquals(1, parkingLots[1].getCars().size());

        //Should get the car from lot 2
        Car fetched = manager.getParkingBoy("Super Smart Parking Boy").fetch(ticket);
        assertSame(fetched, car);
        assertEquals(0, parkingLots[1].getCars().size());
    }

    @Test
    void should_manager_park() {
        ParkingLot[] parkingLots = new ParkingLot[2];
        parkingLots[0] = new ParkingLot(1);
        Manager manager = new Manager(parkingLots);
        Car car = new Car();
        ParkingTicket ticket = manager.park(car);

        Car fetched = manager.fetch(ticket);

        assertSame(fetched, car);
    }

    @Test
    void should_manager_report_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        Manager manager = new Manager(parkingLot);
        manager.addParkingBoy("Parking Boy", new ParkingBoy(parkingLot));
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket ticket = manager.getParkingBoy("Parking Boy").park(car);

        assertNull(manager.getParkingBoy("Parking Boy").fetch(wrongTicket));
        assertSame(car, manager.getParkingBoy("Parking Boy").fetch(ticket));
    }

    @Test
    void should_manager_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        Manager manager = new Manager(parkingLot);
        manager.addParkingBoy("Parking Boy", new ParkingBoy(parkingLot));
        ParkingTicket wrongTicket = new ParkingTicket();

        manager.getParkingBoy("Parking Boy").fetch(wrongTicket);
        String message = manager.getParkingBoy("Parking Boy").getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    @Test
    void should_manager_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        Manager manager = new Manager(parkingLot);
        manager.addParkingBoy("Parking Boy", new ParkingBoy(parkingLot));
        ParkingTicket wrongTicket = new ParkingTicket();

        manager.getParkingBoy("Parking Boy").fetch(wrongTicket);
        assertNotNull(manager.getParkingBoy("Parking Boy").getLastErrorMessage());

        ParkingTicket ticket =manager.getParkingBoy("Parking Boy").park(new Car());
        assertNotNull(ticket);
        assertNull(manager.getParkingBoy("Parking Boy").getLastErrorMessage());
    }

    @Test
    void should_manager_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        Manager manager = new Manager(parkingLot);
        manager.addParkingBoy("Parking Boy", new ParkingBoy(parkingLot));
        Car car = new Car();

        ParkingTicket ticket = manager.getParkingBoy("Parking Boy").park(car);

        assertNull(manager.getParkingBoy("Parking Boy").fetch(null));
        assertSame(car, manager.getParkingBoy("Parking Boy").fetch(ticket));
    }

}
