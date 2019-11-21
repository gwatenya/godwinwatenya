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
public abstract class User {
    private String username;
    private String password;
    
    //Manager Class State
    public abstract void addCustomer(String usern, String pass, double amount);
    public abstract boolean deleteCustomer(String usern);
    public abstract ArrayList<User> getUsers();
    
    //Customer Class State
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract double getBalance();
    public abstract void onlinePurchase(double price);
    public abstract int getLevel();
    
    //Any State
    public abstract boolean repOk();
    public void setUsername(String usern){
        try{
            if (usern != null || !"".equals(usern)){
                username = usern;
            }
            else{
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Input username");
        }
    }    
    public void setPassword(String pass){
        try{
            if (pass != null || !"".equals(pass)){
                password = pass;
            }
            else{
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Input password");
        }
        
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
