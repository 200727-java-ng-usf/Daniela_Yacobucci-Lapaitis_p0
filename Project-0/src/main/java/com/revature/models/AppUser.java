package com.revature.models;

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
    //without role and middle name
    public AppUser(Integer id, String firstName, String lastName,
                   String username, String password, String email) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.LOCKED;
    }

    // without middle name
    public AppUser(Integer id, String firstName, String lastName,
                   String username, String password, String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    //without role
    public AppUser(Integer id, String firstName, String middleName,
                   String lastName, String username, String password, String email) {
        this(id, firstName, lastName, username, password, email);
        this.middleName = middleName;
        this.role = Role.LOCKED;
    }

    // everything
    public AppUser(Integer id, String firstName, String middleName, String lastName,
                   String username, String password, String email, Role role) {
        this(id, firstName, lastName, username, password, email, role);
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
}
