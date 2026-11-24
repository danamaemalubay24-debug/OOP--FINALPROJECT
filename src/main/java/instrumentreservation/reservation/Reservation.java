package instrumentreservation.reservation;

public final class Reservation {
    private final int id;
    private final int instrumentId;
    private final String instrumentName;
    private final String borrower;
    private boolean returned;

    public Reservation(int id, int instrumentId, String instrumentName, String borrower) {
        this.id = id;
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
        this.borrower = borrower;
    }

    int getId() {
        return id;
    }

    int getInstrumentId() {
        return instrumentId;
    }

    String getInstrumentName() {
        return instrumentName;
    }

    String getBorrower() {
        return borrower;
    }

    boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        returned = true;
    }
}
