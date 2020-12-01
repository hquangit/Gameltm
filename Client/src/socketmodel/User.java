/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmodel;

import java.io.Serializable;

/**
 *
 * @author KyThuat88
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private int online;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User() {
        this.online=0;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.online=0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.online = 0;
        this.online=0;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.online=0;
    }
    
    public Object[] toObject(){
        return new Object[]{id, username};
    }
    public static void main(String[] args) {
        
    }
}
