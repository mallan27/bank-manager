package amoine4;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BankLogic håller ordning på en kundlista med alla bankens kunder.
 * 
 * @author Malou Nielsen, amoine-4
 */
public class BankLogic {
	private List<Customer> customerList;

	public BankLogic() {
		customerList = new ArrayList<Customer>();

	}

	/**
	 * Returnerar alla kunder i kundlistan i en List<String>. I funktionen skapas en
	 * ny lista för att behålla inkapslingen av kundlistan. I den kopierade Listan
	 * som returneras finns kunders förnamn, efternamn och personnummer.
	 * 
	 * @return listan med alla kunder som finns i
	 */

	public List<String> getAllCustomers() {
		List<String> theList = new ArrayList<String>();
		for (Customer c : customerList) {
			String customer = c.getWholeCustomer();
			theList.add(customer);
		}
		return theList;

	}

	/**
	 * Skapar en kund med personnr pNo, och för och efternamn. Kunden läggs in i
	 * kundlistan och returnerar då true. Om personnummret redan finns i listan
	 * eller ett personnummer inte skickats med returneras false och ingen kund
	 * läggs till.
	 * 
	 * @param förnamn, efternamn och personummer på kund man vill skapa
	 * @return boolesk uttryck om operationen lyckades eller ej.
	 */

	public boolean createCustomer(String name, String surname, String pNo) {
		boolean check = true;
		if (!pNo.isEmpty()) {
			for (Customer c : customerList) {
				if (pNo.equals(c.getPersonNr())) {
					check = false;
					break;
				}
			}
		} else {
			check = false;
		}

		if (!check == false) {
			Customer newCustomer = new Customer(name, surname, pNo);
			customerList.add(newCustomer);
			check = true;
		}
		return check;
	}

	/**
	 * Returnerar information om en kund med personnummer pNo, samt alla dennes
	 * konton. Informationen returneras i en List <String>, där de första elementet
	 * i listan innehåller kundens personnummer och namn. Om kunden inte hittas är
	 * listan tom och null returneras.
	 * 
	 * @param personummer på kund man vill ha information om
	 * @return en lista där första elementet innehåller kundens information och de
	 *         nästkommande elementen dennes konton.
	 **/

	public List<String> getCustomer(String pNo) {
		List<String> infoCustomer = new ArrayList<String>();
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				String customer = c.getWholeCustomer();
				infoCustomer.add(customer);
				for (int p = 0; p < c.getKontoLength(); p++) {
					String account = c.getKonto(p);
					infoCustomer.add(account);
				}
			}

		}
		if (infoCustomer.size() == 0) {
			return null;
		}

		return infoCustomer;
	}

	/**
	 * Ändrar förnamn och/eller efternamn på person med personnummer pNo. Om någon
	 * utav strängarna name eller surname är tomma strängar ändras bara det namn
	 * vars sträng har innehåll. Returnerar true om något av namnen har ändrats,
	 * annars false.
	 * 
	 * @param namn, efternamn och personummer
	 * @return boolesk uttryck om operationen lyckades eller inte.
	 */

	public boolean changeCustomerName(String name, String surname, String pNo) {
		boolean check = false;
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				if (!name.isEmpty()) {
					c.setName(name);
					check = true;
				}
				if (!surname.isEmpty()) {
					c.setSurName(surname);
					check = true;
				}
				break;
			} else {
				check = false;
			}

		}
		return check;
	}

	/**
	 * Skapar ett sparkonto åt person med personnummer pNo. Returnerar kontonummret
	 * på de skapade kontot. Om inget konto kunde skapas returneras -1.
	 * 
	 * @param personummer på person man vill skapa konto åt
	 * @return kontonummer på de skapade kontot
	 */

	public int createSavingsAccount(String pNo) {
		int createdAccountNumber = 0;
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				createdAccountNumber = c.createSavingsAccount();
				break;
			} else {
				createdAccountNumber = -1;
			}
		}
		return createdAccountNumber;
	}

	/**
	 * Skapar ett kreditkonto åt person med personnummer pNo. Returnerar
	 * kontonummret på de skapade kontot. Om inget konto kunde skapas returneras -1.
	 * 
	 * @param personummer på person man vill skapa konto åt
	 * @return kontonummer på de skapade kontot
	 */

	public int createCreditAccount(String pNo) {
		int createdAccountNumber = 0;
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				createdAccountNumber = c.createCreditAccount();
				break;
			} else {
				createdAccountNumber = -1;
			}
		}
		return createdAccountNumber;
	}

	/**
	 * Returnerar en string med information om ett konto med kontonummer accountId
	 * som tillhör person med personnummer pNo. Om inget personnummer eller
	 * kontonummer kopplat till personnummer hittas returneras null.
	 * 
	 * @param personnummer och kontonummer
	 * @return string med information om konto
	 */
	public String getAccount(String pNo, int accountId) {
		String accountInfo = "";
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				for (int p = 0; p < c.getKontoLength(); p++) {
					if (accountId == c.getAccountId(p)) {
						accountInfo = c.getKonto(p);
						break;
					}
				}

			}
		}
		if (accountInfo.isEmpty()) {
			return null;
		}
		return accountInfo;
	}

	/**
	 * Sätter in pengar på konto med kontonummer accountId, som tillhör person med
	 * personnummer pNo. Summan skickas med till funktionen i customer-klassen.
	 * Returnerar true om insättningen lyckades annars false.
	 * 
	 * @param personnummer, kontonummer och belopp
	 * @return boolesk uttryck om operationen lyckades eller inte.
	 */
	public boolean deposit(String pNo, int accountId, int amount) {
		boolean check = false;
		if (amount > 0) {
			for (Customer c : customerList) {
				if (pNo.equals(c.getPersonNr())) {
					for (int p = 0; p < c.getKontoLength(); p++) {
						if (accountId == c.getAccountId(p)) {
							check = c.insert(accountId, amount);
							break;
						}
					}
				}

			}

		}
		return check;

	}

	/**
	 * Gör uttag från ett konto med kontonummer accountId, som tillhör person med
	 * personnummer pNo. Summan som ska tas bort skickas med till en funktion
	 * customerklassen. Returnerar true om uttaget genomfördes annars false.
	 * 
	 * @param personnummer, kontonummer och belopp
	 * @return true eller false om operationen lyckades eller inte.
	 */
	public boolean withdraw(String pNo, int accountId, int amount) {
		boolean check = false;
		if (amount > 0) {
			for (Customer c : customerList) {
				if (pNo.equals(c.getPersonNr())) {
					for (int p = 0; p < c.getKontoLength(); p++) {
						if (accountId == c.getAccountId(p)) {
							if (c.withdraw(accountId, amount)) {
								check = true;
								break;
							}

						}
					}
				}

			}

		}
		return check;
	}

	/**
	 * Avslutar konto med kontonummer accountId som tillhör personen med
	 * personnummer pNo. Returnerar en presentation av konto med räntan i kronor om
	 * borttaget är lyckat. Om inget konto har tagits bort retuneras null.
	 * 
	 * @param personnummer och kontonummer
	 * @return null om inget konto hittades, annars information om raderade kontot.
	 **/
	public String closeAccount(String pNo, int accountId) {
		String retur = "";
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				for (int p = 0; p < c.getKontoLength(); p++) {
					if (accountId == c.getAccountId(p)) {
						retur = c.getDeletedAccount(p);
						break;
					}
				}
			}
		}
		if (retur.isEmpty()) {
			retur = null;
		}
		return retur;
	}

	/**
	 * Tar bort en kund och dennes konton med personnummer pNo. Returnerar den
	 * borttagna kunden och alla borttagna konton om funktionen lyckas, annars
	 * returneras null.
	 * 
	 * @param personnummer
	 * @return lista med information om bortagen kund och dennes raderade konton
	 */
	public List<String> deleteCustomer(String pNo) {
		List<String> returnCustomer = new ArrayList<String>();
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				while (c.getKontoLength() != 0) {
					String konto = c.getDeletedAccount(0);
					returnCustomer.add(konto);
				}
				String customer = c.getWholeCustomer();
				customerList.remove(c);
				returnCustomer.addFirst(customer);
				break;
			}
		}
		if (returnCustomer.isEmpty()) {
			return null;
		}

		return returnCustomer;
	}

	/**
	 * Hämtar en lista med transaktioner för kontot med kontomunret som skickad med
	 * och för kund med personnummer pNo.
	 * 
	 * @param pNo
	 * @param accountId
	 * @return Lista med transaktioner.
	 */
	public List<String> getTransactions(String pNo, int accountId) {
		List<String> tList = new ArrayList<String>();
		boolean check = false;
		for (Customer c : customerList) {
			if (pNo.equals(c.getPersonNr())) {
				for (int p = 0; p < c.getKontoLength(); p++) {
					if (accountId == c.getAccountId(p)) {
						tList = c.getTransac(p);
						check = true;
					}
				}
			}
		}
		if (!check) {
			return null;
		}
		return tList;
	}

	/**
	 * Metod för att returnera personnummer på person med index "i" i kund listan.
	 * 
	 * @param i
	 * @return personnummer
	 */
	public String getCustomerPNumber(int i) {
		return customerList.get(i).getPersonNr();
	}

	/**
	 * Metod för att hämta kunds namn med personnummer.
	 * 
	 * @param personnummer
	 * @return kundens namn
	 */

	public String getCustomerName(String pNumber) {
		String namn = "";
		for (Customer c : customerList) {
			if (pNumber.equals(c.getPersonNr())) {
				namn = c.getName();
			}
		}
		return namn;
	}

	/**
	 * MEtod för att rensa kundlistan och lägga till en ny. Används när banken
	 * laddas in.
	 * 
	 * @param list
	 */
	private void setCustomerList(List<Customer> list) {
		customerList.clear();
		customerList = list;
	}

	/**
	 * Metod för att hämta kundlistan
	 * 
	 * @return kundlistan
	 */
	public List<Customer> getCustomerList() {
		return customerList;
	}

	/**
	 * Metod för att se till att kontonummret börjat på rätt nummer när en bank
	 * laddas in.
	 */
	public void updateAccountNumber() {
		int highestAccountNumber = 1000;
		for (Customer customer : customerList) {
			for (int i = 0; i < customer.getKontoLength(); i++) {
				int accNumber = customer.getAccountId(i);
				if (accNumber >= highestAccountNumber) {
					highestAccountNumber = accNumber;
				}

			}
		}
		Account a = new SavingsAccount();
		a.setLastAssignedNumber(highestAccountNumber);
	}

	/**
	 * Metod för att spara transaktioner med datum till en textfil.
	 * 
	 * @param accNumber
	 * @param pNumber
	 * @return BufferedWriter
	 */
	public BufferedWriter saveTrans(int accNumber, String pNumber) throws FileNotFoundException, IOException {
		BufferedWriter transactionfile = null;
		List<String> list = getTransactions(pNumber, accNumber);
		if (list != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.now();
			transactionfile = new BufferedWriter(new FileWriter("Transactions.txt"));
			transactionfile.write(date.format(formatter));
			transactionfile.newLine();
			for (String l : list) {
				transactionfile.write(l);
				transactionfile.newLine();
			}
			transactionfile.newLine();
			transactionfile.write("Nuvarande saldo: " + getAccount(pNumber, accNumber));
		}
		return transactionfile;
	}

	/**
	 * Metod för att spara banken
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void saveBank() throws FileNotFoundException, IOException {
		ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("BankFile"));
		save.writeObject(getCustomerList());
	}

	/**
	 * Metod för att ladda in banken
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void loadBank() throws FileNotFoundException, IOException {
		@SuppressWarnings("resource")
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("BankFile"));
		try {
			setCustomerList((List<Customer>) ois.readObject());
			updateAccountNumber();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new GUI();
	}


}
