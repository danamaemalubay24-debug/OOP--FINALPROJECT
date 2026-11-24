package instrumentreservation.reservation;

import java.util.List;

final class ReservationDisplay {
    private ReservationDisplay() {
    }

    static void print(List<Reservation> reservations, String emptyMessage) {
        if (reservations.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Reservation reservation : reservations) {
            String status = reservation.isReturned() ? "Returned" : "Borrowed";
            System.out.printf("#%d | Instrument [%d] %s | %s | %s%n", reservation.getId(),
                    reservation.getInstrumentId(), reservation.getInstrumentName(), reservation.getBorrower(), status);
        }
    }
}
