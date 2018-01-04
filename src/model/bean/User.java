package model.bean;

import model.Model;

import java.io.Serializable;

public class User implements Serializable{


    /**
     * createdAt : 2017-11-07 13:50:16
     * objectId : 16e90be8d0
     * sessionToken : a8b4618f404de85d80ad9e1437f62aca
     * updatedAt : 2018-01-04 16:19:55
     * username : 123
     */

    private String createdAt;
    private String objectId;
    private String sessionToken;
    private String updatedAt;
    private String username;



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return Model.getGson().toJson(this);
    }
}
