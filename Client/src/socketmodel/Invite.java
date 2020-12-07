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
public class Invite implements Serializable{
    private User user;

    public Invite(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
   
}
