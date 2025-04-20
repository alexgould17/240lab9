# Program Design Specifications
## Classes
### `Driver`
Helper methods:
- Display main menu
- Display equal bars divider
- Display food database header

### `Food`
Knows:
- Name of the food `String name`
- Food group `String group` *thought about making this an enum, but seems unnecessarily complex & restrictive*
- Kilocalories per serving `int kcal` *will be referenced in the interface simply as* "calories" *because Americans tend not to know that food calories are actually measured in kilocalories*
- Daily percentage `double daily`
- `static final String STRING_DEFAULT`, the default value for String parameters in this class.
- `static final String[] tableHeaders` array of 4 `String`s (one for each variable in the `Food` class) that will head the table when the full list is displayed.
- `static int[] columnWidths` array of 4 `int`s listing the width of each column in the display table. Initialized to the length of the corresponding `String` in `tableHeaders`.
- `NumberFormatter`s for integers & percentages

Does:
- Default & full constructors
- getters & setters (`kcal` >= 0, `daily` >= 0.0)
- `static` method to read a `Food` from a line of text, `throws InputMismatchException`
- toString that prints a well-formatted line of text describing the item

### `FoodList`
Singly-linked list class storing a variable number of `Food` items.
Should implement `Iterable` for use in range-based `for` loops.

Knows:
- `int size`
- `FoodListNode start`

Does:
- Default constructor & constructor with single `Food` item
- Add a `Food` to the list
- `int getLength()`
- Implements `Iterable` & `Iterator` properly to list the contained `Food`s in order
- Deletes items from itself above a certain `kcal` threshold
- Selects random foods for a meal (params: `int numFoods`)
- Finds a food by `name` in the list & returns it (or `null` if not found)

### `FoodListNode`
`protected` subclass for linked list implementation. Data members are
public for easier manipulation since this is not a public-facing class.

Knows:
- `public Food element`
- `public FoodListNode next`

Does:
- Default & with `Food` item constructors