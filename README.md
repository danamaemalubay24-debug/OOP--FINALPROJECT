# Instrument Reservation System

A Java console app for managing an instrument inventory and tracking loans. A reservation checks out an instrument immediately. Returning it makes it available again and keeps the reservation in the history for the current session. Data resets when the app exits.

## Run on Windows

Open the project folder in your IDE, or open PowerShell and change to the `instrument-reservation` folder. Run this command from that folder.

```powershell
powershell -NoProfile -ExecutionPolicy RemoteSigned -File .\run.ps1
```

The launcher compiles the app and opens the menu. Enter `1` to add an instrument or `10` to exit.

Use a Java Development Kit (JDK) version 8 or newer. If the launcher cannot locate it, set `JAVA_HOME` to your JDK installation directory and run the command again.

## Run the tests

Run this command from the project folder to check inventory, reservations, input validation, and exit behavior against the included console examples.

```powershell
powershell -NoProfile -ExecutionPolicy RemoteSigned -File .\run.ps1 -Test
```

Each case pairs an `.input` file containing the lines a user types with an `.expected` file containing the resulting messages. The test runner compares the messages in order and reports the case name and differing output on failure.

## Project structure

```text
instrument-reservation/
  src/main/java/instrumentreservation/
    Main.java                 Menu and entry point
    console/                  Input, menu interface, and Exit
    instrument/               Instrument records and inventory choices
    reservation/              Reservation records and loan choices
  src/test/
    java/                      Console test runner
    cases/                     Input and expected output for each test
  run.ps1                      Build, run, and test on Windows
  WALKTHROUGH.md               Code, console examples, and Git commands
  .gitignore                   Keeps build output and the local JDK out of Git
```

See the [project walkthrough](WALKTHROUGH.md) for the menu choices and GitHub setup.
