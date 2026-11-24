package instrumentreservation.instrument;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class InstrumentInventory {
    private final Map<Integer, Instrument> instruments = new LinkedHashMap<>();
    private int nextInstrumentId = 1;

    public Instrument add(String name, String type) {
        Instrument instrument = new Instrument(nextInstrumentId++, name, type);
        instruments.put(instrument.getId(), instrument);
        return instrument;
    }

    public Instrument find(int id) {
        return instruments.get(id);
    }

    public Collection<Instrument> all() {
        return Collections.unmodifiableCollection(instruments.values());
    }

    public void delete(int id) {
        instruments.remove(id);
    }
}
