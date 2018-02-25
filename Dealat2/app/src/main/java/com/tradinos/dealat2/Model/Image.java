package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 * Created by developer on 18.02.18.
 */

public class Image implements Serializable {
    public static int ImageCounter;

    private String path, serverPath;
   // private int number;
    private boolean selected, markedAsMain, loading = true;

    public Image(String path) {
        this.path = path;
        this.loading = true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
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

    public boolean isMarkedAsMain(){
        return markedAsMain;
    }

    public void markAsMain(){
        this.markedAsMain = true;
    }

    public void unMarkAsMain(){
        this.markedAsMain = false;
    }

    public void setLoading(boolean b){
        loading = b;
    }

    public boolean isLoading(){
        return loading;
    }
}
