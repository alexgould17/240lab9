# Project Readme
This is a small program, written in Java, meant to
fulfill the requirements for a computer science class I did
not have the chance to take. Specifically, the class is CSC-240
at Parkland College, taught by Professor David Bock. The lab
specifications are listed below.

The intent of this project was to develop my own skills by
completing this, the final assignment for the second in the
series of classes on Java, but also to demonstrate good use of
version control, as the rest of the assignments I've completed
have been developed on a single machine.

## Lab description
*this is copied directly from its source, [here](https://remy.parkland.edu/~dbock/Class/csc240/Labs/Lab9.html)*

### Lab 9 – Food List
Develop a meal planning system that allows a user to create meals based 
on a list of foods stored in a file. Write a program to read and store a
linked list of foods from [this text file](https://remy.parkland.edu/~dbock/Class/csc240/Labs/foods.txt) *(Alex's note: this file is saved in the project folder)*
(to use the file, simply download and import into your Eclipse project).
Handle all I/O exceptions during reading with `try/catch` statements.
Each line in this text file represents a single food with four pieces of
information including the name of a food, the food group, the number of
calories, and the daily percentage. Below are examples:
```
Salmon protein 600 0.2
Strawberries fruit 200 0.02
Asparagus vegetable 32 0.1
```
Develop a class (named `FoodList`) that implements a linked list similar
to the textbook example and lecture slides to store individual food
items read from the file. You **must** develop your own linked list
class and may **not** use an array or the Java API classes (e.g.
`ArrayList`, `LinkedList`\[sic\]) to store the food items. After the
linked list of food items has been constructed, provide a menu with
functionality to allow a user to perform the following operations.
1. List all foods in the linked list
2. Create a meal by manually selecting three foods
3. Create a meal by randomly selecting three foods
4. Remove foods from the list that are high in calorie

When listing the foods in the linked list (item 1 above), print them in
aligned columns with headings as shown below. For manual meal creation
(item 2 above), prompt the user for three foods from the list. If the
entered food is not in the list or spelled incorrectly, continue until
all three foods from the list have been specified. For random meal
creation (item 3 above), randomly select three foods from the list to
create the meal. For both manual and random meal creation, compute the
total number of calories and daily percentages from all the foods in the
meal and print along with the selected foods. For removal (item 4 above),
you are to remove food items from the linked list that contain calories
over a user-specified limit. To remove an object from the linked list,
you **must** alter the references within the objects to maintain the
linked list. Note that using a conditional statement to simply not print
these records does **not** suffice as removal of an object from the
linked list. You **must** develop at least two classes (excluding the
driver) for this assignment.
