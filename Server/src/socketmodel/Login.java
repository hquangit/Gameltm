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
public class Login implements Serializable{
    private User user;

    public Login() {
    }

    public Login(User user) {
        this.user = user;
    }
        
    public User getUser() {
        return user;
    }
    
    public static void main(String[] args) {
        
    }
}
