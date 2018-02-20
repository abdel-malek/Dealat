package com.tradinos.dealat2.Model;

/**
 * Created by developer on 18.02.18.
 */

public class Item {
    protected String id, name;

    public Item(){}

    public static Item getNoItem(){

        return new Item("-1", "--");
    }

    public Item(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
