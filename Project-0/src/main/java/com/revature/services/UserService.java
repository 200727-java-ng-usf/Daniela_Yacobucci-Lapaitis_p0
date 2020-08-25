package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.Accounts.CheckingAccount;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.UserRepository;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Optional;

import static com.revature.AppDriver.app;

public class UserService {

    private UserRepository userRepo;

    public UserService (UserRepository repo) {
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        userRepo = repo;
    }

    public boolean hasMiddleName(String middleName){
        return (middleName.trim().equals("") ? false : true);
    }

    public void register(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user field values provided during registration!");
        }

        Optional<AppUser> existingUser = userRepo.findUserByUsername(newUser.getUsername());
        Optional<AppUser> existingEmail = userRepo.findUserByEmail(newUser.getEmail());

        if (existingUser.isPresent()) {
            throw new ResourcePersistenceException("Provided username is already in use!");
        }
        if (existingEmail.isPresent()) {
            throw new ResourcePersistenceException("Provided email is already in use!");
        }

        newUser.setRole(Role.BASIC_USER);
        userRepo.save(newUser);
        System.out.println(newUser);

    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        return true;
        // TODO add email validation
    }


    public void authenticate(String username, String password) {

        // validate that the provided username and password are not empty values

        try {
            if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
                throw new InvalidRequestException("Invalid credential values provided!");
            }

            AppUser authUser = userRepo.findUserByCredentials(username, password)
                    .orElseThrow(AuthenticationException::new);

            app.setCurrentUser(authUser);
            app.setCurrentUserAccounts(app.getAccountService().getAccountsOfCurrentUsers(authUser));
            
        } catch (InvalidRequestException | AuthenticationException ire) {
            System.err.println("Invalid login credentials provided!");
        }


        //TODO figure out which layer does exception handling
        //TODO understand AuthenticationException::new and check why it was not working

    }

    public AppUser getUserByUsername(String username) {
        return null;
    }
}