package instrumentreservation;

import instrumentreservation.console.ConsoleInput;
import instrumentreservation.console.Exit;
import instrumentreservation.console.MenuChoice;
import instrumentreservation.instrument.AddInstrument;
import instrumentreservation.instrument.DeleteInstrument;
import instrumentreservation.instrument.InstrumentInventory;
import instrumentreservation.instrument.SearchInstrument;
import instrumentreservation.instrument.UpdateInstrumentInfo;
import instrumentreservation.instrument.ViewInstruments;
import instrumentreservation.reservation.MakeReservation;
import instrumentreservation.reservation.MyBorrowedItems;
import instrumentreservation.reservation.ReservationHistory;
import instrumentreservation.reservation.ReturnInstrument;
import instrumentreservation.reservation.ViewReservations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Main {
    private static final Exit EXIT = new Exit();

    private final ConsoleInput input;
    private final List<MenuChoice> choices;

    private Main(ConsoleInput input) {
        this.input = input;
        InstrumentInventory inventory = new InstrumentInventory();
        ReservationHistory history = new ReservationHistory();
        choices = Arrays.asList(
                new AddInstrument(input, inventory),
                new ViewInstruments(inventory),
                new SearchInstrument(input, inventory),
                new MakeReservation(input, inventory, history),
                new ViewReservations(history),
                new MyBorrowedItems(input, inventory),
                new ReturnInstrument(input, inventory),
                new UpdateInstrumentInfo(input, inventory),
                new DeleteInstrument(input, inventory),
                EXIT
        );
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(new ConsoleInput(scanner)).run();
        } catch (NoSuchElementException endOfInput) {
            System.out.println();
            EXIT.execute();
        }
    }

    private void run() {
        while (true) {
            printMenu();
            int choice = input.readNumber("Enter your choice: ");
            if (choice < 1 || choice > choices.size()) {
                System.out.printf("Choose a menu option from 1 to %d.%n", choices.size());
                continue;
            }

            MenuChoice selected = choices.get(choice - 1);
            selected.execute();
            if (selected == EXIT) {
                return;
            }
        }
    }

    private void printMenu() {
        System.out.println("\nINSTRUMENT RESERVATION SYSTEM");
        for (int index = 0; index < choices.size(); index++) {
            System.out.printf("%d. %s%n", index + 1, choices.get(index).label());
        }
    }
}
