package model;

public class ItemUrgent extends Item {

    @Override
    public void setName(String s) {
        name = "[!!!] " + s;
    }
/*    @Override
    public String getName(String s) {
        return name.substring(6);
    }*/
}
