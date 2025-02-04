package amoine4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Användargränssnitt med fyra inre lyssnarklasser, en referens till BankLogic
 * för att komma åt logiken i banken.
 * 
 * @author Malou Nielsen, amoine-4
 */
public class GUI {
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 700;
	private static final int PANEL_WIDTH = FRAME_WIDTH - 10;
	private static final int PANEL_HEIGHT = FRAME_HEIGHT - 50;
	private BankLogic bl;
	private String formatExep = "Vänligen mata in belopp och konto-nummer med siffror";
	private String wrongMatch = "Transaktion misslyckades, kontrollera att personnummer och kontonummer är rätt";

	private JFrame frame;
	private JPanel basePanel;
	private JPanel startPanel;
	private JPanel customerPanel;
	private JPanel accountPanel;
	private JPanel transactionPanel;
	private JPanel menuPanel;
	private JPanel textPanel;

	private ActionListener welcomeListener;
	private ActionListener cl;
	private ActionListener al;
	private ActionListener tl;

	private JTextField ssnA;
	private JTextField accountNumber;

	private JTextField firstname;
	private JTextField surname;
	private JTextField ssn;

	private JTextArea textArea;
	private JTextArea textAreaAccount;

	private JTextField ssnT;
	private JTextField accountNumberTransaction;
	private JTextField sum;
	private JTextArea textAreaTransactions;
	private JPanel buttons;

	private JList customerList;
	private JList accountList;
	private JList transactionList;

	/**
	 * När ett GUI-objekt instansieras skapas en referens till banklogic och en
	 * frame. Anropar metoder i konstruktorn för att bygga upp gränssnittet. Sätter
	 * synligheten till true.
	 */
	public GUI() {
		bl = new BankLogic();
		frame = new JFrame("Bank");
		frame.setBounds(200, 100, FRAME_WIDTH, FRAME_HEIGHT);
		basePanel = new JPanel();
		basePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		createComponents();
		frame.add(basePanel);
		welcomePage();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/**
	 * Skapar komponenterna till gränssnittet. I metoden skapas startsidan och
	 * metoden anropar andra metoder som skapar olika vyer. Adderar Startpanelen
	 * till base-panel.
	 */

	public void createComponents() {
		startPanel = new JPanel(new GridLayout(2, 1));
		welcomeListener = new StartListener();
		JLabel welcome = new JLabel("Välkommen till banken!");

		startPanel.add(welcome);
		createMenu();
		buttons.setVisible(false);
		startPanel.add(menuPanel);

		createTransactionPage();
		createCustomerPage();
		createAccountPage();
		basePanel.add(startPanel);
	}

	/**
	 * Skapar "Hantera kund"-vyn och adderar den till bas-panelen.
	 */

	public void createCustomerPage() {
		cl = new CustomerListener();
		customerPanel = new JPanel(new BorderLayout());
		customerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		JPanel inputPanel = new JPanel(new GridLayout(9, 1));

		createMenu();
		inputPanel.add(menuPanel);

		customerList = new JList();
		customerList.setBorder(BorderFactory.createTitledBorder("Bankens kunder"));

		customerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) {
					textAreaTransactions.setText("");
					String selectedCustomer = (String) customerList.getSelectedValue();
					textArea.setText(selectedCustomer);
					int pos = customerList.getSelectedIndex();
					String personNumber = bl.getCustomerPNumber(pos);
					ssn.setText(personNumber);
				}
			}
		});

		firstname = new JTextField(10);
		surname = new JTextField(10);
		ssn = new JTextField(10);
		firstname.setBorder(BorderFactory.createTitledBorder("Förnamn"));
		surname.setBorder(BorderFactory.createTitledBorder("Efternamn"));
		ssn.setBorder(BorderFactory.createTitledBorder("Personnummer"));
		inputPanel.add(firstname);
		inputPanel.add(surname);
		inputPanel.add(ssn);

		JButton addBtn = new JButton("Lägg till");
		JButton deleteBtn = new JButton("Ta bort kund");
		JButton changeBtn = new JButton("Ändra namn");
		JButton showCustomerBtn = new JButton("Visa kund");
		JButton showAllBtn = new JButton("Visa alla kunder");
		textArea = new JTextArea(20, 30);

		addBtn.addActionListener(cl);
		deleteBtn.addActionListener(cl);
		changeBtn.addActionListener(cl);
		showCustomerBtn.addActionListener(cl);
		showAllBtn.addActionListener(cl);

		inputPanel.add(addBtn);
		inputPanel.add(deleteBtn);
		inputPanel.add(changeBtn);
		inputPanel.add(showCustomerBtn);
		inputPanel.add(showAllBtn);
		topPanel.add(inputPanel);
		textPanel.add(textArea);
		textPanel.add(customerList);
		topPanel.add(textPanel);
		customerPanel.add(topPanel);
		basePanel.add(customerPanel);

	}

	/**
	 * Skapar "Hantera konto"-vyn och adderar den till bas-panelen.
	 */
	public void createAccountPage() {
		al = new AccountListener();
		accountPanel = new JPanel(new BorderLayout());
		accountPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		JPanel inputPanel = new JPanel(new GridLayout(9, 1));
		createMenu();
		inputPanel.add(menuPanel);

		accountList = new JList();
		accountList.setBorder(BorderFactory.createTitledBorder("Bankens kunder"));

		ssnA = new JTextField(10);
		accountNumber = new JTextField(10);
		accountNumber.setBorder(BorderFactory.createTitledBorder("Kontonummer"));
		ssnA.setBorder(BorderFactory.createTitledBorder("Personnummer"));
		inputPanel.add(ssnA);
		inputPanel.add(accountNumber);

		JButton addSavingAccountBtn = new JButton("Skapa sparkonto");
		JButton addCreditAccountBtn = new JButton("Skapa kreditkonto");
		JButton showAccountBtn = new JButton("Visa konto");
		JButton deleteAccountBtn = new JButton("Avsluta konto");

		textAreaAccount = new JTextArea(20, 30);

		addSavingAccountBtn.addActionListener(al);
		addCreditAccountBtn.addActionListener(al);
		showAccountBtn.addActionListener(al);
		deleteAccountBtn.addActionListener(al);

		accountList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) {
					textAreaAccount.setText("");
					int position = accountList.getSelectedIndex();
					String pNumber = bl.getCustomerPNumber(position);
					List<String> list = bl.getCustomer(pNumber);
					for (String s : list) {
						textAreaAccount.append(s + "\n");
						ssnA.setText(pNumber);

					}
				}
			}
		});

		inputPanel.add(addSavingAccountBtn);
		inputPanel.add(addCreditAccountBtn);
		inputPanel.add(showAccountBtn);
		inputPanel.add(deleteAccountBtn);
		topPanel.add(inputPanel);
		textPanel.add(textAreaAccount);
		textPanel.add(accountList);
		topPanel.add(textPanel);
		accountPanel.add(topPanel);
		basePanel.add(accountPanel);
	}

	/**
	 * Skapar "Hantera transaktioner"-vyn och adderar den till bas-panelen.
	 */

	public void createTransactionPage() {
		tl = new TransactionListener();
		transactionPanel = new JPanel(new BorderLayout());
		transactionPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		JPanel inputPanel = new JPanel(new GridLayout(9, 1));
		createMenu();
		inputPanel.add(menuPanel);

		transactionList = new JList();
		transactionList.setBorder(BorderFactory.createTitledBorder("Bankens kunder"));
		ssnT = new JTextField(10);
		accountNumberTransaction = new JTextField(10);
		sum = new JTextField(10);
		accountNumberTransaction.setBorder(BorderFactory.createTitledBorder("Kontonummer"));
		ssnT.setBorder(BorderFactory.createTitledBorder("Personnummer"));
		sum.setBorder(BorderFactory.createTitledBorder("Summa"));
		inputPanel.add(ssnT);
		inputPanel.add(accountNumberTransaction);
		inputPanel.add(sum);

		transactionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) {
					textAreaTransactions.setText("");
					int position = transactionList.getSelectedIndex();
					String pNumber = bl.getCustomerPNumber(position);
					List<String> list = bl.getCustomer(pNumber);
					for (String s : list) {
						textAreaTransactions.append(s + "\n");
						ssnT.setText(pNumber);
					}

				}

			}
		});

		JButton depositBtn = new JButton("Sätt in pengar");
		JButton withdrawBtn = new JButton("Ta ut");
		JButton transactionBtn = new JButton("Visa transaktioner");
		JButton saveTransactions = new JButton("Spara transaktioner");

		textAreaTransactions = new JTextArea(20, 10);
		depositBtn.addActionListener(tl);
		withdrawBtn.addActionListener(tl);
		transactionBtn.addActionListener(tl);
		saveTransactions.addActionListener(tl);

		inputPanel.add(depositBtn);
		inputPanel.add(withdrawBtn);
		inputPanel.add(transactionBtn);
		inputPanel.add(saveTransactions);
		topPanel.add(inputPanel);
		textPanel.add(textAreaTransactions);
		textPanel.add(transactionList);
		topPanel.add(textPanel);

		transactionPanel.add(topPanel);
		basePanel.add(transactionPanel);
	}

	/**
	 * Metoden används av alla vyer och skapar en meny-panel. Meny panelen innehålle
	 * räven knappar för att spara och ladda bank.
	 */

	public void createMenu() {
		buttons = new JPanel(new GridLayout(2, 1));
		JButton save = new JButton("Spara bank");
		save.addActionListener(welcomeListener);
		JButton getBank = new JButton("Ladda bank");
		getBank.addActionListener(welcomeListener);
		buttons.add(save);
		buttons.add(getBank);
		textPanel = new JPanel(new GridLayout(2, 1));
		menuPanel = new JPanel(new GridLayout(1, 2));
		JMenuBar menuBar = new JMenuBar();
		JMenu customerMenu = new JMenu("Meny");
		JMenuItem customerMenuItem1 = new JMenuItem("Hantera kunder");
		JMenuItem customerMenuItem2 = new JMenuItem("Hantera konton");
		JMenuItem customerMenuItem3 = new JMenuItem("Hantera transaktioner");
		customerMenuItem1.addActionListener(welcomeListener);
		customerMenuItem2.addActionListener(welcomeListener);
		customerMenuItem3.addActionListener(welcomeListener);
		customerMenu.add(customerMenuItem1);
		customerMenu.add(customerMenuItem2);
		customerMenu.add(customerMenuItem3);
		menuBar.add(customerMenu);
		menuPanel.add(menuBar);
		menuPanel.add(buttons);
	}

	/**
	 * MEtod för att visa startsidan.
	 */

	public void welcomePage() {
		customerPanel.setVisible(false);
		accountPanel.setVisible(false);
		transactionPanel.setVisible(false);
		startPanel.setVisible(true);
	}

	/**
	 * Metod för att visa "Hantera kund" sidan. Sätter även synligheten för
	 * knapparna spara och ladda bank till true. Uppdaterar jListen till de kunder
	 * som finns i bankLogics customerList.
	 */
	public void customerPage() {
		customerList.setListData(bl.getAllCustomers().toArray());
		startPanel.setVisible(false);
		accountPanel.setVisible(false);
		transactionPanel.setVisible(false);
		customerPanel.setVisible(true);
		buttons.setVisible(true);

	}

	/**
	 * Metod för att visa "Hantera konton" sidan. Sätter även synligheten för
	 * knapparna spara och ladda bank till true. Uppdaterar jListen till de kunder
	 * som finns i bankLogics customerList.
	 */

	public void accountPage() {
		accountList.setListData(bl.getAllCustomers().toArray());
		startPanel.setVisible(false);
		customerPanel.setVisible(false);
		transactionPanel.setVisible(false);
		accountPanel.setVisible(true);
		buttons.setVisible(true);

	}

	/**
	 * Metod för att visa "Hantera transaktioner" sidan. Sätter även synligheten för
	 * spara och ladda bank till true. Uppdaterar jListen till de kunder som finns i
	 * bankLogics customerList.
	 */
	public void transactionPage() {
		transactionList.setListData(bl.getAllCustomers().toArray());
		startPanel.setVisible(false);
		customerPanel.setVisible(false);
		accountPanel.setVisible(false);
		transactionPanel.setVisible(true);
		buttons.setVisible(true);

	}

	/**
	 * Metod för att rensa alla textfält. Beroende på vilket fält som är satt till
	 * synligt rensas olika fält. Rensar även valet man gjort i jList.
	 */

	public void clear() {
		if (customerPanel.isVisible()) {
			customerList.setListData(bl.getAllCustomers().toArray());
			firstname.setText("");
			surname.setText("");
			ssn.setText("");
		} else if (accountPanel.isVisible()) {
			accountList.setListData(bl.getAllCustomers().toArray());
			ssnA.setText("");
			accountNumber.setText("");
			accountList.clearSelection();
		} else if (transactionPanel.isVisible()) {
			transactionList.setListData(bl.getAllCustomers().toArray());
			ssnT.setText("");
			accountNumberTransaction.setText("");
			sum.setText("");
			transactionList.clearSelection();
		}
	}

	/**
	 * Metod för att komma åt "createCustomer" i BankLogic och uppdatera
	 * customerList. Hämtar information från textfälten i GUI.
	 */

	public void addCustomer() {
		if (bl.createCustomer(firstname.getText(), surname.getText(), ssn.getText())) {
			clear();
		} else {
			JOptionPane.showMessageDialog(frame, "Kunden kunde inte läggas till");
		}
	}

	/**
	 * Metod för att komma åt "deleteCustomer" i BankLogic och uppdatera
	 * customerList. Hämtar information från textfälten i GUI.
	 */

	public void deleteCustomer() {
		textArea.setText("");
		List<String> customer = bl.deleteCustomer(ssn.getText());
		if (!customer.isEmpty()) {
			clear();
			for (String s : customer) {
				textArea.append(s + "\n");
			}
		}
	}

	/**
	 * Metod för att komma åt "changeCustomerName" i BankLogic och uppdatera
	 * customerList. Hämtar information från textfälten i GUI.
	 */
	public void changeName() {
		bl.changeCustomerName(firstname.getText(), surname.getText(), ssn.getText());
		clear();
	}

	/**
	 * Metod för att komma åt "getCustomer" i BankLogic och skriva ut till textArea.
	 * Hämtar information från textfälten i GUI.
	 */
	public void showCustomer() {
		textArea.setText("");
		List<String> customer = bl.getCustomer(ssn.getText());
		for (String s : customer) {
			textArea.append(s + "\n");
		}
	}

	/**
	 * Metod för att komma åt "getAllCustomers i BankLogic och skriva ut till
	 * textArea.
	 */
	public void showAllCustomers() {
		textArea.setText("");
		List<String> customers = bl.getAllCustomers();
		for (String s : customers) {
			textArea.append(s + "\n");
		}
	}

	/**
	 * Metod för att komma åt "createSavingsAccount" i BankLogic. Hämtar information
	 * från textfälten i GUI och rensar fälten.
	 */

	public void createSavingsAccount() {
		int s = bl.createSavingsAccount(ssnA.getText());
		textAreaAccount.setText("" + s);
		clear();

	}

	/**
	 * Metod för att komma åt "createCreditAccount" i BankLogic. Hämtar information
	 * från textfälten i GUI och rensar fälten.
	 */

	public void createCreditAccount() {
		int s = bl.createCreditAccount(ssnA.getText());
		textAreaAccount.setText("" + s);
		clear();
	}

	/**
	 * Metod för att komma åt "getAccount" i BankLogic. Gör om strängen
	 * accountnumber till en int och hämtar personnummret från textfältet.
	 */

	public void showAccount() {
		try {
			int i = Integer.parseInt(accountNumber.getText());
			textAreaAccount.setText(bl.getAccount(ssnA.getText(), i));
			clear();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, formatExep);
		}
	}

	/**
	 * Metod för att komma åt "deleteAccount" i BankLogic. Gör om strängen
	 * accountnumber till en int och hämtar personnummret från textfältet.
	 */
	public void deleteAccount() {
		try {
			int i = Integer.parseInt(accountNumber.getText());
			textAreaAccount.setText(bl.closeAccount(ssnA.getText(), i));
			clear();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, formatExep);
		}

	}

	/**
	 * Metod för att komma åt "deposit" i BankLogic. Gör om strängarna accountnumber
	 * och summa till int och hämtar personnummret från textfältet. Rensar sedan
	 * alla fälten.
	 */
	public void deposit() {
		try {
			int accNumber = Integer.parseInt(accountNumberTransaction.getText());
			int s = Integer.parseInt(sum.getText());
			if (bl.deposit(ssnT.getText(), accNumber, s)) {
				textAreaTransactions.setText(bl.getAccount(ssnT.getText(), accNumber));
				clear();
			} else {
				JOptionPane.showMessageDialog(frame, wrongMatch);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, formatExep);
		}

	}

	/**
	 * Metod för att komma åt "withdraw" i BankLogic. Gör om strängarna
	 * accountnumber och summa till int och hämtar personnummret från textfältet.
	 * Rensar sedan alla fälten.
	 */
	public void withdraw() {
		try {
			int accNumber = Integer.parseInt(accountNumberTransaction.getText());
			int s = Integer.parseInt(sum.getText());
			if (bl.withdraw(ssnT.getText(), accNumber, s)) {
				textAreaTransactions.setText(bl.getAccount(ssnT.getText(), accNumber));
				clear();
			} else {
				JOptionPane.showMessageDialog(frame, wrongMatch);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, formatExep);
		}
	}

	/**
	 * Metod för att komma åt "getTransaction" i BankLogic. Gör om strängen
	 * accountnumber till int och hämtar personnummret från textfältet. Visar
	 * informationen i textArea och Rensar sedan alla fälten.
	 */
	public void showTransactions() {
		textAreaTransactions.setText("");
		try {
			int accNumber = Integer.parseInt(accountNumberTransaction.getText());
			List<String> list = bl.getTransactions(ssnT.getText(), accNumber);
			if (list == null) {
				JOptionPane.showMessageDialog(frame,
						"Inga transaktioner hittades, kontrollera personnummer och kontonummer");

			} else {
				for (String s : list) {
					textAreaTransactions.append(s + "\n");
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, formatExep);
		} finally {
			clear();
		}
	}

	/**
	 * Metod för att spara transaktioner
	 */

	public void saveTransactions() {
		BufferedWriter transactionfile = null;
		try {
			int accNumber = Integer.parseInt(accountNumberTransaction.getText());
			String pNumber = ssnT.getText();
			try {
				transactionfile = bl.saveTrans(accNumber, pNumber);
				if (transactionfile != null) {
					try {
						transactionfile.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(frame, formatExep);
					}
				} else {
					JOptionPane.showMessageDialog(frame, wrongMatch);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Kan inte hitta filen");
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Kunde inte spara filen");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, formatExep);
		}
	}

	/**
	 * Metod för att spara banken i bank logic.
	 */
	public void saveBank() {
		try {
			bl.saveBank();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Banken går inte att spara, kontrollera filen");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Banken går inte att spara, kontrollera filen");
		}

	}

	/**
	 * Metod för att hämtade den inladdade banken i Banklogic.
	 */
	public void loadBank() {
		try {
			bl.loadBank();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Ingen fil hittades");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Lyssnarklass till menyn.
	 */

	public class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Hantera kunder")) {
				customerPage();
			} else if (e.getActionCommand().equals("Hantera konton")) {
				accountPage();
			} else if (e.getActionCommand().equals("Hantera transaktioner")) {
				transactionPage();
			} else if (e.getActionCommand().equals("Spara bank")) {
				saveBank();
			} else if (e.getActionCommand().equals("Ladda bank")) {
				loadBank();
				clear();

			}

		}

	}

	/**
	 * Lyssnarklass till "hantera kund" vyn.
	 */
	public class CustomerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Lägg till")) {
				addCustomer();
			} else if (e.getActionCommand().equals("Ta bort kund")) {
				deleteCustomer();
			} else if (e.getActionCommand().equals("Ändra namn")) {
				changeName();
			} else if (e.getActionCommand().equals("Visa kund")) {
				showCustomer();
			} else if (e.getActionCommand().equals("Visa alla kunder")) {
				showAllCustomers();
			}

		}

	}

	/**
	 * Lyssnarklass till "hantera konto" vyn.
	 */
	public class AccountListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Skapa sparkonto")) {
				createSavingsAccount();
			} else if (e.getActionCommand().equals("Skapa kreditkonto")) {
				createCreditAccount();
			} else if (e.getActionCommand().equals("Visa konto")) {
				showAccount();
			} else if (e.getActionCommand().equals("Avsluta konto")) {
				deleteAccount();
			}

		}
	}

	/**
	 * Lyssnarklass till "hantera transaktioner" vyn.
	 */
	public class TransactionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Sätt in pengar")) {
				deposit();
			} else if (e.getActionCommand().equals("Ta ut")) {
				withdraw();
			} else if (e.getActionCommand().equals("Visa transaktioner")) {
				showTransactions();
			} else if (e.getActionCommand().equals("Spara transaktioner")) {
				saveTransactions();
			}

		}

	}

}
