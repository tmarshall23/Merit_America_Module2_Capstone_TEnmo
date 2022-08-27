package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.List;

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
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        AccountService account = new AccountService(currentUser);
        System.out.print("Your current account balance is: ");
        System.out.println(account.findBalance(currentUser.getUser().getId()));
		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        // sets transfer to send, int, for transfer id
        UserService userService = new UserService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);


        Account userAccount = accountService.getAccount(currentUser.getUser().getId());

        userService.printUsers(userService.userIdAndName(currentUser));

        int receivingAccountId = consoleService.promptForInt("Please select a User ID to transfer to: ");

        Account receivingAccount = accountService.getAccount((long) receivingAccountId);

        //make sure that accountService is not user and is part of map

        BigDecimal transferAmount = consoleService.promptForBigDecimal("Please enter an amount to send: ");

        //make sure value is not negative or zero,
        //




        userAccount.setBalance(userAccount.getBalance().subtract(transferAmount));
        receivingAccount.setBalance(receivingAccount.getBalance().add(transferAmount));



        Transfer transfer = new Transfer(2,2,userAccount.getAccount_id(),receivingAccount.getAccount_id(),transferAmount);
        transferService.sendTransferInfo(transfer);



        accountService.update(userAccount, (int)userAccount.getAccount_id());
        accountService.update(receivingAccount,(int) receivingAccount.getAccount_id());


        //need to validate that ID given is from the list.
        //need to validate that funds are available for transfer, also not ZERO or negative

        //send has initial status of approved
        //need to send the transfer details to the transfer table, transfer status and transfer type are already populated
        //gives transfer id need type int, status int, accountService from id, accountService to id and amount inserted into transfer table.

        //step 5 and 6 depend on finishing transfer table,
        //select * from transfer where accountFrom_id = ?, select * from transfer where transfer_id = ? respectively

        //handle exceptions and errors
        //comment code

        //2 methods for transfer status?? one for approved, one for denied?

//        System.out.println(currentUser.getUser().getId() + "--" + currentUser.getUser().getUsername());

//        System.out.println(receivingAccountId + "--" + receivingAccount);
//
//        System.out.println("Making Sending Transfer");
//
//        System.out.print("Is Transfer Valid: ");
//
//        System.out.println(accountService.isTransferValid(transferAmount, currentUser.getUser().getUsername()));
//
//        System.out.print("Your sent amount: " + transferAmount);

	}



	private void requestBucks() {

		// TODO Auto-generated method stub
		
	}

}
