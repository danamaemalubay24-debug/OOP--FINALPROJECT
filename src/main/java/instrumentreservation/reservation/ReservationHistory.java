package instrumentreservation.reservation;

import instrumentreservation.instrument.Instrument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ReservationHistory {
    private final List<Reservation> reservations = new ArrayList<>();
    private int nextId = 1;

    Reservation checkout(Instrument instrument, String borrower) {
        Reservation reservation = instrument.checkout(nextId++, borrower);
        reservations.add(reservation);
        return reservation;
    }

    List<Reservation> all() {
        return Collections.unmodifiableList(reservations);
    }
}
