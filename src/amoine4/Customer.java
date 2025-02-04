package amoine4;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Innehåller förnamn, efternamn och personnummer. Klassen håller också reda på
 * en kunds konton i en arraylist. Kontona är kopplade till kunden. När kunden
 * skapas skapas också en lista med konton, listan har plats för 10 konton som
 * är maxantalet.
 * 
 * @author Malou Nielsen, amoine-4
 */

public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private final String personalNumber;
	private List<Account> accounts;

	public Customer(String firstName, String lastName, String peronalNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.personalNumber = peronalNumber;
		accounts = new ArrayList<Account>(10);

	}

	/**
	 * Returnerar en sträng med kundens personnummer. Krävs eftersom varibeln är
	 * satt til private i klassen.
	 * 
	 * @return personnummer
	 */
	public String getPersonNr() {
		return personalNumber;
	}

	/**
	 * Returnerar en sträng med personnummer, förnamn och efternamn för en kund.
	 * 
	 * @retun information om en kund
	 */
	public String getWholeCustomer() {
		String customer = personalNumber + " " + firstName + " " + lastName;
		return customer;
	}

	/**
	 * Sätter kundens förnamn till den inskickade strängen name. Krävs eftersom
	 * varibeln är satt til private i klassen.
	 * 
	 * @param namn som ska ändras eller sättas på kontot när de skapas.
	 */
	public void setName(String name) {
		this.firstName = name;
	}

	/**
	 * Sätter kundens efternamn till den inskickade strängen surName. Krävs eftersom
	 * varibeln är satt til private i klassen.
	 * 
	 * @param efternamn som ska ändras till eller sättas på kontot när de skapas.
	 */
	public void setSurName(String surName) {
		this.lastName = surName;
	}

	/**
	 * Skapar ett nytt sparkonto med saldo 0.0kr och lägger till i accountlistan för
	 * kunden. Returnerar kontonummret.
	 * 
	 * @return kontonummret på de nyskapade kontot
	 */
	public int createSavingsAccount() {
		Account newAccount = new SavingsAccount();
		accounts.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Skapar ett nytt kreditkonto med saldo 0.0kr och lägger till i accountlistan
	 * för kunden. Returnerar kontonummret.
	 * 
	 * @return kontonummret på de nyskapade kontot
	 */
	public int createCreditAccount() {
		Account newAccount = new CreditAccount();
		accounts.add(newAccount);
		return newAccount.getAccountNumber();
	}

	/**
	 * Returnerar storleken på accounts-listan.
	 * 
	 * @return storlek på account-arrayListen
	 */
	public int getKontoLength() {
		return accounts.size();
	}

	/**
	 * Returnerar en string med information om konto på plats "i" i accounts-listan.
	 * 
	 * @param indexet i som kontot finns på
	 * @return kontots information som en string
	 */
	public String getKonto(int i) {
		String account = accounts.get(i).getAccount();
		return account;
	}

	/**
	 * Returnerar kontonummer.
	 * 
	 * @param index i på kontot
	 * @return kontonummer för konto i
	 */
	public int getAccountId(int i) {
		int accountNumber = accounts.get(i).getAccountNumber();
		return accountNumber;
	}

	/**
	 * Sätter in pengar på ett konto med kontonummer accountnumber. Om insättningen
	 * lyckas returneras true annars false. Loopar igenom listan med accounts och
	 * kontrollerar så det stämmer överrens med det inskickade kontonumret.
	 * 
	 * @param kontonummer och belopp
	 * @return boolesk uttryck om insättningen lyckades eller inte
	 */
	public boolean insert(int accountNumber, int amount) {
		boolean check = false;
		BigDecimal gr = new BigDecimal(amount);
		for (int i = 0; i < accounts.size(); i++) {
			if (accountNumber == accounts.get(i).getAccountNumber()) {
				accounts.get(i).insert(gr);
				check = true;
			}
		}

		return check;
	}

	/**
	 * Gör ett uttag på amount från kundens konto med kontonummer accountNumber. Om
	 * uttaget lyckas returneras true, annars false.
	 * 
	 * @param kontonummer och belopp att ta ut
	 * @return boolesk uttryck om uttaget lyckades eller inte
	 */
	public boolean withdraw(int accountNumber, int amount) {
		boolean check = false;
		BigDecimal bigDecAmount = new BigDecimal(amount);
		for (int i = 0; i < accounts.size(); i++) {
			if (accountNumber == accounts.get(i).getAccountNumber()) {
				check = accounts.get(i).withDraw(bigDecAmount);
				break;
			}
		}
		return check;
	}

	/**
	 * Tar bort kontot från accounts med index i. Och hämtar information om det
	 * kontot som sedan returneras i en sträng.
	 * 
	 * @param platsen i listan där kontot finns
	 * @return en string med information om det raderade kontot.
	 */
	public String getDeletedAccount(int i) {
		String sc = accounts.remove(i).getDeletedAccountInfo();
		return sc;
	}

	/**
	 * Skapar en lista och hämtar transaktioner för kontot med index "i" i
	 * accounts-listan.
	 * 
	 * @param i
	 * @return lista med transaktioner
	 */

	public List<String> getTransac(int i) {
		List<String> transactionList = new ArrayList<String>();
		transactionList = accounts.get(i).getTransactions();
		return transactionList;
	}
	/**
	 * Metod för att hämta förnamn
	 * @return Förnamn
	 */
	
	public String getName() {
		return firstName; 
	}
	
}
