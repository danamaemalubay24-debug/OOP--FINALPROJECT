package instrumentreservation.reservation;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;
import instrumentreservation.instrument.Instrument;
import instrumentreservation.instrument.InstrumentInventory;

import java.util.ArrayList;
import java.util.List;

public final class MyBorrowedItems implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public MyBorrowedItems(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "My Borrowed Items";
    }

    @Override
    public void execute() {
        String borrower = input.readRequired("Enter your name: ");
        List<Reservation> borrowedItems = new ArrayList<>();
        for (Instrument instrument : inventory.all()) {
            Reservation reservation = instrument.getActiveReservation();
            if (reservation != null && reservation.getBorrower().equalsIgnoreCase(borrower)) {
                borrowedItems.add(reservation);
            }
        }
        ReservationDisplay.print(borrowedItems, "No borrowed items found for " + borrower + ".");
    }
}
