package instrumentreservation.instrument;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;

public final class UpdateInstrumentInfo implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public UpdateInstrumentInfo(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "Update Instrument Info";
    }

    @Override
    public void execute() {
        Instrument instrument = input.readInstrument(inventory);
        if (instrument == null) {
            return;
        }

        String name = input.readLine("New name (Enter to keep " + instrument.getName() + "): ");
        String type = input.readLine("New type (Enter to keep " + instrument.getType() + "): ");
        instrument.updateDetails(name, type);
        System.out.println("Instrument information updated.");
    }
}
