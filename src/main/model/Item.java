package model;

public abstract class Item {
    public String name;

    // REQUIRES: Item
    // MODIFIES: Item's name
    // EFFECTS: Sets the item's name
    public void setName(String s) {
        name = s;
    }

    // REQUIRES: Item with a name
    // MODIFIES: N/A
    // EFFECTS: Returns the item's name
    public String getName() {
        return name;
    }

    public String removeUrgentTag(Item i) {
        return i.getName().substring(6);
    }
}
