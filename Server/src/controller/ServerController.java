/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketmodel.Login;
import socketmodel.Register;
import socketmodel.User;
import socketmodel.UserOnline;

/**
 *
 * @author KyThuat88
 */
public class ServerController {
    private Connection con;
    private ServerSocket myServer;
    private int serverPort = 8888;
    public ServerController() {
        DBConnect("mmt", "root", "123456789");
        openServer();
        while(true){
            listenning();
        }
    }
    
    private void openServer(){
        try {
            myServer = new ServerSocket(serverPort);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void DBConnect(String dbName, String username,String password){
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,username, password);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    private void listenning(){
        try {
            Socket socket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            Object o = ois.readObject();
            if(o instanceof Login){
                Login login = (Login)o;
                if(checkUser(login.getUser())){                                        
                    int id = login.getUser().getId();
                    System.out.println(id);
                    String sql = "UPDATE users SET online = 1 WHERE idusers = ?";                                                                                   
                    try {
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    oos.writeObject("ok");
                }
                else
                    oos.writeObject("false");
            }   
            else if(o instanceof Register){
                Register register = (Register)o;
                if(checkRegis(register.getUser())){
                    insertUser(register.getUser());
                    oos.writeObject("ok");
                }
                else
                    oos.writeObject("false");
            }
            
            else if(o instanceof UserOnline){
                ArrayList<User> useronline = new ArrayList();
                String sql = "SELECT idusers, username FROM users WHERE online = ?";
                PreparedStatement stmt;
                try {
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1, 1);
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        int id = rs.getInt("idusers");
                        String username = rs.getString("username");
                        User user = new User(id,username);
                        useronline.add(user);
                    }
                                       
                } catch (SQLException ex) {
                    Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                UserOnline userOnl = new UserOnline(useronline);
                oos.writeObject(userOnl);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insertUser(User user){
        String query = "INSERT INTO users (username, password,online) VALUES (?, ?, ?)";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, 0);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkRegis(User user){
        String query = "Select * FROM users WHERE username=?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    private boolean checkUser(User user){
        String query = "Select * FROM users WHERE username ='"
        + user.getUsername()
        + "' AND password ='" + user.getPassword() + "'";
        
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                user.setId(rs.getInt("idusers"));
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
