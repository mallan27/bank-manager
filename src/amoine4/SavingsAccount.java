package amoine4;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Ärver klassen Account, är alltså en typ av konto. När ett sparkonto skapas
 * skickas parametrarna kontotyp:Sparkonto och räntesats:2.4 till superklassens
 * konstruktor. Ett boolesk attribut "freeWithdraw" håller ordning på det första
 * gratis uttaget för kontot.
 * 
 * @author Malou Nielsen, amoine-4
 */
public class SavingsAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	private boolean freeWithdraw;

	public SavingsAccount() {
		super("Sparkonto", new BigDecimal("2.4"));
		freeWithdraw = true;

	}

	/**
	 * Skapar en BigDecimal för uttagsränta på 2%. Kontrollerar så att saldot är mer
	 * än summan som ska tas ut. Om det är första uttaget så är freeWithdraw satt
	 * till true, och pengarna kan tas ut och då sätts varibeln till false. Om det
	 * redan är false slås uttags-summan och uttag-summan ihop och kontrollerar så
	 * saldot räcker.
	 * 
	 * @param Uttag-summa
	 * @return true eller false
	 */

	@Override
	public boolean withDraw(BigDecimal sum) {
		BigDecimal wrate = new BigDecimal("0.02");
		boolean check = false;
		if (getBalance().compareTo(sum) >= 0) {
			if (freeWithdraw) {
				subtractBalance(sum);
				freeWithdraw = false;
				check = true;
			} else {
				BigDecimal totalAmount = sum.add(sum.multiply(wrate));
				if (getBalance().compareTo(totalAmount) >= 0) {
					subtractBalance(totalAmount);
					check = true;
				}
			}
		}
		return check;
	}

	/**
	 * Metod för att kunna se räntesatsen på kontot.
	 * 
	 * @return En sträng med räntan.
	 */
	@Override
	public String getInterestedAmount() {
		BigDecimal interestAmount = new BigDecimal("0");
		BigDecimal count = new BigDecimal("100");
		interestAmount = getBalance().multiply(getIntRate()).divide(count);
		String interestAmountStr = NumberFormat.getCurrencyInstance(Locale.of("SV", "SE")).format(interestAmount);
		return interestAmountStr;
	}

	/**
	 * Returnerar information om konto i en string.
	 * 
	 * @return information om ett konto.
	 */
	public String getAccount() {
		String s = "" + getAccountNumber() + " " + getBalanceFormatted() + " " + getAccountType() + " "
				+ getInterestRate();
		return s;
	}

}
