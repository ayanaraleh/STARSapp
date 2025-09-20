package ui;

import model.*;

import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// STARS Application
// Managers can: 
// Add or remove a new telescope to catalogue
// Specify type of telescope
// View all telescopes in catalogue 
// Users can:
// Filter catalogue by experience level

// Represents the UI for STARS Application, 
// instantiate scanner inputs and 
// catalogue so they remain private and unchanged
public class StarsApp {
    private static final String RESO_STORE = "./data/reservations.json";
    private static final String CATALOGUE_STORE = "./data/catalogue.json";

    private final Scanner scanner;
    private TelescopeCatalogue catalogue;
    private ReservationManager reservationManager;
    private final CatalogueJsonReader catalogueJsonReader;
    private final ReservationJsonReader reservationJsonReader;
    private final CatalogueJsonWriter catalogueJsonWriter;
    private final ReservationJsonWriter reservationJsonWriter;
    private boolean runStars = true;

    // EFFECTS: Constructs UI, and starts application in main
    public StarsApp() {
        this.scanner = new Scanner(System.in);
        this.catalogue = new TelescopeCatalogue();
        this.reservationManager = new ReservationManager();
        this.reservationJsonReader = new ReservationJsonReader(RESO_STORE, catalogue);
        this.catalogueJsonReader = new CatalogueJsonReader(CATALOGUE_STORE);
        this.catalogueJsonWriter = new CatalogueJsonWriter(CATALOGUE_STORE);
        this.reservationJsonWriter = new ReservationJsonWriter(RESO_STORE);
        runApp();

    }

    // EFFECTS: runs the STARSApp in main for new instance
    private void runApp() {
        // switches to false and exits app when valid input given
        while (runStars) {
            startingMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            processChoice(choice);
        }
    }

    // EFFECTS: Displays application startup menu
    private void startingMenu() {
        System.out.println("\nWelcome to STARS");
        System.out.println("1. Manager - Access Manager Tools");
        System.out.println("2. Users - Rent a telescope!");
        System.out.println("3. Users - Cancel a reservation");
        System.out.println("4: Load my saved data");
        System.out.println("5: Save my data");
        System.out.println("6. Exit STARS");
        System.out.print("Please enter your choice: ");
    }

    // EFFECTS: Processes user choice from startup menu
    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                managerTools();
                break;
            case 2:
                findTelescopes();
                break;
            case 3:
                cancel();
                break;
            case 4:
                loadData();
                break;
            case 5:
                saveData();
                break;
            case 6:
                System.out.println("Exiting STARS, have a stellar day!");
                runStars = false;
                break;
            default:
                System.out.println("INVALID CHOICE SELECTED: Please try again.");
        }
    }

    // EFFECTS: Management tools for catalogue
    private void managerTools() {
        System.out.println("1. Add a telescope");
        System.out.println("2. Remove a telescope");
        System.out.println("3. View all telescopes");
        System.out.println("4. View rental reservations");
        System.out.print("Select an action: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addTelescope();
                break;
            case 2:
                removeTelescope();
                break;
            case 3:
                viewMyCatalogue();
                break;
            case 4:
                viewReservations();
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }

    }

    // EFFECTS: allows manager to add new telescope
    private void addTelescope() {
        System.out.print("Enter the telescope name: ");
        String name = scanner.nextLine();

        TelescopeType type = chooseTelescopeType();
        if (type == null) {
            System.out.println("INVALID TYPE: Telescope not added.");
            return;

        }
        Telescope t = new Telescope(name, type);
        catalogue.addTelescope(t);
        System.out.println(t.getTelescopeName() + "| ID: " + t.getTelescopeId() + " added!.");
    }

    // EFFECTS: allows manager to remove telescope
    // with given id
    private void removeTelescope() {
        System.out.print("Enter telescope id to be removed: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean wasRemoved = catalogue.removeTelescope(id);

        if (wasRemoved) {
            System.out.println("Telescope ID: " + id + " was removed successfully.");
        } else {
            System.out.println("Telescope removal unsuccessful.");
        }

    }

    // EFFECTS: allows manager to specify telescope type
    private TelescopeType chooseTelescopeType() {
        System.out.println("Select telescope type");
        TelescopeType[] types = TelescopeType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + "." + types[i]);

        }
        System.out.print("Enter type (1-" + types.length + ")");
        int select = scanner.nextInt();
        scanner.nextLine();

        if (select >= 1 && select <= types.length) {
            return types[select - 1]; // return correct 0 base index

        }
        return null;

    }

    // EFFECTS: allows manager to view their current
    // catalogue and their availabilities.

    private void viewMyCatalogue() {
        List<Telescope> myCatalogue = catalogue.viewCatalogue();
        showTelescopes("Your Telescope Catalogue:", myCatalogue);

    }

    // EFFECTS: allows Users to find a telescope to rent based on their
    // indicated experience level.
    private void findTelescopes() {
        catalogueChoices();
        int choice = scanner.nextInt();
        scanner.nextLine();

        String experienceLevel = processExperienceInput(choice);

        List<Telescope> filteredTelescopes = catalogue.filterByExperience(experienceLevel);
        showTelescopes("Available Telescopes that are " + experienceLevel, filteredTelescopes);
        System.out.print("\nProceed with requesting a rental? (Y/N): ");
        String choice2 = scanner.next();
        scanner.nextLine();

        switch (choice2) {
            case "Y":
                rentalRequest(filteredTelescopes);
                break;

            case "N":
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("INVALID CHOICE SELECTED: Please try again.");
        }
    }

    private String processExperienceInput(int choice) {
        String experienceLevel;
        switch (choice) {
            case 1:
                experienceLevel = "BEGINNER";
                break;
            case 2:
                experienceLevel = "INTERMEDIATE";
                break;
            case 3:
                experienceLevel = "ADVANCED";
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return null;
        }
        return experienceLevel;
    }

    // EFFECTS: Allows users to request a rental for chosen telescope
    private void rentalRequest(List<Telescope> telescopes) {
        System.out.print("Enter your chosen telescope ID: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        for (Telescope t : telescopes) {
            if (t.getTelescopeId() != choice) {
                System.out.println("INVALID CHOICE SELECTED: Please try again.");
                return;
            } else {
                System.out.print("Enter your name: ");
                String username = scanner.nextLine();

                Reservation newReservation = reservationManager.makeReservation(username, t);
                if (newReservation != null) {
                    System.out.println("Reservation successful!: " + newReservation);
                } else {
                    System.out.println("Error: Could not make reservation, please try again.");
                }
            }
        }
    }

    // EFFECTS: Allow users to cancel a reservation request
    private void cancel() {
        System.out.print("Enter your reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        boolean canceled = reservationManager.cancelReservation(reservationId);

        if (canceled) {
            System.out.println("Reservation successfully canceled.");
        } else {
            System.out.println("Invalid reservation ID or reservation already canceled.");
        }
    }

    // EFFECTS: Displays catalogue's user choices:
    private void catalogueChoices() {
        System.out.println("Select experience level:");
        System.out.println("1. Beginner");
        System.out.println("2. Intermediate");
        System.out.println("3. Advanced");
        System.out.print("Enter your choice [1-3]: ");

    }

    // EFFECTS: Displays all reservation requests
    private void viewReservations() {
        System.out.println("\nCurrent Reservations:");
        if (reservationManager.getReservations().size() == 0) {
            System.out.println("No reservations available.");
        } else {
            for (Reservation r : reservationManager.getReservations()) {
                System.out.println(r);
            }
        }
    }

    private void loadData() {
        try {
            catalogue = catalogueJsonReader.read();
            reservationManager = reservationJsonReader.read();
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Warning: Could not load saved data.");
        }
    }

    private void saveData() {
        try {
            catalogueJsonWriter.open();
            catalogueJsonWriter.write(catalogue);
            catalogueJsonWriter.close();

            reservationJsonWriter.open();
            reservationJsonWriter.write(reservationManager);
            reservationJsonWriter.close();

            System.out.println("Data saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Warning: Could not save user data.");
        }
    }

    // EFFECTS: Displays any filtered or non-filtered
    // list of telescopes with their availability status
    private void showTelescopes(String header, List<Telescope> scopes) {
        System.out.println("\n" + header + ".");
        if (scopes.size() == 0) {
            System.out.println("No telescopes found.");
        } else {
            for (Telescope t : scopes) {
                String status;
                if (t.getAvailabilityStatus()) {
                    status = "Available";
                } else {
                    status = "Reserved";
                }
                System.out.println("ID: " + t.getTelescopeId() + "| " + t.getTelescopeName()
                        + "| " + t.getTelescopeType() + "| " + status);

            }

        }

    }

}