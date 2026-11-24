package instrumentreservation.console;

import instrumentreservation.instrument.Instrument;
import instrumentreservation.instrument.InstrumentInventory;

import java.util.Scanner;

public final class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readNumber(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException invalidNumber) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    public String readRequired(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field is required.");
        }
    }

    public Instrument readInstrument(InstrumentInventory inventory) {
        int id = readNumber("Instrument ID: ");
        Instrument instrument = inventory.find(id);
        if (instrument == null) {
            System.out.printf("No instrument has ID %d.%n", id);
        }
        return instrument;
    }
}
