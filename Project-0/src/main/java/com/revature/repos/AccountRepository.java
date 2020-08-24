package com.revature.repos;

import com.revature.models.AppUser;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

    public class AccountRepository {

        public AccountRepository(){
            System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        }

        public ArrayList<Integer> findUserAccounts(AppUser appUser){

            ArrayList<Integer> accountNumbers= new ArrayList<>();

            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

                String sql = "SELECT account_number FROM project_0.app_users__accounts_jt " +
                             "WHERE user_id = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, String.valueOf(appUser.getId()));

                System.out.println("[LOG] - prepared statement " + pstmt);
                //TODO remove breadcrumb

                ResultSet rs = pstmt.executeQuery();


            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            return accountNumbers;
        }
}
