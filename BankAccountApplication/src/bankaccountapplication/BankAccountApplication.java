/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccountapplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 *
 * @author Godwin Watenya
 */
public class BankAccountApplication extends Application {
    
    UserManager userManager = new UserManager();
    CurrentUser currentUser;
    
    @Override
    public void start(Stage primaryStage) {
        Stage managerStage = new Stage();
        Stage customerStage = new Stage();
        
// Login Screen
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(10, 11, 12, 13));
        loginPane.setHgap(5);
        loginPane.setVgap(5);
        
        //Username and Password input
        Label loginUserLbl = new Label("Username: ");
        Label loginPassLbl = new Label("Password: ");
        TextField usernTxtField = new TextField();
        usernTxtField.setPrefColumnCount(10);
        PasswordField passPassField = new PasswordField();
        passPassField.setPrefColumnCount(10);
        
        //Login button
        Button loginBtn = new Button();
        loginBtn.setText("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                   String tempName = usernTxtField.getText();
                    String tempPass = passPassField.getText();
                    Label loginLbl = new Label();
                    ArrayList<User> users = userManager.getUsers();
                    for (User x: users){
                        if ((x.getUsername().equals(tempName))){
                            if ((x.getPassword().equals(tempPass))){
                                currentUser = new CurrentUser(x);
                                primaryStage.hide();
                                if (currentUser.getUser() instanceof Manager){
                                    managerStage.showAndWait();
                                }
                                else{
                                    customerStage.setTitle("Customer " + currentUser.getUsername());
                                    customerStage.showAndWait();
                                }
                                currentUser = null;
                                usernTxtField.clear();
                                passPassField.clear();
                                primaryStage.show();
                            }
                        }
                    } 
                }
                catch(Exception ignore){  }
            }
        });
        
        Button btnCloseApp = new Button();
        btnCloseApp.setText("Close");
        btnCloseApp.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });
        
        loginPane.add(loginUserLbl, 0, 0);
        loginPane.add(usernTxtField, 1, 0);
        loginPane.add(loginPassLbl, 0, 1);
        loginPane.add(passPassField, 1, 1);
        loginPane.add(loginBtn, 1, 2);
        loginPane.add(btnCloseApp, 1, 3);
        
        Label lblInstructions = new Label("If an action on this application " +  
            "doesn't have a reaction, \nthe inputs required are invalid.\n" +
            "____________________________________________________________");
        Label creatorlbl = new Label("By Godwin Watenya");
        BorderPane mainLoginPane = new BorderPane();
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(creatorlbl, 10d);
        AnchorPane.setBottomAnchor(creatorlbl, 10d);
        anchorPane.getChildren().add(creatorlbl);
        mainLoginPane.setCenter(loginPane);
        mainLoginPane.setBottom(anchorPane);
        mainLoginPane.setTop(lblInstructions);
        mainLoginPane.setPadding(new Insets(10, 11, 12, 13));
        
        Scene loginScene = new Scene(mainLoginPane, 400, 260);
        
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
         
// Manager Screen
        FlowPane managerPane = new FlowPane();
        managerPane.setPadding(new Insets(10, 10, 10, 10));
        managerPane.setMinSize(50,200);
        managerPane.setHgap(5);
        managerPane.setVgap(5);
        // Logout Case
        Button btnLOM = new Button(); // Button Log Out Manager 
        btnLOM.setText("Log Out");
        btnLOM.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                managerStage.close();
            }
        });
        // Add Customer Case
        Button addBtn = new Button();
        addBtn.setText("Add Customer");
        Label amount = new Label("Amount: $");
        addBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage addCustStage = new Stage();
                addCustStage.setTitle("Add Customer");
                GridPane addCustPane = new GridPane();
                addCustPane.setPadding(new Insets(11, 12, 13, 14));
                Scene addCustScene = new Scene(addCustPane,320,150);
                addCustStage.setScene(addCustScene);
                Label newUserLbl = new Label("Set Username: ");
                Label newPassLbl = new Label("Set Pass: ");
                
                addCustPane.add(newUserLbl, 0, 0);
                addCustPane.add(newPassLbl, 0, 1);
                addCustPane.add(amount, 0, 2);
                TextField addUserTxtField = new TextField();
                TextField setPassField = new PasswordField();
                TextField setAmount = new TextField();
                
                addCustPane.add(addUserTxtField, 1, 0);
                addCustPane.add(setPassField, 1, 1);
                addCustPane.add(setAmount, 1, 2);
                Button confirmBtn = new Button();
                confirmBtn.setText("Confirm");
                confirmBtn.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        String tempA = setAmount.getText();
                        
                        for (int i=0; i<tempA.length();i++){
                            double dblAmount;
                            try{
                                for(User x: userManager.getUsers()){
                                    if (x.getUsername().equals(addUserTxtField.getText())){
                                        throw new IllegalArgumentException ();
                                    }
                                }
                                dblAmount = Double.parseDouble(tempA);
                                if (dblAmount < 100){
                                    throw new IllegalArgumentException();
                                }
                                currentUser.addCustomer(addUserTxtField.getText(), setPassField.getText(), dblAmount);
                                Button btnCloseCheck = new Button();
                                btnCloseCheck.setText("User Added");
                                Stage tempStage = new Stage();
                                Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                                tempStage.setTitle("Success");
                                tempStage.setScene(tempScene);
                                btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event){
                                       tempStage.close();
                                   }
                                });
                                addCustStage.hide();
                                tempStage.showAndWait();
                                addCustStage.show();
                            }
                            catch(Exception e){
                                /* Changed this to do nothing when invalid.
                                 * When it used to do sonething, it would always
                                 * throw an exception for a valid input since one
                                 * click on the button from ther user could be
                                 * sensed as 3 or more clicks by the program 
                                */
                                return;
                            }
                        }
                    }
                });
                Button closeAdd = new Button();
                closeAdd.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        addCustStage.close();
                    }
                });
                closeAdd.setText("Close");
                addCustPane.add(confirmBtn, 0, 3);
                addCustPane.add(closeAdd, 1, 3);
                addCustStage.show();
            }
        });
        
        // Delete Customer Case
        Button delBtn = new Button();
        delBtn.setText("Delete Customer");
        delBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage delCustStage = new Stage();
                FlowPane delCustPane = new FlowPane();
                delCustPane.setPadding(new Insets(11, 12, 13, 14));
                Scene delCustScene = new Scene(delCustPane,500,100);
                delCustStage.setTitle("Delete Customer");
                delCustStage.setScene(delCustScene);
                Label lblUsername = new Label("Username");
                TextField tempTxtFld = new TextField();
                Button btnConfirmDel = new Button();
                btnConfirmDel.setText("Confirm");
                Button closeDel = new Button();
                closeDel.setText("Close");
                delCustPane.getChildren().addAll(lblUsername, tempTxtFld, btnConfirmDel, closeDel);
                btnConfirmDel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            if (currentUser.deleteCustomer(tempTxtFld.getText()) == true){
                                Button btnCloseCheck = new Button();
                                btnCloseCheck.setText("User Deleted");
                                Stage tempStage = new Stage();
                                Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                                tempStage.setTitle("Success");
                                tempStage.setScene(tempScene);
                                btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event){
                                       tempStage.close();
                                   }
                                });
                                delCustStage.hide();
                                tempStage.showAndWait();
                                delCustStage.show();
                            }
                            else {
                                throw new IllegalArgumentException();
                            }
                        }
                        catch (Exception ignore){  }
                    }
                });
                closeDel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        delCustStage.close();
                    }
                });
                delCustStage.show();
            }
        });
        
        managerPane.getChildren().addAll(addBtn, delBtn, btnLOM);
        Scene managerScene = new Scene (managerPane, 400, 200);
        managerStage.setTitle("Manager");
        managerStage.setScene(managerScene);
        
// Customer Screen
        GridPane customerPane = new GridPane();
        Scene customerScene = new Scene(customerPane,200,250);
        //customerStage.setTitle("Customer ");
        customerPane.setPadding(new Insets(11, 12, 13, 14));
        customerPane.setMinSize(200,250);
        customerPane.setHgap(5);
        customerPane.setVgap(5);
        // Logout Case
        Button btnLOC = new Button(); // Button Log Out Customer
        btnLOC.setText("Log Out");
        btnLOC.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                customerStage.close();
            }
        });
        
        // Get Balance Case
        Label lblGetBal = new Label("Balance: ");
        Button btnGetBal = new Button();
        btnGetBal.setText("Get Balance");
        btnGetBal.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                double amount = currentUser.getBalance();
                DecimalFormat moneyFormat = new DecimalFormat("#.00");
                String money = moneyFormat.format(amount);
                Button btnCloseCheck = new Button();
                btnCloseCheck.setText("Balance: $" + money);
                Stage tempStage = new Stage();
                Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                tempStage.setTitle("Balance");
                tempStage.setScene(tempScene);
                btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                   @Override
                   public void handle(ActionEvent event){
                       tempStage.close();
                   }
                });
                tempStage.show();
            }
        });
        
        // Deposit - to be done
        Button btnDeposit = new Button();
        btnDeposit.setText("Deposit");
        btnDeposit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage depositStage = new Stage();
                FlowPane depositPane = new FlowPane();
                depositPane.setPadding(new Insets(11, 12, 13, 14));
                Scene depositScene = new Scene(depositPane, 425, 100);
                depositStage.setTitle("Deposit");
                depositStage.setScene(depositScene);
                
                Label lblAddAmount = new Label("Amount: $");
                TextField addAmountTxtFld = new TextField();
                Button btnDep = new Button();
                Button btnCancel = new Button();
                depositPane.getChildren().addAll(lblAddAmount, addAmountTxtFld, btnDep, btnCancel);
                btnDep.setText("Deposit");
                btnDep.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            Double dblDep = Double.parseDouble(addAmountTxtFld.getText());
                            if (dblDep <= 0)
                                throw new IllegalArgumentException(); 
                            currentUser.deposit(dblDep);
                            DecimalFormat moneyFormat = new DecimalFormat("#.00");
                            String money = moneyFormat.format(dblDep);                          
                            Button btnCloseCheck = new Button();
                            btnCloseCheck.setText("Deposit of $" + money + " was successful");
                            Stage tempStage = new Stage();
                            Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                            tempStage.setTitle("Success");
                            tempStage.setScene(tempScene);
                            btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent event){
                                   tempStage.close();
                               }
                            });
                            depositStage.hide();
                            tempStage.showAndWait();
                            depositStage.show();
                            
                        }
                        catch(Exception e){
                            
                        }
                    }
                });
                btnCancel.setText("Cancel");
                btnCancel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        depositStage.close();
                    }
                });
                depositStage.show();
            }
        });
        
        // Withdraw - to be done
        Button btnWithdraw = new Button();
        btnWithdraw.setText("Withdraw");
        btnWithdraw.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage withdrawStage = new Stage();
                FlowPane withdrawPane = new FlowPane();
                withdrawPane.setPadding(new Insets(11, 12, 13, 14));
                Scene withdrawScene = new Scene(withdrawPane, 450, 100);
                withdrawStage.setScene(withdrawScene);
                withdrawStage.setTitle("Withdraw");
                
                Label lbWithdrawAmount = new Label("Amount: $");
                TextField wdAmountTxtFld = new TextField();
                Button btnWithdraw = new Button();
                Button btnCancel = new Button();
                withdrawPane.getChildren().addAll(lbWithdrawAmount, wdAmountTxtFld, btnWithdraw, btnCancel);
                btnWithdraw.setText("Withdraw");
                btnWithdraw.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            Double dbWithdraw = Double.parseDouble(wdAmountTxtFld.getText());
                            if (dbWithdraw <= 0 || dbWithdraw > currentUser.getBalance())
                                throw new IllegalArgumentException(); 
                            currentUser.withdraw(dbWithdraw);
                            DecimalFormat moneyFormat = new DecimalFormat("#.00");
                            String money = moneyFormat.format(dbWithdraw);
                            Button btnCloseCheck = new Button();
                            btnCloseCheck.setText("Withdrawal of $" + money + " was successful");
                            Stage tempStage = new Stage();
                            Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                            tempStage.setTitle("Success");
                            tempStage.setScene(tempScene);
                            btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent event){
                                   tempStage.close();
                               }
                            });
                            withdrawStage.hide();
                            tempStage.showAndWait();
                            withdrawStage.show();
                            
                        }
                        catch(Exception e){
                            System.out.println("Amount must be between 0 and current funds.");
                        }
                    }
                });
                btnCancel.setText("Cancel");
                btnCancel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        withdrawStage.close();
                    }
                });
                withdrawStage.show();
            }
        });
        
        // Online Purchase
        Button btnOnlineP = new Button();
        btnOnlineP.setText("Online Purchase");
        btnOnlineP.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage purchaseStage = new Stage();
                GridPane purchasePane = new GridPane();
                purchasePane.setPadding(new Insets(11, 12, 13, 14));
                Scene purchaseScene = new Scene(purchasePane, 700, 200);
                purchaseStage.setScene(purchaseScene);
                purchaseStage.setTitle("Online Purchase");
                
                Label lblaccountLevel = new Label("Account Level Fee");
                Label lblsilver = new Label(" Silver\n Balance < $10,000");
                Label lblsilverfee = new Label(" $20.00");
                Label lblgold = new Label(" Gold\n $10,000 <= Balance < $20,000");
                Label lblgoldfee = new Label(" $10.00");
                Label lblplat = new Label(" Platinum\n Balance >= $20,000");
                Label lblplatfee = new Label(" $0.00");
                Label lblPrice = new Label("Price(>=$50): $");
                TextField priceTxtFld = new TextField();
                Button btnDep = new Button();
                Button btnCancel = new Button();
                
                purchasePane.add(lblaccountLevel, 0, 0);
                purchasePane.add(lblsilver, 1, 0);
                purchasePane.add(lblgold, 2, 0);
                purchasePane.add(lblplat, 3, 0);
                purchasePane.add(lblsilverfee, 1, 1);
                purchasePane.add(lblgoldfee, 2, 1);
                purchasePane.add(lblplatfee, 3, 1);
                purchasePane.add(lblPrice, 0, 2);
                purchasePane.add(priceTxtFld, 1, 2);
                purchasePane.add(btnDep, 2, 2);
                purchasePane.add(btnCancel, 2, 3);
                
                btnDep.setText("Pay");
                btnDep.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            Double dbPrice = Double.parseDouble(priceTxtFld.getText());
                            if (dbPrice < 50)
                                throw new IllegalArgumentException();
                            switch (currentUser.getLevel()){
                                case 0: // Silver Account has a fee of $20
                                    if (dbPrice + 20.0 > currentUser.getBalance()){
                                        throw new IllegalArgumentException();
                                    }   
                                    break;
                                case 1: // Gold Account has a fee of $10
                                    if (dbPrice + 10.0 > currentUser.getBalance()){
                                        throw new IllegalArgumentException();
                                    } 
                                    break;
                                case 2: // Platinum Account has no fee
                                    if (dbPrice > currentUser.getBalance()){
                                        throw new IllegalArgumentException();
                                    }
                                    break;
                                default:
                                    throw new IllegalArgumentException();
                            }
                            currentUser.onlinePurchase(dbPrice);
                            DecimalFormat moneyFormat = new DecimalFormat("#.00");
                            String money = moneyFormat.format(dbPrice);
                            Button btnCloseCheck = new Button();
                            btnCloseCheck.setText("Purchase of $" + money + " was successful");
                            Stage tempStage = new Stage();
                            Scene tempScene = new Scene(btnCloseCheck, 300, 100);
                            tempStage.setTitle("Success");
                            tempStage.setScene(tempScene);
                            btnCloseCheck.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent event){
                                   tempStage.close();
                               }
                            });
                            purchaseStage.hide();
                            tempStage.showAndWait();
                            purchaseStage.show();
                            
                        }
                        catch(Exception e){
                            System.out.println("Amount must be between 0 and current funds.");
                        }
                    }
                });
                btnCancel.setText("Cancel");
                btnCancel.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        purchaseStage.close();
                    }
                });
                purchaseStage.show();
            }
        });
        customerPane.add(btnLOC, 0, 0);
        customerPane.add(btnGetBal, 0, 1);
        customerPane.add(btnDeposit, 0, 2);
        customerPane.add(btnWithdraw, 0, 3);
        customerPane.add(btnOnlineP, 0, 4);
        customerStage.setScene(customerScene);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
