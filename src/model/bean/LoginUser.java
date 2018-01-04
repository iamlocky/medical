package model.bean;

import model.Model;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class LoginUser implements Serializable{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LinkedHashMap<String,String> getMap(){
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        map.put("username",username);
        map.put("password",password);
        return map;
    }
    @Override
    public String toString() {
        return Model.getGson().toJson(this);
    }

}
