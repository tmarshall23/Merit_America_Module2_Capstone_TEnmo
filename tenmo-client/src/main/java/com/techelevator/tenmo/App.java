package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import io.cucumber.java.en_old.Ac;

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
        TransferService transferService = new TransferService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        Account userAccount = accountService.getAccount(currentUser.getUser().getId());

       List<Integer> toList = transferService.getTransferIdsFrom(userAccount.getAccount_id());
        List<Integer> fromList = transferService.getTransferIdsTo(userAccount.getAccount_id());

        for (Integer id:fromList) {
            System.out.println(id + " To: " + transferService.getUsernameForTransferTo(id) + " $" + transferService.getAmountForTransfer(id));
        }
        for (Integer id:toList) {
            System.out.println(id + " From: " + transferService.getUsernameForTransferFrom(id) + " $" + transferService.getAmountForTransfer(id));
        }
        System.out.println(" ");
       int transferInfo = consoleService.promptForInt("Please select a transaction ID for more information OR 0 to exit to Main Menu: ");
        if (transferInfo == 0){
            mainMenu();
        }
        Transfer transferOutput = transferService.getTransferForId((long)transferInfo);

        System.out.println(" ");
        System.out.println("Transfer Id ---- " + transferOutput.getTransfer_id());
        System.out.println("Transfer Status ---- " + transferService.getTransferTypeDesc(transferOutput.getTransfer_type_id()));
        System.out.println("Transfer Type ---- " + transferService.getTransferStatusDesc(transferOutput.getTransfer_status_id()));
        System.out.println("Account From ---- " + transferOutput.getAccount_from());
        System.out.println("Account To ---- " + transferOutput.getAccount_to());
        System.out.println("Amount ---- " + "$" +transferOutput.getAmount());
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        // sets transfer to send, int, for transfer id
        UserService userService = new UserService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        int approved = 2;
        int denied = 3;
        int send = 2;

        Account userAccount = accountService.getAccount(currentUser.getUser().getId());
        consoleService.sendTEBucksMenu();
        userService.printUsers(userService.userIdAndName(currentUser));
        consoleService.sendTEBucksMenuEnd();
        int receivingAccountId = consoleService.promptForInt("Please select a User ID to transfer to Or 0 to exit to Main Menu: ");
        if(receivingAccountId == userAccount.getAccount_id() || !userService.userIdAndName(currentUser).containsKey((long)(receivingAccountId))){
            System.out.println("You can't send money to yourself, or to people who don't exist");
            mainMenu();
        }
        if (receivingAccountId == 0){
            mainMenu();
        }
        Account receivingAccount = accountService.getAccount((long) receivingAccountId);



        BigDecimal transferAmount = consoleService.promptForBigDecimal("Please enter an amount to send: ");

        Transfer transfer = new Transfer(send, approved, userAccount.getAccount_id(), receivingAccount.getAccount_id(), transferAmount);

        if(transferAmount.equals(BigDecimal.valueOf(0))){
            System.out.println(" ");
            System.out.println("Cannot send a zero amount");
            mainMenu();
        }
       else if(transferAmount.compareTo(BigDecimal.ZERO) < 0){
            System.out.println(" ");
            System.out.println("Cannot send negative amount");
            mainMenu();
        }
       else if(transferAmount.compareTo(userAccount.getBalance()) == 1){
            System.out.println(" ");
            System.out.println("Insufficient funds");
            transfer.setTransfer_status_id(denied);
            transferService.sendTransferInfo(transfer);
            mainMenu();
        }
       else {
            transferService.sendTransferInfo(transfer);
        }
        userAccount.setBalance(userAccount.getBalance().subtract(transferAmount));
        receivingAccount.setBalance(receivingAccount.getBalance().add(transferAmount));

        accountService.update(userAccount, (int)userAccount.getAccount_id());
        accountService.update(receivingAccount,(int) receivingAccount.getAccount_id());

        System.out.println(" ");
        System.out.println("Transfer Complete: " + transferService.getTransferStatusDesc(transfer.getTransfer_status_id()));
        System.out.println(" ");
        System.out.println("Amount sent to " + receivingAccount.getAccount_id() + ": $"
                            + transferAmount);


        //handle exceptions and errors
        //comment code

	}



	private void requestBucks() {

		// TODO Auto-generated method stub
		
	}

}
