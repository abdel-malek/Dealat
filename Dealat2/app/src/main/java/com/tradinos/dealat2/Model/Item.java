package com.tradinos.dealat2.Model;

/**
 * Created by developer on 18.02.18.
 */

public class Item {
    protected String id, name;
    protected boolean checked;

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

    public boolean isNothing(){
        return this.id.equals("-1");
    }

    public boolean isChecked(){
        return this.checked;
    }

    public void check(){
        this.checked = true;
    }

    public void unCheck(){
        this.checked = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
