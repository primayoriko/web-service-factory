package com.factory.model;

import java.sql.ResultSet;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

import org.postgresql.util.MD5Digest;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlElement( required = true )
    private String username;
    @XmlElement( required = true )
    private String name;
    @XmlElement( required = true )
    private String email;
    @XmlElement( required = true )
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

    public boolean validatePassword(String password) {
        return this.password.equals(User.hashPassword(this.username, password));
    }

    public static String hashPassword(String username, String password){
        try {
            byte[] digest = MD5Digest.encode(username.getBytes("UTF-8"), password.getBytes("UTF-8"), "temenguedapetchilde3x".getBytes("UTF-8"));
            String hashedPassword = new String(digest, "US-ASCII");
            return hashedPassword.substring(3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
