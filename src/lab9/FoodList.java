package lab9;

import java.util.Iterator;

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
        while(node != null) {
            if(node.element.getCals() >= cals) {
                node.next = node.next.next;
                node = node.next;
            }
        }
    }
}
