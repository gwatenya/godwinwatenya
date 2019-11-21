/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccountapplication;

import java.util.ArrayList;

/**
 *
 * @author Godwin Watenya
 */
public class UserManager {
    private ArrayList<User> users;
    
    public UserManager(){
        users = new ArrayList<User>();
        Initializer.initialize(this);
    }
    
    public boolean hasManager(){
        if (users.isEmpty() == true){
            return false;
        }
        for (User x: users){
            if (x instanceof Manager){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<User> getUsers(){
        return users;
    }
    
    public void addUser(User user){
        users.add(user);       
    }
    
    public boolean deleteCustomer(String user){
        for (User x: users){
            if (x.getUsername().equals(user)){
                if (x instanceof Customer){
                    Customer temp = (Customer) x;
                    double balance = temp.getBalance();
                    users.remove(x);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void displayUsers(){
        System.out.println("Number of users: " + users.size());
        for (User x: users){
            System.out.println(x);
        }
    }
}
