package instrumentreservation.instrument;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;

public final class AddInstrument implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public AddInstrument(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "Add Instrument";
    }

    @Override
    public void execute() {
        String name = input.readRequired("Instrument name: ");
        String type = input.readRequired("Instrument type: ");
        Instrument instrument = inventory.add(name, type);
        System.out.printf("Instrument added with ID %d.%n", instrument.getId());
    }
}
