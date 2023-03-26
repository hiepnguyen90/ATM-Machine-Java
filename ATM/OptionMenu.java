import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();


	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pinNumber = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					Account acc = (Account) pair.getValue();
					if (data.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						getAccountType(acc);
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checking Account");
				System.out.println(" Type 2 - Savings Account");
				System.out.println(" Type 3 - View Checking and Savings Account Balance");
				System.out.println(" Type 4 - View Log");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						getChecking(acc);
						break;
					case 2:
						getSaving(acc);
						break;
					case 3:
						System.out.println("Checking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						System.out.println("Checking Savings Balance: " + moneyFormat.format(acc.getSavingBalance()));
						getAccountType(acc);
					case 4:
						printHistory(acc);
					case 5:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nChecking Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						System.out.println("\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						break;
					case 2:
						acc.getCheckingWithdrawInput();
						break;
					case 3:
						acc.getCheckingDepositInput();
						break;

					case 4:
						acc.getTransferInput("Checking");
						break;
					case 5:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
					case 1:
						System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
						break;
					case 2:
						acc.getsavingWithdrawInput();
						break;
					case 3:
						acc.getSavingDepositInput();
						break;
					case 4:
						acc.getTransferInput("Savings");
						break;
					case 5:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					if (!data.containsKey(cst_no)) {
						end = true;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		data.put(cst_no, new Account(cst_no, pin));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		getLogin();
	}

	public void mainMenu() throws IOException {

		read();
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
					case 1:
						getLogin();
						end = true;
						break;
					case 2:
						createAccount();
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
		quit();
		menuInput.close();
	}
	public void printHistory(Account account) {
		try {
			String pizza = "";
			File file = new File("log.txt");
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				pizza = scan.nextLine();
				if (Integer.parseInt(pizza.split(":")[0]) == account.getCustomerNumber()) {
					System.out.println(pizza);
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}


	public void quit() {
		try {
			Iterator<Map.Entry<Integer, Account>> iterator = data.entrySet().iterator();
			File file = new File("hiep3.txt");
			PrintStream writer = new PrintStream(file);
			while (iterator.hasNext()) {
				if (file != null) {
					Map.Entry<Integer, Account> entry = iterator.next();
					writer.println("" + entry.getKey() + "," + entry.getValue());
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		System.exit(0);
	}

	public void read() throws FileNotFoundException {
		Scanner read = new Scanner(new File("hiep3.txt"));
		String[] line;

		Integer username;
		Integer password;
		Double cbalance = 0.0;
		Double sbalance = 0.0;

		while(read.hasNext()) {
			line = read.next().split(",");

			username = Integer.parseInt(line[0]);
			password = Integer.parseInt(line[2]);
			cbalance = Double.parseDouble(line[3]);
			sbalance = Double.parseDouble(line[4]);

			Account account = new Account(username, password, cbalance, sbalance);
			data.put(username, account);
			}
		}
	}

