/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccountapplication;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Godwin Watenya
 */
public class Manager extends User{
    private UserManager userManager;
    
    public Manager(String user, String pass, UserManager userMan) {
        try{
            try{
                if (userMan.hasManager() == true){
                    throw new IllegalArgumentException();
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Only one manager per system");
                return;
            }
            if (user.equals("admin") && pass.equals("admin")){
                super.setUsername(user);
                super.setPassword(pass);
                userManager = userMan;
                userManager.addUser(this);
            }
            else{
              throw new IllegalArgumentException();  
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Manager initialized with specific username and password.");
        }
    }
    
    @Override
    public void addCustomer(String user, String pass, double amount){
        ArrayList <User> users = userManager.getUsers();
        for (User x: users){
            try{
                if (x.getUsername().equals(user)){
                    throw new IllegalArgumentException ();
                }    
            }
            catch(IllegalArgumentException e){
                //System.out.println("Can't have the same username as another user");
                return;
            }
            
        }
        try{
            if (amount < 100){
                throw new IllegalArgumentException ();
            }
            else{
                //Writing into a file
                try{
                    double balance = amount;
                    FileWriter fw = new FileWriter("Users\\"+user+".txt"); // Creates a file
                    PrintWriter pw = new PrintWriter(fw); // Prints to the file created
                    pw.println("Customer");
                    pw.println(user);
                    pw.println(pass);
                    pw.println(balance);
                    pw.close();
                    userManager.addUser(new Customer(user, pass, amount, userManager));
                }
                catch (Exception e){
                    System.out.println("Error creating Customer file");
                }
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Initial deposit must be greater or equal to $100");
        }
    }
    @Override
    public boolean deleteCustomer(String usern){
        try{
            if (userManager.deleteCustomer(usern) == false)
                throw new IllegalArgumentException ();
            if (!(new File ("Users\\"+ usern + ".txt").delete()))
                throw new IllegalArgumentException ();
            return true;
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid Customer.");
            return false;
        }
        
    }
    @Override
    public ArrayList<User> getUsers(){
        return userManager.getUsers();
    }
    
    @Override
    public void deposit(double amount){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Manager invalid.");
        }
    }
    @Override
    public void withdraw(double amount){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Manager invalid.");
        }
    }
    @Override
    public double getBalance(){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Manager invalid.");
        }
        return -1;
    }
    @Override
    public void onlinePurchase(double price){
        
    }
    @Override
    public int getLevel(){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Manager invalid.");
        }
        return -1;
    }
    
    @Override
    public String toString(){
        return ("Manager: " + this.getUsername());
    }
    
    @Override
    public boolean repOk(){
        return ((getUsername().equals("admin")) && (getPassword().equals("admin")));
    }
}
