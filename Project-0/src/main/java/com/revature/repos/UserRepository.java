package com.revature.repos;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepository {

    private String baseQuery = "SELECT * FROM Project_0.app_users au "
                             + "JOIN Project_0.user_roles ur "
                             + "USING (role_id)";



    public UserRepository() {

    }

    /**
     * READ operation
     * @param username
     * @param password
     * @return Optional<AppUser>
     */
    public Optional<AppUser> findUserByCredentials(String username, String password) {

        Optional<AppUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;
    }

    /**
     * READ operation
     * @param username
     * @return Optional<AppUser>
     */
    public Optional<AppUser> findUserByUsername(String username) {

        Optional<AppUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    /**
     * READ operation
     * @param email
     * @return Optional<AppUser>
     */
    public Optional<AppUser> findUserByEmail(String email) {

        Optional<AppUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    /**
     * Convenience method to make READ operations more readable
     * @param rs
     * @return Set<AppUser>
     * @throws SQLException
     */
    private Set<AppUser> mapResultSet(ResultSet rs) throws SQLException {

        Set<AppUser> users = new HashSet<>();

        while (rs.next()) {
            AppUser temp = new AppUser();
            temp.setId(rs.getInt("user_id"));
            temp.setFirstName(rs.getString("first_name"));
            //resolve middle name
            temp.setMiddleName(rs.getString("middle_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setEmail(rs.getString("email"));
            temp.setRole(Role.getByName(rs.getString("name")));
            users.add(temp);
        }

        return users;

    }

    /**
     * CREATE operation
     * @param newUser
     */
    public void save(AppUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql;

            if (newUser.getMiddleName()==null){
                sql = "INSERT INTO Project_0.app_users (username, password," +
                        "first_name, last_name, email, role_id)  " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            }else {
                sql = "INSERT INTO Project_0.app_users (username, password, first_name, " +
                        "last_name, email, role_id, middle_name) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                // TODO fix middle name tight coupling
            }

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"user_id"});
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt   (6, newUser.getRole().ordinal() + 1);

            if (newUser.getMiddleName()!=null) {
                pstmt.setString(7, newUser.getMiddleName());
            }

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys();

                rs.next();
                newUser.setId(rs.getInt(1));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }


}
