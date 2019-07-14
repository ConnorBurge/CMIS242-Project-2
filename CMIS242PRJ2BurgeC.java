/*
File Name: CMIS242PRJBurgeC.java
Author: Connor Burge
Class: CMIS 242
Date: June 15, 2019
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Parent class
 **/
// Parent class to hold all three classes
public class CMIS242PRJ2BurgeC {
  /**
   * ATM class
   **/
  private static class ATM extends JFrame {
    // Create each type of button
    JButton balance, withdraw, deposit, transfer, exit;
    JLabel accountNumber, transactionAmount;
    JTextField accountNum, transAmount;
    JRadioButton checking, savings;
    JOptionPane frame = new JOptionPane ();
    ButtonGroup radios = new ButtonGroup ();

    // Create each accounts array
    double[][] checkingAccounts = {{7623, 1200.00, 2}, {8729, 1700.00, 0}, {3242, 4612.00, 0}, {9823, 2345.00, 0},
            {2341, 5421.12, 2}, {8321, 56233.00, 0}};

    double[][] savingsAccounts = {{7623, 2000.00, 0}, {8729, 1000.00, 1}, {7321, 4500.00, 2}, {3242, 100.00, 3},
            {9823, 234.00, 2}, {2341, 12373.12, 1}, {8321, 2314.00, 0}};

    // Create string variables to be used during printArray() in the terminal
    String[] checkingStrings = {"Phyllis Jones", "Cletus Smith", "Sam Seybright", "Oscar Blue", "Sally Grief", "Jacues Matchel"};
    String[] savingsStrings = {"Phyllis Jones", "Cletus Smith", "Betty Booth", "Sam Seybright", "Oscar Blue", "Sally Grief", "Jacues Matchel"};

    // Define the decimal format
    DecimalFormat df = new DecimalFormat ("$0.00");

    // Action listener for the Deposit button
    class DepositButtonListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        try {
          deposit ();
        } catch (Exception e1) {
          errorValidNumber ();
        }
      }
    }
    // Action listener for the Withdraw button
    class WithdrawButtonListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        try {
          withdraw ();
        } catch (Exception e1) {
          errorValidNumber ();
        }
      }
    }
    // Action listener for the Transfer button
    class TransferButtonListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        try {
          transfer ();
        } catch (Exception e1) {
          errorNoNumber();
        }
      }
    }
    // Action listener for the Balance button
    class BalanceButtonListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        try {
          getAccountBalance ();
        } catch (Exception e1) {
          System.out.println ("Caught in BalanceButtonListener");
        }
      }
    }
    // Action listener for the Exit button
    class ExitButtonListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
        try {
          printArray ();
        } catch (Exception e1) {
          System.out.println ("Caught in ExitButtonListener");
        }
        System.exit (0);
      }
    }
    /**
     * ATM methods
     */
    private double accountBalance, accountNumb, transactionAm;
    private int match;

    /* getAccountBalance() parse's the accountNum text field and converts the String to a double to be used. Then, it checks
       to see to see which radio button is selected. If checking is selected, the for loop loops through the checking array
       until it finds the account number entered in accountNum text field. Wants it finds a match, it index's that element
       in the array and uses the same index for the account balance. Once the loop knows the balance, the balance is
       displayed in the JOptionPane message dialog box. The same process happens if the savings radio button is selected.
       If no number is entered, message dialog box is shown.
     */
    private void getAccountBalance() {
      try {
        accountNumb = Integer.parseInt (accountNum.getText ());
        if (checking.isSelected ()) {
          for (double[] checkingAccount : checkingAccounts) {
            double accountNo = checkingAccount[0];
            if (accountNo == accountNumb) {
              accountBalance = checkingAccount[1];
              match = 1;
            }
          }
        }
        else if (savings.isSelected ()) {
          for (double[] savingsAccount : savingsAccounts) {
            double accountNo = savingsAccount[0];
            if (accountNo == accountNumb) {
              accountBalance = savingsAccount[1];
              match = 1;
            }
          }
        }
        if (match != 1) {
          invalidEntry ();
        } else {
          JOptionPane.showMessageDialog (frame, "Account Balance: " + df.format (accountBalance));
        }
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog (frame, "Enter a 4 Digit account number.");
        clearAccountNumber ();
      }
    }

    /* deposit() parse's the accountNum and transAmount text field and converts the Strings to a doubles to be used.
       Once it determines which radio button is selected and finds the index of the account, the deposit amount is added to
       the account balance. The new account balance is assigned to the indexed account balance. A JOptionPane dialog box
       is shown displaying the new balance. If an invalid number is entered in the transAmount text field, a error box
       is displayed.
     */
    private void deposit() {
      try {
        accountNumb = Integer.parseInt (accountNum.getText ());
        transactionAm = Double.parseDouble (transAmount.getText ());
        if (transactionAm > 0) {
          if (checking.isSelected ()) {
            for (int index = 0; index < checkingAccounts.length; index++) {
              double accountNo = checkingAccounts[index][0];
              if (accountNo == accountNumb) {
                accountBalance = checkingAccounts[index][1];
                accountBalance = accountBalance + transactionAm;
                checkingAccounts[index][1] = accountBalance;
                match = 1;
              }
            }
            clearEntryValue ();
          } else if (savings.isSelected ()) {
            for (int index = 0; index < savingsAccounts.length; index++) {
              double accountNo = savingsAccounts[index][0];
              if (accountNo == accountNumb) {
                accountBalance = savingsAccounts[index][1];
                accountBalance = accountBalance + transactionAm;
                savingsAccounts[index][1] = accountBalance;
                match = 1;
              }
            }
            clearEntryValue ();
          }
          if (match != 1) {
            invalidEntry ();
          } else {
            JOptionPane.showMessageDialog (frame, "Account Balance: " + df.format (accountBalance));
          }
        } else {
          JOptionPane.showMessageDialog (frame, "Enter Amount Larger than 0");
        }
      } catch (Exception e) {
        errorNoNumber ();
      }
    }

    /* withdraw() parse's the accountNum and transAmount text field and converts the Strings to a doubles to be used.
       Once it determines which radio button is selected and finds the index of the account, the withdraw amount is subtracted
       from the account balance. The new account balance is assigned to the indexed account balance. It counts the number of
       transactions made within that account. A JOptionPane dialog box is shown displaying the new balance. If an invalid
       number is entered in the transAmount text field, a error box is displayed.
     */
    private void withdraw() {
      try {
        accountNumb = Integer.parseInt (accountNum.getText ());
        transactionAm = Double.parseDouble (transAmount.getText ());
        double fee = 0.0;
        if (transactionAm > 0 && transactionAm % 20 == 0) {
          if (checking.isSelected ()) {
            for (int index = 0; index < checkingAccounts.length; index++) {
              double accountNo = checkingAccounts[index][0];
              if (accountNo == accountNumb) {
                match = 1;
                accountBalance = checkingAccounts[index][1];
                checkingAccounts[index][2]++;
                if ((checkingAccounts[index][2] + savingsAccounts[index][2]) > 4) {
                  fee = 1.50;
                  JOptionPane.showMessageDialog (frame, "Service charge: $1.50");
                }
                accountBalance = (accountBalance - transactionAm) - fee;
                if (accountBalance < 1) {
                  accountBalance = (accountBalance + transactionAm) + fee;
                  throw new InsufficientFunds ();
                }
                checkingAccounts[index][1] = accountBalance;
              }
            }
          } else if (savings.isSelected ()) {
            for (int index = 0; index < savingsAccounts.length; index++) {
              double accountNo = savingsAccounts[index][0];
              if (accountNo == accountNumb) {
                match = 1;
                accountBalance = savingsAccounts[index][1];
                savingsAccounts[index][2]++;
                if ((checkingAccounts[index][2] + savingsAccounts[index][2]) > 4) {
                  fee = 1.50;
                  JOptionPane.showMessageDialog (frame, "Service charge: $1.50");
                }
                accountBalance = (accountBalance - transactionAm) - fee;
                if (accountBalance < 1) {
                  accountBalance = (accountBalance + transactionAm) + fee;
                  throw new InsufficientFunds ();
                }
                savingsAccounts[index][1] = accountBalance;
              }
            }
          }
          if (match != 1) {
            invalidEntry ();
          } else {
            JOptionPane.showMessageDialog (frame, "Account Balance: " + df.format (accountBalance));
          }
        } else {
          errorValidNumber ();
        }
        clearEntryValue ();
      } catch (InsufficientFunds e) {
        System.out.println ("Insufficient funds caught in withdraw()");
      }
    }

    /* transfer() parse's the accountNum and transAmount text field and converts the Strings to a doubles to be used.
       Once it determines which radio button is selected and finds the index of the account, the transfer amount is
       moved from the selected account to the other account. If an insufficient funds error is thrown, then the transaction
       reverses itself to display remain the previous balance. A JOptionPane dialog box is shown displaying the new balances.
       If an invalid number is entered in the transAmount text field, a error box is displayed.
     */
    private void transfer() {
      try {
        accountNumb = Integer.parseInt (accountNum.getText ());
        transactionAm = Double.parseDouble (transAmount.getText ());
        double checkingAccount;
        double savingsAccount;
        if (transactionAm > 0) {
          if (checking.isSelected ()) {
            for (int index = 0; index < checkingAccounts.length; index++) {
              double accountNo = checkingAccounts[index][0];
              if (accountNo == accountNumb) {
                match = 1;
                accountBalance = checkingAccounts[index][1];
                accountBalance = accountBalance - transactionAm;
                if (accountBalance < 1) {
                  throw new InsufficientFunds ();
                } else {
                  checkingAccounts[index][1] = accountBalance;
                  savingsAccounts[index][1] = savingsAccounts[index][1] + transactionAm;
                  savingsAccount = savingsAccounts[index][1];
                  JOptionPane.showMessageDialog (frame, "Checking Account " + df.format (accountBalance) + "\n" + "Savings Account: " + df.format (savingsAccount));
                }
              }
            }
          } else if (savings.isSelected ()) {
            for (int index = 0; index < savingsAccounts.length; index++) {
              double accountNo = savingsAccounts[index][0];
              if (accountNo == accountNumb) {
                match = 1;
                accountBalance = savingsAccounts[index][1];
                accountBalance = accountBalance - transactionAm;
                if (accountBalance < 1) {
                  throw new InsufficientFunds ();
                } else {
                  savingsAccounts[index][1] = accountBalance;
                  checkingAccounts[index][1] = checkingAccounts[index][1] + transactionAm;
                  checkingAccount = checkingAccounts[index][1];
                  JOptionPane.showMessageDialog (frame, "Savings Account " + df.format (accountBalance) + "\n" + "Checking Account: " + df.format (checkingAccount));
                }
              }
            }
          }
        } else {
          JOptionPane.showMessageDialog (frame, "Enter an amount larger than $0.00.");
        }
        clearEntryValue();
      } catch (InsufficientFunds e) {
        System.out.println ("Insufficient funds caught in transfer()");
      }
    }

    // Print the array on the terminal box
    private void printArray() {
      String format = "|%1$-4s|%2$-10s|%3$-20s|%4$-20s|%5$-5s|\n";
      System.out.println ("\n");
      System.out.format (format, "ACC#", "TYPE", "NAME", "BALANCE", "TRANS");
      System.out.println ("=================================================================");
      for (int i = 0; i < checkingAccounts.length; i++) {
        System.out.format (format, ((int) checkingAccounts[i][0]), "Checking", checkingStrings[i], df.format (checkingAccounts[i][1]), ((int) checkingAccounts[i][2]));
      }
      for (int i = 0; i < savingsAccounts.length; i++) {
        System.out.format (format, ((int) savingsAccounts[i][0]), "Savings", savingsStrings[i], df.format (savingsAccounts[i][1]), ((int) savingsAccounts[i][2]));
      }
    }

    private void errorValidNumber() {
      JOptionPane.showMessageDialog (frame, "Please use $20 increments.");
      clearEntryValue ();
    }

    private void errorNoNumber() {
      JOptionPane.showMessageDialog (frame, "Please enter a valid amount.");
      clearEntryValue ();
    }

    private void invalidEntry() {
      JOptionPane.showMessageDialog (frame, "Please enter a valid account number.");
      clearEntryValue ();
    }

    // Clears the text account number field
    private void clearAccountNumber() {
      accountNum.setText ("");
    }

    // Clears the text transaction field
    private void clearEntryValue() {
      transAmount.setText ("");
    }

    /**
     * ATM Constructor
     */
    private ATM() {

      setLayout (new GridBagLayout ());
      GridBagConstraints window = new GridBagConstraints ();
      window.insets = new Insets (5, 5, 5, 5);

      accountNumber = new JLabel ("Account Number:");
      accountNumber.setHorizontalAlignment (JLabel.RIGHT);
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 0;
      window.gridwidth = 1;
      add (accountNumber, window);

      accountNum = new JTextField (10);
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 1;
      window.gridy = 0;
      window.gridwidth = 3;
      add (accountNum, window);

      withdraw = new JButton ("Withdraw");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 1;
      window.gridwidth = 3;
      add (withdraw, window);

      deposit = new JButton ("Deposit");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 1;
      window.gridy = 1;
      window.gridwidth = 3;
      add (deposit, window);

      transfer = new JButton ("Transfer");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 2;
      window.gridwidth = 3;
      add (transfer, window);

      balance = new JButton ("Balance");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 1;
      window.gridy = 2;
      window.gridwidth = 3;
      add (balance, window);

      checking = new JRadioButton ("Checking");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 3;
      window.gridwidth = 3;
      add (checking, window);

      savings = new JRadioButton ("Savings");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 1;
      window.gridy = 3;
      window.gridwidth = 3;
      add (savings, window);

      transactionAmount = new JLabel ("Transaction Amount:");
      transactionAmount.setHorizontalAlignment (JLabel.RIGHT);
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 4;
      window.gridwidth = 1;
      add (transactionAmount, window);

      transAmount = new JTextField (10);
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 1;
      window.gridy = 4;
      window.gridwidth = 3;
      add (transAmount, window);

      exit = new JButton ("Exit");
      window.fill = GridBagConstraints.HORIZONTAL;
      window.gridx = 0;
      window.gridy = 5;
      window.gridwidth = 6;
      add (exit, window);

      savings.setSelected (true);
      radios.add (checking);
      radios.add (savings);

      balance.addActionListener (new BalanceButtonListener ());
      deposit.addActionListener (new DepositButtonListener ());
      withdraw.addActionListener (new WithdrawButtonListener ());
      transfer.addActionListener (new TransferButtonListener ());
      exit.addActionListener (new ExitButtonListener ());
      printArray ();
    }

    /**
     * Account class
     **/
    public static class Account extends ATM {

      public Account() {
      }
    } // End of Account
  } // End of ATM

  /**
   * InsufficientFunds exception
   **/
  private static class InsufficientFunds extends Exception {
    private InsufficientFunds() {
      JOptionPane frame = new JOptionPane ();
      JOptionPane.showMessageDialog (frame, "Insufficient Funds");
    } // End of InsufficientFunds()
  } // End of InsufficientFunds

  /**
   * main() method
   **/
  public static void main(String[] args) {

    ATM window = new ATM ();

    window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    window.setSize (400, 300);
    window.setTitle ("ATM MACHINE");
    window.setLocationRelativeTo (null);
    window.setVisible (true);
  }// End of main()
} // End of CMIS242PRJ2BurgeC