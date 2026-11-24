package instrumentreservation.instrument;

import instrumentreservation.reservation.Reservation;

public final class Instrument {
    private final int id;
    private String name;
    private String type;
    private Reservation activeReservation;

    Instrument(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Reservation getActiveReservation() {
        return activeReservation;
    }

    public boolean isBorrowed() {
        return activeReservation != null;
    }

    public void updateDetails(String name, String type) {
        if (!name.isEmpty()) {
            this.name = name;
        }
        if (!type.isEmpty()) {
            this.type = type;
        }
    }

    public Reservation checkout(int reservationId, String borrower) {
        activeReservation = new Reservation(reservationId, id, name, borrower);
        return activeReservation;
    }

    public boolean checkIn() {
        if (!isBorrowed()) {
            return false;
        }

        activeReservation.markReturned();
        activeReservation = null;
        return true;
    }
}
