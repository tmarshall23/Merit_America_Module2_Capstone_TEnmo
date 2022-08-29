package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import io.cucumber.java.en_old.Ac;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                break;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


	private void viewCurrentBalance() {
        //Creates a new account for the currentUser Object.
        AccountService account = new AccountService(currentUser);
        System.out.print("Your current account balance is: ");
        //returns the balance from the database, given the userId
        System.out.println(account.findBalance(currentUser.getUser().getId()));
		
	}

	private void viewTransferHistory() {
        TransferService transferService = new TransferService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        Account userAccount = accountService.getAccount(currentUser.getUser().getId());
       //Creates a List of Transfer Ids for the transfers sent to other accounts for the current user
        List<Integer> toList = transferService.getTransferIdsFrom(userAccount.getAccount_id());
       //Creates a List of Transfer Ids for the transfers received from other accounts to the current user
        List<Integer> fromList = transferService.getTransferIdsTo(userAccount.getAccount_id());
            //Prints the list of accounts From
            for (Integer id:fromList) {
                System.out.println(id + " To: " + transferService.getUsernameForTransferTo(id) + " $" + transferService.getAmountForTransfer(id));
            }
            //Prints the list of accounts To
            for (Integer id:toList) {
                System.out.println(id + " From: " + transferService.getUsernameForTransferFrom(id) + " $" + transferService.getAmountForTransfer(id));
            }
                System.out.println(" ");
        //Asks user for the Transfer Id of a valid Transfer to display Transfer info
        int transferInfo = consoleService.promptForInt("Please select a transaction ID for more information OR 0 to exit to Main Menu: ");


        if (transferInfo == 0){
            mainMenu();
            consoleService.closeScanner();


        }else
        {
            if(toList.contains(transferInfo) || fromList.contains(transferInfo)) {
                //Creates Transfer based off of Server information
                Transfer transferOutput = transferService.getTransferForId((long) transferInfo);
                //Prints all info from Transfer
                System.out.println(" ");
                System.out.println("Transfer Id ---- " + transferOutput.getTransfer_id());
                System.out.println("Transfer Status ---- " + transferService.getTransferTypeDesc(transferOutput.getTransfer_type_id()));
                System.out.println("Transfer Type ---- " + transferService.getTransferStatusDesc(transferOutput.getTransfer_status_id()));
                System.out.println("Account From ---- " + transferOutput.getAccount_from());
                System.out.println("Account To ---- " + transferOutput.getAccount_to());
                System.out.println("Amount ---- " + "$" + transferOutput.getAmount());
            }
            else{
                System.out.println(" ");
                System.out.println("Please enter a valid TransferID");
            }
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {

        UserService userService = new UserService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        int approved = 2;
        int denied = 3;
        int send = 2;

        //Creates connection to server VIA userID
        Account userAccount = accountService.getAccount(currentUser.getUser().getId());
        //Creates Menu of Users and Id's to send Money to
        consoleService.sendTEBucksMenu();
        userService.printUsers(userService.userIdAndName(currentUser));
        consoleService.sendTEBucksMenuEnd();
        //Asks for UserID for account to send to
        int receivingAccountId = consoleService.promptForInt("Please select a User ID to transfer to Or 0 to exit to Main Menu: ");
            //Makes sure that the account is valid in the list of provided accounts, and not the user.
            if(receivingAccountId == userAccount.getAccount_id() || !userService.userIdAndName(currentUser).containsKey((long)(receivingAccountId))){
                System.out.println("You can't send money to yourself, or to people who don't exist");
                mainMenu();
                consoleService.closeScanner();
            }
            //returns to main menu, when prompted by zero
            if (receivingAccountId == 0){
                mainMenu();
                consoleService.closeScanner();
            }
        //retrieves the account that was asked for in previous step
        Account receivingAccount = accountService.getAccount((long) receivingAccountId);
        //creates the transfer amount by the amount given
        BigDecimal transferAmount = consoleService.promptForBigDecimal("Please enter an amount to send: ");
        //creates a transfer to send to the database after validation
        Transfer transfer = new Transfer(send, approved, userAccount.getAccount_id(), receivingAccount.getAccount_id(), transferAmount);
            //Validates amount is not zero
            if(transferAmount.equals(BigDecimal.valueOf(0))){
                System.out.println(" ");
                System.out.println("Cannot send a zero amount");
                mainMenu();
                consoleService.closeScanner();
             }
            //Validates the amount is not negative
            else if(transferAmount.compareTo(BigDecimal.ZERO) < 0){
                System.out.println(" ");
                System.out.println("Cannot send negative amount");
                mainMenu();
                consoleService.closeScanner();
             }
            //Validates the amount is not more than the user currently has
            else if(transferAmount.compareTo(userAccount.getBalance()) == 1){
                System.out.println(" ");
                System.out.println("Insufficient funds");
                transfer.setTransfer_status_id(denied);
                transferService.sendTransferInfo(transfer);
                mainMenu();
                consoleService.closeScanner();
             }
            //Sends transfer info to Server
            else {
                transferService.sendTransferInfo(transfer);
             }
        //sets the balance of both users, adding and subtracting the transfer amount
        userAccount.setBalance(userAccount.getBalance().subtract(transferAmount));
        receivingAccount.setBalance(receivingAccount.getBalance().add(transferAmount));
        //Sends the users to the server to update database
        accountService.update(userAccount, (int)userAccount.getAccount_id());
        accountService.update(receivingAccount,(int) receivingAccount.getAccount_id());
        //info on transaction printed
        System.out.println(" ");
        System.out.println("Transfer Complete: " + transferService.getTransferStatusDesc(transfer.getTransfer_status_id()));
        System.out.println(" ");
        System.out.println("Amount sent to " + receivingAccount.getAccount_id() + ": $" + transferAmount);


        //handle exceptions and errors
        //comment code

	}

	private void requestBucks() {

		// TODO Auto-generated method stub
		
	}

}
