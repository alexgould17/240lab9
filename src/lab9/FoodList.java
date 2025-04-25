package lab9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 * Class representing a collection of <code>Food</code> items. Implemented internally as a singly-linked list per lab specifications, it does
 * <strong>not</strong> implement all standard linked list functionality, solely that necessary for lab specifications. Can be iterated over.
 * Trivial getters not documented.
 *
 * @author Alexander Gould
 * @version 1.0
 */
public class FoodList implements Iterable<Food> {
    /**
     * Inner class representing singly-linked list nodes. Both fields are made public for easier access since the whole class is hidden.
     *
     * @author Alexander Gould
     * @version 1.0
     */
    protected class FoodListNode {
        /**
         * The <code>Food</code> item stored at this node.
         */
        public Food element;

        /**
         * Pointer to the next node
         */
        public FoodListNode next;

        /**
         * Default constructor, sets <code>element</code> &amp; <code>next</code> to <code>null</code>.
         */
        public FoodListNode() {element=null;next=null;}

        /**
         * Constructor with <code>Food</code> instance. Sets element to the provided instance &amp; <code>next</code> to <code>null</code>.
         * @param element <code>Food</code> instance to store in the node
         */
        public FoodListNode(Food element) {this.element = element;next=null;}
    }

    /**
     * Length of the list.
     */
    protected int size;

    /**
     * Pointer to the first node in the list.
     */
    protected FoodListNode start;

    /**
     * Default constructor. Makes an empty list.
     */
    public FoodList() {
        start = null;
        size = 0;
    }

    /**
     * Constructor accepting a <code>Food</code> instance. Creates a list of size 1 &amp; sets the first node to a node containing <code>f</code>.
     * @param f <code>Food</code> instance to begin the list with.
     */
    public FoodList(Food f) {
        start = new FoodListNode(f);
        size = 1;
    }

    /**
     * Adds a <code>Food</code> instance to the end of the list. O(n) time.
     * @param f <code>Food</code> instance to be added.
     */
    public void add(Food f) {
        FoodListNode temp = new FoodListNode(f);

        // Case: empty list
        if(start == null) {
            start = temp;
        } else {

            // Otherwise, loop to the end & add this item
            FoodListNode current = start;
            while(current.next != null)
                current = current.next;
            current.next = temp;
        }

        // Keep the length up-to-date
        size++;
    }

    /**
     * Getter for <code>size</code>
     * @return the length of this list
     */
    public int getLength() {
        return size;
    }

    /**
     * Deletes all items from the list with Calories >= <code>cals</code>.
     * @param cals The Calorie threshold
     */
    public void removeHighCalFoods(int cals) {
        // Start at the beginning & set a flag for whether we need to update the link to start or not
        FoodListNode node = start, prev = null;
        boolean isStart = true;

        // Loop thru all elements & check if the Calories meet the threshold
        while(node != null) {
            if(node.element.getCals() >= cals) {

                // End of the list? If so, null this node entirely. Otherwise, link the next node
                if(node.next == null) {
                    node = null;
                    if(prev != null)
                        prev.next = null;
                } else {
                    FoodListNode temp = node;
                    node.element = temp.next.element;
                    node.next = temp.next.next;
                }

                // Reduce size
                size--;

                // Keep start pointer updated
                if(isStart)
                    start = node;
            } else {

                // If the Calorie threshold is not met, skip to the next item.
                prev = node;
                node = node.next;
                isStart = false;
            }
        }
    }

    /**
     * Picks foods at random from the list based on the passed param &amp; returns a meal consisting of the chosen foods. Can select duplicate entries.
     * An attempt has been made to uniformly distribute the chances across all <code>Food</code>s in the list, but it may not have resulted in a
     * truly rigorous pattern of randomization. A moe efficient implementation of this might use a min-heap instead of an <code>ArrayList</code> for
     * the random foods.
     * @param numFoods the number of <code>Food</code>s to be included in the random meal.
     * @return an array of <code>Food</code>s of size <code>numFoods</code> containing the randomly-selected meal.
     */
    public Food[] randomMeal(int numFoods) {
        // Make sure numFoods is positive
        if(numFoods <= 0)
            throw new IllegalArgumentException("numFoods must be > 0!");

        // Array of Foods, ArrayList of Integers (position of item on list) & Random generator
        Food[] meal = new Food[numFoods];
        ArrayList<Integer> foodnums = new ArrayList<Integer>();
        Random rand = new Random();

        // Fill foodnums with numFoods random Integers & sort it (each one corresponds to a Food instance to be chosen)
        for(int i = 0; i < numFoods; i++) {
            foodnums.add(rand.nextInt(size));
        }
        foodnums.sort(Integer::compareTo);

        // Loop thru the list & fill meal
        int i = 0, list = 0;
        FoodListNode current = start;
        while(i < numFoods) {

            // If the list index equals the position of the current food we are trying to find,
            if(list == foodnums.get(i)) {

                // then add it to the meal & go to the next index in the array of randoms.
                meal[i] = current.element;
                i++;
            } else {

                // Otherwise, increment the list index & go to the next list node.
                list++;
                current = current.next;
            }
        }

        return meal;
    }

    /**
     * Searches for a <code>Food</code> by name (case-insensitive) in the list.
     * @param name the name of the <code>Food</code> to search for.
     * @return the <code>Food</code> instance if found in the list,<code>null</code> if not.
     */
    public Food findByName(String name) {
        // Iterate over the list & return the Food if its name matches the passed String
        for(Food f : this) {
            if(f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }

        return null;
    }

    /**
     * @return "Empty list!" if the list is empty, otherwise each <code>Food</code> in the list, in order, on its own line.
     */
    @Override
    public String toString() {
        if(start == null)
            return "Empty list!\n";
        StringBuilder s = new StringBuilder();

        for(Food f : this) {
            s.append(f);
            s.append('\n');
        }

        return s.toString();
    }

    /**
     * @return a new <code>Iterator&lt;Food&gt;</code> instance that starts at the beginning of this list.
     */
    @Override
    public Iterator<Food> iterator() {
        return new Iterator<Food>() {
            private FoodListNode current = start;

            @Override
            public boolean hasNext() {
                return !(current == null);
            }

            @Override
            public Food next() {
                Food f = current.element;
                current = current.next;
                return f;
            }
        };
    }
}
