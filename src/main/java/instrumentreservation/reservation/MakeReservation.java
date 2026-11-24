package instrumentreservation.reservation;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;
import instrumentreservation.instrument.Instrument;
import instrumentreservation.instrument.InstrumentInventory;

public final class MakeReservation implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;
    private final ReservationHistory history;

    public MakeReservation(ConsoleInput input, InstrumentInventory inventory, ReservationHistory history) {
        this.input = input;
        this.inventory = inventory;
        this.history = history;
    }

    @Override
    public String label() {
        return "Make Reservation";
    }

    @Override
    public void execute() {
        Instrument instrument = input.readInstrument(inventory);
        if (instrument == null) {
            return;
        }
        if (instrument.isBorrowed()) {
            System.out.println("This instrument is already borrowed.");
            return;
        }

        String borrower = input.readRequired("Borrower's name: ");
        Reservation reservation = history.checkout(instrument, borrower);
        System.out.printf("Reservation #%d created for %s.%n", reservation.getId(), borrower);
    }
}
