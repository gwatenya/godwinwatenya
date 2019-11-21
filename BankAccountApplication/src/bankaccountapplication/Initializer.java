/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccountapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Godwin Watenya
 */

public class Initializer {
    //Writing into a text file
     
    public static void initialize(UserManager userManager){
        //Reading User Folder to initialize users for the userManager   
        try{
            File folder = new File("Users");
            File[] listOfUsers = folder.listFiles();
            for (File y: listOfUsers){
                if (y.isFile()){
                    try{
                        String role;
                        String usern;
                        String pass;
                        FileReader fr = new FileReader("Users\\"+y.getName()); // Opens the file
                        BufferedReader br = new BufferedReader(fr);
                        role = br.readLine();
                        usern = br.readLine();
                        pass = br.readLine();
                        if (role.equals("Manager")){
                            Manager manager = new Manager(usern, pass, userManager);
                        }
                        else if (role.equals("Customer")){
                            try{
                                String amount = br.readLine();
                                double dblAmount = Double.parseDouble(amount);
                                Customer cust = new Customer(usern,pass,dblAmount, userManager);
                                userManager.addUser(cust);
                            }
                            catch (Exception e){
                                System.out.println("Error with amount");
                            }
                            finally{
                                br.close();
                            }
                            
                        }
                    }
                    catch (Exception e){
                        System.out.println("Error initializing user");
                    }
                }
            }
            if (userManager.hasManager() == false){
                FileWriter fw = new FileWriter("Users\\admin.txt"); // Creates a file
                PrintWriter pw = new PrintWriter(fw); // Prints to the file created
                pw.println("Manager");
                pw.println("admin");
                pw.println("admin");
                User manager = new Manager("admin", "admin", userManager);
                pw.close();
            }
        }
        catch(Exception e){
            System.out.println("Error opening file");
        }
    }
}
