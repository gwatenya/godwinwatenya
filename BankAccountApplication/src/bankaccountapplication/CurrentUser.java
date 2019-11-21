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

public class CurrentUser extends User{
/* OVERVIEW: The function of this class is to handle the states of different 
 *           users, i.e if it is a manager using the program, or a specific
 *           customer using the program.
 *           This class is mutable since it handles the changing of state 
 *           between different users.
 *
 * The abstraction function is:
 *  AF(U) = Role + Username
 *   Where U = the current user of the program.
 *   If current user is an instance of manager class:
 *    AF(M) = AF(U)
 *    Where M = a user of type Manager.
 *   If current user is an instance of customer class:
 *    AF(C) = AF(U) + Balance
 *    Where C = user of type customer
 *          Balance = balance of the current user's account
 *
 * The Rep Invariant is:
 *  For Customer State: If a customer object exists, it must have a username and password, 
 *  and the balance of the account associated to it must be greater or 
 *  equal to zero
 *  For Manager State: The manager class is required to have the username and password admin
 */
    
    private User current;
    
    // constructor
    public CurrentUser(User cUser){
        /* EFFECTS: Initializes the state of the program to the user who 
         *   logged in
         */
        current = cUser;
    }
    public User getUser(){
        // EFFECTS: Return the current user.
        return current;
    }
    
    @Override
    public String getUsername(){
        // EFFECTS: Return the current user's username.
        return current.getUsername();
    }
    
    //Manager Class State
    @Override
    public void addCustomer(String usern, String pass, double amount){
        /* REQUIRES: Current User is instance of Manager Class.
         * MODIFIES: The UserManager by adding a new custormer if its
         *           instance variables are valid
        */
        current.addCustomer(usern, pass, amount);
    }
    @Override
    public boolean deleteCustomer(String usern){
        /* REQUIRES: Current User is instance of Manager Class.
         * MODIFIES: The UserManager by deleting a custormer if it exists.
         * EFFECTS: Returns true if deletion was successful, otherwise false.
        */
        return current.deleteCustomer(usern);
    }
    @Override
    public ArrayList<User> getUsers(){
        /* REQUIRES: Current User is instance of Manager Class.
         * EFFECTS: Return an ArrayList of all the users
        */
        return current.getUsers();
    }
    
    //Customer Class State
    @Override
    public void deposit(double amount){
        /* REQUIRES: Current User is instance of Customer Class.
         * MODIFIES: Current User's funds are added by the depositted amount
        */
        current.deposit(amount);
    }
    @Override
    public void withdraw(double amount){
        /* REQUIRES: Current User is instance of Customer Class.
         * MODIFIES: Current User's funds are deducted by the withdrawn amount
        */
        current.withdraw(amount);
    }
    @Override
    public double getBalance(){
        /* REQUIRES: Current User is instance of Customer Class.
         * EFFECTS: Returns the Balance of the Current User
        */
        return current.getBalance();
    }
    @Override
    public void onlinePurchase(double price){
        /* REQUIRES: Current User is instance of Customer Class.
         * MODIFIES: Current User's funds are deducted by the price and the
         *           account level fees associated.
        */
        current.onlinePurchase(price);
    }
    @Override
    public int getLevel(){
        /* REQUIRES: Current User is instance of Customer Class.
         * EFFECTS: Returns the account level of the current user
        */
        return current.getLevel();
    }
    @Override
    public String toString(){
        // EFFECTS: Returns the abstraction fuction.
        return current.toString();
    }
    
    @Override
    public boolean repOk(){
        // EFFECTS: Returns the rep invariant.
        return current.repOk();
    }
}
