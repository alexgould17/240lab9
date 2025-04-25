package lab9;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;

/**
 * Class representing a food entrée that may, for example, be served in a cafeteria.
 * Each instance stores the name, the food group it belongs to, the energy content (in kilocalories, hereinafter Calories) &amp; what percentage
 * of the daily recommended value the food represents. The daily percentage is stored as a floating-point number but
 * displayed as a percentage rounded to the nearest whole number.
 * The class stores a small amount of information specific to the lab it was created for (see readme) in order to facilitate
 * terminal output.
 * Getter &amp; setter methods are only documented where non-trivial.
 *
 * @author Alexander Gould
 * @version 1.0
 */
public class Food {
    /**
     * Default value for <code>String</code> fields of instances of this class.
     */
    public static final String STRING_DEFAULT = "NONE";

    /**
     * Ordered array of headers corresponding to each field for terminal display.
     */
    public static final String[] tableHeaders = {
            "Name", "Food Group", "Calories", "Daily %"
    };

    /**
     * Ordered array of widths corresponding to each display column for terminal output. Originally initialized to the width of the <code>String</code>s
     * in <code>tableHeaders</code>, inputting values via <code>fromTextLine</code> updates the widths.
     */
    public static int[] columnWidths = {
            tableHeaders[0].length(), tableHeaders[1].length(),
            tableHeaders[2].length(), tableHeaders[3].length()
    };

    /**
     * Used to format <code>daily</code> for terminal output.
     */
    protected static NumberFormat percent = NumberFormat.getPercentInstance(Locale.US);

    /**
     * Used to format <code>kcal</code> for terminal output.
     */
    protected static NumberFormat integer = NumberFormat.getIntegerInstance(Locale.US);

    /**
     * The name of this instance
     */
    protected String name;

    /**
     * The food group this instance belongs to
     */
    protected String group;

    /**
     * The energy content in Calories of this instance
     */
    protected int kcal;

    /**
     * The daily percentage this instance fulfills
     */
    protected double daily;

    /**
     * Class method to create a <code>Food</code> instance from a line of text input. Automatically updates <code>columnWidths</code> as necessary.
     * Throws an <code>InputMismatchException</code> for a <code>null</code> line, not having 4 space-separated values, or inconsistencies in
     * the numerical types.
     * @param line A single line of text containing a <code>Food</code> item. Expects <code>String String int double</code>, single space-delimited.
     * @return a <code>Food</code> item based on the data
     */
    public static Food fromTextLine(String line) {
        // Make sure line is not null
        if(line == null)
            throw new InputMismatchException("line is null!");

        // Break up line by its designated delimiter: a single space
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

    /**
     * Default constructor: sets name &amp; group = STRING_DEFAULT, kcal=0 &amp; daily=0.0
     */
    public Food() {
        name = STRING_DEFAULT;
        group = STRING_DEFAULT;
        kcal = 0;
        daily = 0.0;
    }

    /**
     * Full constructor. Uses setters to validate all passed data.
     * @param name Name of the food
     * @param group Food group it belongs to
     * @param kcal The amount of energy in Calories in a serving of the food
     * @param daily Daily recommended percentage of the food per serving
     */
    public Food(String name, String group, int kcal, double daily) {
        this.setName(name);
        this.setGroup(group);
        this.setCals(kcal);
        this.setDailyPercentage(daily);
    }

    /**
     * Getter for <code>name</code>
     * @return the name of this instance
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for <code>name</code>
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>group</code>
     * @return the food group of this instance
     */
    public String getGroup() {
        return group;
    }

    /**
     * Setter for <code>group</code>
     * @param group the new food group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Getter for <code>kcal</code>
     * @return the Calories of this instance
     */
    public int getCals() {
        return kcal;
    }

    /**
     * Setter for <code>kcal</code>. Ensures passed param is >= 0.
     * @param cals the Calorie value
     * @return success of operation
     */
    public boolean setCals(int cals) {
        if(cals >= 0) {
            kcal = cals;
            return true;
        }
        return false;
    }

    /**
     * Getter for <code>daily</code>
     * @return the daily percentage of this instance
     */
    public double getDailyPercentage() {
        return daily;
    }

    /**
     * Setter for <code>daily</code>. Ensures passed param is >= 0.
     * @param percentage the daily percentage
     * @return success of operation
     */
    public boolean setDailyPercentage(double percentage) {
        if(percentage >= 0) {
            daily = percentage;
            return true;
        }
        return false;
    }

    /**
     * @return a <code>String</code> containing a single line with all the instance data formatted to the current <code>columnWidth</code>s
     * for terminal display.
     */
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
