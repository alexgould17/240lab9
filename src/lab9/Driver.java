package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    static String menuTitle = "Welcome to Parkland Meal Selector";
    static String[] menuOptions = {
            "1. List food database",
            "2. Create meal by manual selection",
            "3. Create meal by random selection",
            "4. Remove foods high in calorie",
            "5. Exit"
    };
    static String menuInput = "Enter selection: ";
    static int menuWidth = menuTitle.length();

    static String dbpath = "foods.txt";

    public static void main(String[] args) {
        // Update menuWidth
        for(String option : menuOptions) {
            if(menuWidth < option.length())
                menuWidth = option.length();
        }

        // Create our list
        FoodList foods = new FoodList();

        // Try to load data
        System.out.println("Starting Parkland Meal Selector");
        System.out.println("Attempting to load data...");
        try {
            File db = new File(dbpath);
            Scanner foodDB = new Scanner(db);
            while(foodDB.hasNext()) {
                foods.add(Food.fromTextLine(foodDB.nextLine()));
            }
            foodDB.close();
            db = null;
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + dbpath + "\" not found, exiting.");
            System.exit(1);
        }

        // Main menu loop
        int menuChoice = 0;
        Scanner terminal = new Scanner(System.in);
        while(menuChoice != 5) {

            // Display menu & get input
            try {
                displayMainMenu();
                menuChoice = terminal.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter 1-5 for a menu selection!");
                terminal.skip(".*");
            }

            // Break out by input
            switch (menuChoice) {
                case 1:
                    // Print header
                    printEqualBar(menuWidth);
                    printTableHeader();
                    printEqualBar(menuWidth);

                    // Print foods
                    System.out.print(foods);
                    break;
                case 2:
                    int i = 0, numFoods = 3;
                    Food[] meal = new Food[numFoods];

                    // Input loop
                    while(i < numFoods) {
                        System.out.print("Enter name of a food to search for: ");
                        String name = terminal.next();
                        Food f = foods.findByName(name);
                        if(f == null)
                            System.out.println("Food \"" + name + "\" not found!");
                        else
                            meal[i++] = f;
                    }

                    // Output loop
                    System.out.println("\nYour selected meal:");
                    for(Food f : meal) {
                        System.out.println(f);
                    }
                    System.out.println();
                    break;
                case 3:
                    Food[] randommeal = foods.randomMeal(3);

                    // Output loop
                    System.out.println("\nYour randomized meal:");
                    for(Food f : randommeal) {
                        System.out.println(f);
                    }
                    System.out.println();
                    break;
                case 4:
                    // Input loop, ensure cals > 0
                    int cals = 0;
                    while(cals <= 0) {
                        try {
                            System.out.println("Enter a calorie limit as an integer > 0.");
                            System.out.print("All foods >= this will be removed from the list: ");
                            cals = terminal.nextInt();
                        } catch(InputMismatchException e) {
                            System.out.println("Please enter an integer!");
                            terminal.skip(".*");
                        }
                    }

                    foods.removeHighCalFoods(cals);
                    System.out.println("Foods over " + cals + " calories removed.");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Enter a number between 1-5!");
            }
        }

        // Close & exit
        terminal.close();
        System.out.println("\nThanks for running Parkland Meal Selector, exiting now!");
        System.exit(0);
    }

    public static void printEqualBar(int width) {
        System.out.println("=".repeat(width));
    }

    public static void printTableHeader() {
        System.out.print(Food.tableHeaders[0] + " ".repeat(
                Food.columnWidths[0]- Food.tableHeaders[0].length() + 1));
        System.out.print(Food.tableHeaders[1] + " ".repeat(
                Food.columnWidths[1]- Food.tableHeaders[1].length() + 1));
        System.out.print(Food.tableHeaders[2] + " ".repeat(
                Food.columnWidths[2]- Food.tableHeaders[2].length() + 1));
        System.out.println(Food.tableHeaders[3] + " ".repeat(
                Food.columnWidths[3]- Food.tableHeaders[3].length()));
    }

    public static void displayMainMenu() {
        // Title
        printEqualBar(menuWidth);
        System.out.println(menuTitle);
        printEqualBar(menuWidth);

        // Options
        for(String option : menuOptions) {
            System.out.println(option);
        }

        // Blank line & input
        System.out.print("\n" + menuInput);
    }
}
