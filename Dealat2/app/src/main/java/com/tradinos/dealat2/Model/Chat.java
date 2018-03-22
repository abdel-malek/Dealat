package com.tradinos.dealat2.Model;

import java.io.Serializable;

/**
 * Created by developer on 22.03.18.
 */

public class Chat implements Serializable {

    private String userId, sellerId, chatId, adId;
    private String userName, sellerName;
    private String userPic, sellerPic;
    private String adTitle;

    private boolean userSeen, sellerSeen;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getSellerPic() {
        return sellerPic;
    }

    public void setSellerPic(String sellerPic) {
        this.sellerPic = sellerPic;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public boolean isUserSeen() {
        return userSeen;
    }

    public void setUserSeen(boolean userSeen) {
        this.userSeen = userSeen;
    }

    public boolean isSellerSeen() {
        return sellerSeen;
    }

    public void setSellerSeen(boolean sellerSeen) {
        this.sellerSeen = sellerSeen;
    }
}
