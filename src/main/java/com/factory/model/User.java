package com.factory.model;

import java.sql.ResultSet;

public class User {
    private String username;
    private String name;
    private String email;
    private String password;

    public User(ResultSet rs){
        try{
            this.name = rs.getString("name");
            this.username = rs.getString("username");
            this.password = rs.getString("password");
            this.email = rs.getString("email");
        } catch (Exception err){
            err.printStackTrace();
            this.name = null;
            this.username = null;
            this.password = null;
            this.email = null;
        }
    }

    public boolean validatePassword(String password){
        return true;
    }

    public String getHashPassword(String password){
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
