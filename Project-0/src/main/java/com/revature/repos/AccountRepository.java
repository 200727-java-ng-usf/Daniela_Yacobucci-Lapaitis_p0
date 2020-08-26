package com.revature.repos;

import com.revature.models.Accounts.Account;
import com.revature.models.Accounts.CheckingAccount;
import com.revature.models.AppUser;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import static com.revature.AppDriver.app;

public class AccountRepository {

        public AccountRepository(){

        }


    /**
     * READ operation
     * Finds the account numbers of the accounts owned by a centain user and returns them in a HashSet
     * Used by findAccountsByAccountNumber. Separation needed because the Users and the accounds are
     * connected with a junction table, and for possible reusability of specific queries.
     * @param appUser
     * @return Optional<HashSet<Integer>>
     */
    public Optional<HashSet<Integer>> findAccountNumbersByAppUser(AppUser appUser){

            Optional<HashSet<Integer>> _AccountNumbers = Optional.empty();

            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

                String sql = "SELECT account_number FROM project_0.app_users__accounts_jt " +
                             "WHERE user_id = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, appUser.getId());


                ResultSet rs = pstmt.executeQuery();
                _AccountNumbers = Optional.of(mapResultSetOfAccountNumbers(rs));


            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            return _AccountNumbers;
        }

    /**
     * READ operation
     * Finds Accounts given a HashSet of their account numbers
     * Used by getAccountsOfCurrentUsers on userService
     * @param accountNumbers
     * @return Optional<HashSet<Account>>
     */
    public Optional<HashSet<Account>> findAccountsByAccountNumber(HashSet<Integer> accountNumbers) {

        HashSet<Account> Accounts = new HashSet<Account>();

        Iterator accountNumbersIterator = accountNumbers.iterator();
        Iterator accountsIterator = Accounts.iterator();


        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM project_0.accounts WHERE account_number = ?";
            PreparedStatement pstmt;

            ResultSet rs;
            while(accountNumbersIterator.hasNext()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, (Integer) accountNumbersIterator.next());
                 rs = pstmt.executeQuery();


                 //mapResultSet(rs).stream().findFirst()
                Accounts.add(mapResultSetOfAccount(rs));
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return Optional.of(Accounts);

    }

    /**
     * UPDATE operation
     * Updates the balance of the account with the given account number
     * @param balance
     * @param accountNumber
     * @return
     */
    public Optional<Account> updateBalanceInDatabase(double balance, int accountNumber) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            System.out.println("the balance "+ balance + "the account number " + accountNumber);

            String sql = "UPDATE project_0.accounts SET balance = ? WHERE account_number = ?";


            // balance accountId
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();

            app.getCurrentUserAccounts();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /**
     * Convenience method to make READ operations more readable
     * in findAccountsByAccountNumber
     * @param rs
     * @return
     * @throws SQLException
     */
    private Account mapResultSetOfAccount(ResultSet rs) throws SQLException {

        CheckingAccount temp = new CheckingAccount();

        while (rs.next()) {

            temp.setAccountNumber(rs.getInt("account_number"));
            temp.setBalance(rs.getDouble("balance"));
        }


        return temp;

    }


    /**
     * Convenience method to make READ operations more readable
     * in findAccountNumbersByAppUser
     * @param rs
     * @return
     * @throws SQLException
     */
    private HashSet<Integer> mapResultSetOfAccountNumbers(ResultSet rs) throws SQLException {
        HashSet<Integer> accountNumbers = new HashSet<>();

        while (rs.next()) {
           accountNumbers.add(rs.getInt("account_number"));
        }

        return accountNumbers;
    }



}
