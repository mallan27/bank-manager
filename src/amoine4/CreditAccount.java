package amoine4;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Ärver klassen Account, är alltså en typ av konto. När ett kreditkonto skapas
 * skickas parametrarna kontotyp:Kreditkonto och räntesats:1.1 till
 * superklassens konstruktor. Kreditgränsen sätts till 5000kr och skuldräntan
 * 5%.
 * 
 * @author Malou Nielsen, amoine-4
 */
public class CreditAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal creditLimit;
	private BigDecimal debtRate;

	public CreditAccount() {
		super("Kreditkonto", new BigDecimal("1.1"));
		creditLimit = new BigDecimal("5000.00");
		debtRate = new BigDecimal("5.0");
	}

	/**
	 * Skapar ett nytt saldo i varibeln newBalance, för att sedan kontrollera om new
	 * Balance är större än -5000. Kreditgränsen är på 5000 kr, och med
	 * negate-metoden sätts creditlimit till -5000. Om det nya saldot är större än
	 * creditLimit.negate görs uttaget på summan som skickats in och true
	 * returneras, annars false.
	 * 
	 * @param Uttagsumman
	 * @return true eller false
	 */
	@Override
	public boolean withDraw(BigDecimal sum) {
		BigDecimal newBalance = getBalance().subtract(sum);
		if (newBalance.compareTo(creditLimit.negate()) >= 0) {
			subtractBalance(sum);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metod för att kunna se räntesatsen på kontot. Kontrollerar om saldot är
	 * negativt eller positivt, och beroende på de räknas räntan fram med antingen
	 * skuldränta eller räntesatsen för positiva saldon.
	 * 
	 * @return En sträng med ränte-beloppet.
	 * 
	 */

	@Override
	public String getInterestedAmount() {
		BigDecimal interestAmount = new BigDecimal("0");
		String interestAmountStr;
		BigDecimal count = new BigDecimal("100");
		BigDecimal check = new BigDecimal("0.0");
		if (getBalance().compareTo(check) >= 0) {
			interestAmount = getBalance().multiply(getIntRate()).divide(count);
			interestAmountStr = NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(interestAmount);
		} else {
			interestAmount = getBalance().multiply(debtRate).divide(count);
			interestAmountStr = NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(interestAmount);
		}
		return interestAmountStr;
	}

	/**
	 * Returnerar information om konto i en string. Kontrollerar om saldot är på
	 * plus eller minus, och returnerar rätt räntesats baserat på om saldot är lika
	 * med eller överstiger 0kr.
	 * 
	 * @return information om ett konto.
	 */

	public String getAccount() {
		BigDecimal check = new BigDecimal("0.0");
		String s;
		if (getBalance().compareTo(check) >= 0) {
			s = "" + getAccountNumber() + " " + getBalanceFormatted() + " " + getAccountType() + " "
					+ getInterestRate();
		} else {
			s = "" + getAccountNumber() + " " + getBalanceFormatted() + " " + getAccountType() + " " + getDebtRate();
		}
		return s;
	}

	/**
	 * Privat hjälpmetod för att få skuldtäntan i formatterat format med %.
	 * 
	 * @return en string med skuldräntan.
	 */

	private String getDebtRate() {
		BigDecimal bg = new BigDecimal("100");
		NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.of("SV", "SE"));
		percentFormat.setMaximumFractionDigits(1);
		String percentStr = percentFormat.format(debtRate.divide(bg));
		return percentStr;
	}

}
