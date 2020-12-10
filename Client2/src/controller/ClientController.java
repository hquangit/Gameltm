/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import View.HomeView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Message;

/**
 *
 * @author KyThuat88
 */
public class ClientController {

    private Socket mySocket;
    private String serverHost = "localhost";
    private int serverPort = 8888;
    ObjectInputStream ois;
    ObjectOutputStream oos;
           

    public ClientController() {
    }

    public Socket openConnection() {
        try {            
            mySocket = new Socket(serverHost, serverPort);
            ois = new ObjectInputStream(mySocket.getInputStream());
            System.out.println("new ois");
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            System.out.println("new oos");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return mySocket;
    }

    public boolean sendData(Object ob) {
        try {
            oos.writeObject(ob);

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public Object receiveData() {
        Object result = null;
        try {
            Message o = (Message) ois.readObject();
            System.out.println("Da nhan data");
            switch (o.getLabel()) {
                case LOGIN_SUCCESS:
                    HomeView hv = new HomeView();
                    hv.setVisible(true);
                    break;
                case LOGIN_FAIL:
                    JOptionPane.showMessageDialog(null, "Login fail");

            }

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return result;

    }

    public boolean closeConnection() {
        try {
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
