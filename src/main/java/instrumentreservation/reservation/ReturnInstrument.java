package instrumentreservation.reservation;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;
import instrumentreservation.instrument.Instrument;
import instrumentreservation.instrument.InstrumentInventory;

public final class ReturnInstrument implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public ReturnInstrument(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "Return Instrument";
    }

    @Override
    public void execute() {
        Instrument instrument = input.readInstrument(inventory);
        if (instrument == null) {
            return;
        }
        if (!instrument.checkIn()) {
            System.out.println("This instrument is not currently borrowed.");
            return;
        }

        System.out.printf("%s was returned and is now available.%n", instrument.getName());
    }
}
