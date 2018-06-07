package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 * Created by developer on 18.02.18.
 */

public class Image implements Serializable {
    public static int ImageCounter;
    public static final int MAX_IMAGES = 8;

    private String path, serverPath;
    private boolean selected, markedAsMain, loading;

    public Image(){
        path = "";
        loading = false;
        selected = true;
    }

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

    public boolean isPreviouslyLoaded(){ // it means the image was uploaded when Submit and now when Edit Ad, image has no path
        // I mean it has no Local path on the device// but it has a server path of course
        return this.path.equals("");
    }
}
