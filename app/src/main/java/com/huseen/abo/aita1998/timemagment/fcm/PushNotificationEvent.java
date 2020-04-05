package com.huseen.abo.aita1998.timemagment.fcm;

/**
 * Author: Amal
 * Created on: 10/18/2016 , 10:16 PM
 * Project: FirebaseChat
 */

public class PushNotificationEvent {
    private String title;
    private String message;
    private String username;
    private String userImage;
    private String uid;
    private String receiverFCMToken;

    public PushNotificationEvent() {
    }

    public PushNotificationEvent(String title, String message, String username, String userImage, String uid, String fcmToken) {
        this.title = title;
        this.message = message;
        this.username = username;
        this.userImage = userImage;
        this.uid = uid;
        this.receiverFCMToken = fcmToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getFcmToken() {
        return receiverFCMToken;
    }

    public void setFcmToken(String fcmToken) {
        this.receiverFCMToken = fcmToken;
    }
}
