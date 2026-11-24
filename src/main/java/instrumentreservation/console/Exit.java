package instrumentreservation.console;

public final class Exit implements MenuChoice {
    @Override
    public String label() {
        return "Exit";
    }

    @Override
    public void execute() {
        System.out.println("Goodbye!");
    }
}
