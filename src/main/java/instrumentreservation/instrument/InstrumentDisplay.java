package instrumentreservation.instrument;

import java.util.Collection;

final class InstrumentDisplay {
    private InstrumentDisplay() {
    }

    static void print(Collection<Instrument> instruments, String emptyMessage) {
        if (instruments.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Instrument instrument : instruments) {
            String status = instrument.isBorrowed() ? "Borrowed" : "Available";
            System.out.printf("[%d] %s | %s | %s%n", instrument.getId(), instrument.getName(),
                    instrument.getType(), status);
        }
    }
}
