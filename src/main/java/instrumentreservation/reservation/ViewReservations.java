package instrumentreservation.reservation;

import instrumentreservation.console.MenuChoice;

public final class ViewReservations implements MenuChoice {
    private final ReservationHistory history;

    public ViewReservations(ReservationHistory history) {
        this.history = history;
    }

    @Override
    public String label() {
        return "View Reservations";
    }

    @Override
    public void execute() {
        ReservationDisplay.print(history.all(), "No reservations have been made.");
    }
}
