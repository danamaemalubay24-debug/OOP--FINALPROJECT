package instrumentreservation.instrument;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;

public final class DeleteInstrument implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public DeleteInstrument(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "Delete Instrument";
    }

    @Override
    public void execute() {
        Instrument instrument = input.readInstrument(inventory);
        if (instrument == null) {
            return;
        }
        if (instrument.isBorrowed()) {
            System.out.println("Return this instrument before deleting it.");
            return;
        }

        inventory.delete(instrument.getId());
        System.out.printf("%s was deleted.%n", instrument.getName());
    }
}
