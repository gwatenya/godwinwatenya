/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccountapplication;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Godwin Watenya
 */
public class Customer extends User {
    private double funds;
    private int accountLevel;
    UserManager users;
    /* level 0 = silver(funds < $10,000)
     * 1 = gold($10,000 <= funds < $20,000)
     * 2 = platinum(funds > $20,000)
    */
    public Customer(String user, String pass, double amount, UserManager Uman){
        super.setUsername(user);
        super.setPassword(pass);
        funds = amount;
        this.updateLevel();
    }
    
    private void updateLevel(){
        if (funds < 10000){
            accountLevel = 0;
        }
        else if (funds >= 10000 && funds < 20000){
            accountLevel = 1;
        }
        else if (funds >= 20000){
            accountLevel = 2;
        }
    }
    
    @Override
    public int getLevel(){
        return accountLevel;
    }
    
    @Override
    public void deposit(double amount){
        try{
            if (amount <= 0){
                throw new IllegalArgumentException();
            }
            try{
                FileWriter fw = new FileWriter("Users\\"+this.getUsername()+".txt"); // Creates a file
                PrintWriter pw = new PrintWriter(fw); // Prints to the file created
                pw.println("Customer");
                pw.println(this.getUsername());
                pw.println(this.getPassword());
                funds = funds + amount;
                this.updateLevel();
                pw.println(funds);
                pw.close();
            }
            catch (Exception e){
                System.out.println("Error updating file");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Amount must be greater than 0");
        }
        
    }
    @Override
    public void withdraw(double amount){
        try{
            if (amount > funds){
                throw new IllegalArgumentException();
            }
            try{
                FileWriter fw = new FileWriter("Users\\"+this.getUsername()+".txt"); // Creates a file
                PrintWriter pw = new PrintWriter(fw); // Prints to the file created
                pw.println("Customer");
                pw.println(this.getUsername());
                pw.println(this.getPassword());
                funds = funds - amount;
                this.updateLevel();
                pw.println(funds);
                pw.close();
            }
            catch (Exception e){
                System.out.println("Error updating file");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Funds must be greater than amount");
        }
        
    }
    @Override
    public double getBalance(){
        return funds;
    }
    @Override
    public void onlinePurchase(double price){
        double finalFee = 0;
        try{
            if (price > funds){
                throw new IllegalArgumentException(); // Not enough funds
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Not enough funds");
            return;
        }
        try{
            switch (this.accountLevel) {
                case 0: // Silver Account has a fee of $20
                    finalFee = price + 20.00;  
                    break;
                case 1: // Gold Account has a fee of $10
                    finalFee = price + 10.00; 
                    break;
                case 2: // Platinum Account has no fee
                    finalFee = price;
                    break;
                default:
                    break;
            }
            try{
                FileWriter fw = new FileWriter("Users\\"+this.getUsername()+".txt"); // Creates a file
                PrintWriter pw = new PrintWriter(fw); // Prints to the file created
                pw.println("Customer");
                pw.println(this.getUsername());
                pw.println(this.getPassword());
                funds = funds - finalFee;
                this.updateLevel();
                pw.println(funds);
                pw.close();
                updateLevel();
            }
            catch (Exception e){
                System.out.println("Error updating file");
            }
            
        }
        catch (IllegalArgumentException e){
            System.out.println("Not enough funds to cover account level fee");
        }
    }
    
    @Override
    public void addCustomer(String usern, String pass, double amount){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Customer invalid command.");
        }
    }
    
    @Override
    public boolean deleteCustomer(String usern){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Customer invalid command.");
            return false;
        }
    }
    
    @Override
    public ArrayList<User> getUsers(){
        try{
            throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e){
            System.out.println("Customer invalid command.");
        }
        return null;
    }
    
    @Override
    public String toString(){
        return ("Customer: " + this.getUsername() + " Balance: " + this.getBalance());
    }
    
    @Override
    public boolean repOk(){
        return !(this.getUsername() == null || this.getPassword() == null || funds < 0);
    }
}
