package instrumentreservation.instrument;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.MenuChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SearchInstrument implements MenuChoice {
    private final ConsoleInput input;
    private final InstrumentInventory inventory;

    public SearchInstrument(ConsoleInput input, InstrumentInventory inventory) {
        this.input = input;
        this.inventory = inventory;
    }

    @Override
    public String label() {
        return "Search Instrument";
    }

    @Override
    public void execute() {
        String query = input.readRequired("Enter an instrument ID, name, or type: ").toLowerCase(Locale.ROOT);
        List<Instrument> matches = new ArrayList<>();
        for (Instrument instrument : inventory.all()) {
            if (Integer.toString(instrument.getId()).equals(query)
                    || instrument.getName().toLowerCase(Locale.ROOT).contains(query)
                    || instrument.getType().toLowerCase(Locale.ROOT).contains(query)) {
                matches.add(instrument);
            }
        }
        InstrumentDisplay.print(matches, "No instruments match your search.");
    }
}
