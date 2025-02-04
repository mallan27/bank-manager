package amoine4;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Abstrakt klass som representerar ett konto. Innehåller information om saldo,
 * ränta, kontonummer, kontotyp och en lista med transaktionshistorik. Kan inte
 * direkt instansieras. När ett kontoskapas i någon av subklasserna sätts kontot
 * till den medskickade kontotyp-variabeln och korrekt räntesats för kontotypen.
 * Saldot sätts till noll och en tom transaktions-hstorik lista skapas.
 * 
 * @author Malou Nielsen, amoine-4
 */

public abstract class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5694315857307705416L;
	/**
	 * 
	 */

	private BigDecimal balance;
	private BigDecimal interestRate;
	private final int accountNumber;
	private static int lastAssignedNumber = 1000;
	private String accountType;
	private List<String> transactions;

	public Account(String type, BigDecimal inRate) {
		lastAssignedNumber++;
		accountNumber = lastAssignedNumber;
		interestRate = inRate;
		accountType = type;
		balance = new BigDecimal("0.0");
		transactions = new ArrayList<String>();
	}

	/**
	 * Abstrakta metoder som implementeras i subklasserna.
	 * 
	 * @param sum
	 * @return
	 */
	public abstract boolean withDraw(BigDecimal sum);

	public abstract String getInterestedAmount();

	public abstract String getAccount();

	public void setLastAssignedNumber(int number) {
		lastAssignedNumber = number;
	}

	/**
	 * Formatterar saldot och returnerar det som en string.
	 * 
	 * @return saldot formaterat som en string i kr.
	 */

	protected String getBalanceFormatted() {
		String balanceStr = NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(balance);
		return balanceStr;
	}

	/**
	 * Returnerar saldot som en Big Decimal, metoden krävs eftersom balance varibeln
	 * är satt till private i klassen.
	 * 
	 * @return saldot i Big Decimal
	 */

	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Returnerar räntesatsen i en string, formatterad med %.
	 * 
	 * @return räntesatsen som en string formaterad med %
	 */

	public String getInterestRate() {
		BigDecimal bg = new BigDecimal("100");
		NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.of("SV", "SE"));
		percentFormat.setMaximumFractionDigits(1);
		String percentStr = percentFormat.format(interestRate.divide(bg));
		return percentStr;
	}

	/**
	 * Metod för att hämta räntesatsen
	 * 
	 * @return räntesatsen
	 */
	public BigDecimal getIntRate() {
		return interestRate;
	}

	/**
	 * Privat metod för att metoderna inom klassen ska kunna hämta kontotyp.
	 * 
	 * @return kontotyp
	 */
	protected String getAccountType() {
		return accountType;
	}

	/**
	 * Returnerar kontonummer, metoden krävs eftersom varibeln accountNumber är satt
	 * till private klassen.
	 * 
	 * @return kontonummer
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Adderar sum till balance så balance får nytt värde. Ett nytt tid/datum objekt
	 * skapas och läggs till i transactionslistan för kontot.
	 * 
	 * @param insättningsumma
	 */
	public void insert(BigDecimal sum) {
		balance = balance.add(sum);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String transactionInfo = date.format(formatter) + " "
				+ NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(sum) + " Saldo: "
				+ getBalanceFormatted();
		transactions.add(transactionInfo);
	}

	/**
	 * Gör uttag från ett konto genom att skapa en ny Big Decimal withdraw med
	 * värdet av den medskickade strängen sum. Withdraw subtraheras från balance.
	 * Ett nytt tid/datum objekt skapas och läggs till i transactionslistan för
	 * kontot.
	 * 
	 * 
	 * @param uttag-summa
	 */
	public void subtractBalance(BigDecimal sum) {
		balance = balance.subtract(sum);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String transactionInfo = date.format(formatter) + " -"
				+ NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(sum) + " Saldo: "
				+ getBalanceFormatted();
		transactions.add(transactionInfo);
	}

	/**
	 * Skriver ut informationen om ett konto, metoden används när ett konto ska
	 * raderas och returnerar en sträng med kontoinformation och summan av räntan i
	 * kronor.
	 * 
	 * @return information om det raderade kontot
	 */
	public String getDeletedAccountInfo() {
		String s = "" + getAccountNumber() + " " + getBalanceFormatted() + " " + getAccountType() + " "
				+ getInterestedAmount();
		return s;
	}

	/**
	 * Returnerar en kopia av listan med transaktionhistorik för kontot.
	 * 
	 * @return lista med transaktioner.
	 */

	public List<String> getTransactions() {
		List<String> tList = new ArrayList<String>();
		for (int i = 0; i < transactions.size(); i++) {
			tList.add(transactions.get(i));
		}
		return tList;
	}

}
