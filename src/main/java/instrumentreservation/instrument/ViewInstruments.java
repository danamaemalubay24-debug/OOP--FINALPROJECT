package instrumentreservation.instrument;

import instrumentreservation.console.MenuChoice;

public final class ViewInstruments implements MenuChoice {
    private final InstrumentInventory inventory;

    public ViewInstruments(InstrumentInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "View Instruments";
    }

    @Override
    public void execute() {
        InstrumentDisplay.print(inventory.all(), "No instruments have been added.");
    }
}
