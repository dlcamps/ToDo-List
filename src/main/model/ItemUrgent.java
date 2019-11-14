package model;

public class ItemUrgent extends Item {

    @Override
    public void setName(String s) {
        name = "[!!!] " + s;
    }

//    @Override
//    public String getName() {
//        return name.substring(6);
//    }
}
