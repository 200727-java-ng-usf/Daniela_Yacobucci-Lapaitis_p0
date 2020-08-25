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

        private String baseQuery = "SELECT * FROM project_0.accounts ";

        public AccountRepository(){
            System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        }


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

    public Optional<HashSet<Account>> findAccountsByAccountNumber(HashSet<Integer> accountNumbers) {

        HashSet<Account> Accounts = new HashSet<Account>();

        Iterator accountNumbersIterator = accountNumbers.iterator();
        Iterator accountsIterator = Accounts.iterator();


        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE account_number = ?";
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

    private Account mapResultSetOfAccount(ResultSet rs) throws SQLException {

        CheckingAccount temp = new CheckingAccount();

        while (rs.next()) {

            temp.setAccountNumber(rs.getInt("account_number"));
            temp.setBalance(rs.getBigDecimal("balance"));
        }


        return temp;

    }

    private HashSet<Integer> mapResultSetOfAccountNumbers(ResultSet rs) throws SQLException {
        HashSet<Integer> accountNumbers = new HashSet<>();

        while (rs.next()) {
           accountNumbers.add(rs.getInt("account_number"));
        }

        return accountNumbers;
    }





}
