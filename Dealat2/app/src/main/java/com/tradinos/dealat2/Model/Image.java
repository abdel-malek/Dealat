package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 * Created by developer on 18.02.18.
 */

public class Image implements Serializable {
    public static int ImageCounter;

    private String path;
   // private int number;
    private boolean selected;


    public Image(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        this.selected = true;
        ImageCounter++;
    }

    public void unselect(){
        this.selected = false;
        ImageCounter--;
    }
}
