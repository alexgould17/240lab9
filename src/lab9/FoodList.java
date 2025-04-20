package lab9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class FoodList {
    /* Inner class representing singly-linked list nodes */
    protected class FoodListNode {
        public Food element;
        public FoodListNode next; // pointer to next item in list. null if end.

        // Default constructor, sets element to null
        public FoodListNode() {
            element = null;
        }

        // Constructor with Food object
        public FoodListNode(Food element) {
            this.element = element;
        }
    }

    /* FoodList instance vars */
    protected int size;
    protected FoodListNode start, end;

    // Default constructor: empty list
    public FoodList() {
        start = null;
        end = null;
        size = 0;
    }

    // Constructor starting from a single Food item
    public FoodList(Food f) {
        start = new FoodListNode(f);
        end = start;
        size = 1;
    }

    // Add a Food to the list
    public void add(Food f) {
        FoodListNode oldEnd = end;
        end = new FoodListNode(f);
        oldEnd.next = end;
        size++;
    }

    public int getLength() {
        return size;
    }

    // Delete all items from the list with kcal >= cals
    public void removeHighCalFoods(int cals) {
        FoodListNode node = start;
        // Loop thru all elements & check if the calories meet the threshold
        while(node != null) {
            if(node.element.getCals() >= cals) {
                // End of the list? If so, null this node entirely. Otherwise, link the next node
                if(node.next == null) {
                    node.element = null;
                    node = null;
                } else {
                    FoodListNode temp = node;
                    node.element = node.next.element;
                    node.next = node.next.next;
                    node = temp.next;
                }
            }
        }
    }

    public Food[] randomMeal(int numFoods) throws IllegalArgumentException {
        // Make sure numFoods is positive
        if(numFoods <= 0)
            throw new IllegalArgumentException("numFoods must be > 0!");

        // Array of Foods, ArrayList of Integers (position of item on list) & Random generator
        Food[] meal = new Food[numFoods];
        ArrayList<Integer> foodnums = new ArrayList<Integer>();
        Random rand = new Random();

        // Fill foodnums with numFoods random Integers & sort it
        for(int i = 0; i < numFoods; i++) {
            foodnums.add(rand.nextInt(size));
        }
        foodnums.sort(Integer::compareTo);

        // Loop thru the list & fill meal
        int i = 0, list = 0;
        FoodListNode current = start;
        while(i < numFoods) {
            if(list == foodnums.get(i)) {
                meal[i] = current.element;
                i++;
            } else {
                list++;
                current = current.next;
            }
        }

        return meal;
    }

    public Food findByName(String name) {
        FoodListNode current = start;

        // Loop & search
        while(current != null) {
            if(current.element.getName().equalsIgnoreCase(name)) {
                return current.element;
            }
            current = current.next;
        }

        return null;
    }

    // Returns the strings of all the Foods in the list on their own line
    @Override
    public String toString() {
        String s = "";
        FoodListNode current = start;

        // Loop over list & concat
        while(current != null) {
            s += current.element + "\n";
            current = current.next;
        }

        return s;
    }
}
