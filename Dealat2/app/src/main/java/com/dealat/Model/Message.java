package com.dealat.Model;

/**
 * Created by developer on 22.03.18.
 */

public class Message {
    private String id, text, createdAt;
    private boolean toSeller, sent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isToSeller() {
        return toSeller;
    }

    public void setToSeller(boolean toSeller) {
        this.toSeller = toSeller;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
