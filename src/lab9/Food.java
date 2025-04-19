package lab9;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;

public class Food {
    /* Class vars */
    public static final String STRING_DEFAULT = "NONE";

    public static final String[] tableHeaders = {
            "Name", "Food Group", "Calories", "Daily %"
    }; // table headers for display

    public static int[] columnWidths = {
            tableHeaders[0].length(), tableHeaders[1].length(),
            tableHeaders[2].length(), tableHeaders[3].length()
    }; // table display column widths

    // For formatting daily & kcal respectively into Strings
    protected static NumberFormat percent = NumberFormat.getPercentInstance(Locale.US);
    protected static NumberFormat integer = NumberFormat.getIntegerInstance(Locale.US);

    /* Instance vars */
    protected String name, group;
    protected int kcal;
    protected double daily;

    // Class method to create a Food object from a line of text input.
    // Automatically updates columnWidths as necessary.
    public static Food fromTextLine(String line) throws InputMismatchException {
        // Make sure line is not null
        if(line == null)
            throw new InputMismatchException("line is null!");

        // Break our line up by a single space
        String[] vars = line.split(" ");

        // Make sure we have the right number of variables
        if(vars.length != 4)
            throw new InputMismatchException("line does not have 4 space-separated values!");

        // Declare our placeholder variables
        String name, group;
        int kcal;
        double daily;

        // Try to decode the values from vars
        try {
            name = vars[0];
            group = vars[1];
            kcal = Integer.parseInt(vars[2]);
            daily = Double.parseDouble(vars[3]);
        } catch(NumberFormatException e) {
            throw new InputMismatchException("Numerical value in line does not match input format!");
        }

        // Turn kcal & daily to Strings & update columnWidths as necessary
        String cals = integer.format(kcal);
        String per = percent.format(daily);
        if(name.length() > columnWidths[0]) columnWidths[0] = name.length();
        if(group.length() > columnWidths[1]) columnWidths[1] = group.length();
        if(cals.length() > columnWidths[2]) columnWidths[2] = cals.length();
        if(per.length() > columnWidths[3]) columnWidths[3] = per.length();

        // Create a Food item from the input values & return it
        return new Food(name, group, kcal, daily);
    }

    // Default constructor: name & group = STRING_DEFAULT, kcal=0, daily=0.0
    public Food() {
        name = STRING_DEFAULT;
        group = STRING_DEFAULT;
        kcal = 0;
        daily = 0.0;
    }

    // Full constructor
    public Food(String name, String group, int kcal, double daily) {
        this.name = name;
        this.group = group;
        this.kcal = kcal;
        this.daily = daily;
    }

    // Getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCals() {
        return kcal;
    }

    // Checks to ensure param is >= 0
    public boolean setCals(int cals) {
        if(cals >= 0) {
            kcal = cals;
            return true;
        }
        return false;
    }

    public double getDailyPercentage() {
        return daily;
    }

    // Checks to ensure param is >= 0.0
    public boolean setDailyPercentage(double percentage) {
        if(percentage >= 0) {
            daily = percentage;
            return true;
        }
        return false;
    }

    // Formats the instance data to be output in a table according to static columnWidth specs
    @Override
    public String toString() {
        // Pre-format kcal & daily
        String cals = integer.format(kcal);
        String per = percent.format(daily);

        // Pad each String to the specified columnWidth & pad 1 space in between each column
        return name + " ".repeat(columnWidths[0] - name.length() + 1)
                + group + " ".repeat(columnWidths[1] - group.length() + 1)
                + cals + " ".repeat(columnWidths[2] - cals.length() + 1)
                + per + " ".repeat(columnWidths[3] - per.length());
    }
}
