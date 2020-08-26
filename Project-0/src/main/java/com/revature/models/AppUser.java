package com.revature.models;

import com.revature.models.Accounts.Account;
import com.revature.models.Accounts.CheckingAccount;

import java.util.Objects;

public class AppUser {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;

    // TODO include check for middle name where necesary

    public AppUser() {
        super();
        System.out.println("[LOG] - Created user object");
        // TODO remove breadcrumb
    }
    //without role, middle name, and id
    public AppUser(String firstName, String lastName,
                   String username, String password, String email) {
        System.out.println("[LOG] - Created user object");
        // TODO remove breadcrumb
        this.id = id;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.LOCKED;
    }

    // without middle name, and id
    public AppUser(String firstName, String lastName,
                   String username, String password, String email, Role role) {
        this(firstName, lastName, username, password, email);
        this.role = role;
    }

    // without middle name
    public AppUser(Integer id, String firstName, String lastName,
                   String username, String password, String email, Role role) {
        this(firstName, lastName, username, password, email, role);
        this.id = id;
    }

    //without role and id
    public AppUser(String firstName, String middleName,
                   String lastName, String username, String password, String email) {
        this(firstName, lastName, username, password, email);
        this.middleName = middleName;
        this.role = Role.LOCKED;
    }

    // without id
    public AppUser(String firstName, String middleName, String lastName,
                   String username, String password, String email, Role role) {
        this(firstName, lastName, username, password, email, role);
        this.middleName = middleName;
    }

    // everything
    public AppUser(Integer id, String firstName, String middleName, String lastName,
                   String username, String password, String email, Role role) {
        this(firstName, lastName, username, password, email, role);
        this.middleName = middleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(middleName, appUser.middleName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password) &&
                Objects.equals(email, appUser.email) &&
                role == appUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, username, password, email, role);
    }

    @Override
    public String toString() {

        String appUserString = "id = " + id + ", firstName = " + firstName;

        if (middleName!=null) {
            appUserString = appUserString + ", middleName = " + middleName;
        }

        appUserString = appUserString +
                ", lastName = " + lastName +
                ", username = " + username +
                ", password = " + password +
                ", email = " + email +
                ", role = " + role;

        return appUserString;
    }
}
