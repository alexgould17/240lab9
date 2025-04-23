package lab9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class FoodList implements Iterable<Food> {
    /* Inner class representing singly-linked list nodes */
    protected class FoodListNode {
        public Food element;
        public FoodListNode next; // pointer to next item in list. null if end.

        // Default constructor, sets element to null
        public FoodListNode() {element=null;next=null;}

        // Constructor with Food object
        public FoodListNode(Food element) {this.element = element;next=null;}
    }

    /* FoodList instance vars */
    protected int size;
    protected FoodListNode start;

    // Default constructor: empty list
    public FoodList() {
        start = null;
        size = 0;
    }

    // Constructor starting from a single Food item
    public FoodList(Food f) {
        start = new FoodListNode(f);
        size = 1;
    }

    // Add a Food to the list
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
        size++;
    }

    public int getLength() {
        return size;
    }

    // Delete all items from the list with kcal >= cals
    public void removeHighCalFoods(int cals) {
        FoodListNode node = start, prev = null;
        boolean isStart = true;

        // Loop thru all elements & check if the calories meet the threshold
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
                prev = node;
                node = node.next;
                isStart = false;
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
        // Iterate over the list & return the Food if its name matches the passed String
        for(Food f : this) {
            if(f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }

        // If not found, return null
        return null;
    }

    // Returns the strings of all the Foods in the list on their own line
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

    // Iterable implementation
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
