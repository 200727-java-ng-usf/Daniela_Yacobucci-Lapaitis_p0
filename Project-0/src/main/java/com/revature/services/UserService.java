package com.revature.services;

import com.revature.models.AppUser;
import com.revature.repos.UserRepository;

public class UserService {

    public UserService (UserRepository userRepo) {

    }

    public boolean checkForMiddleName(AppUser appUser){

        return (appUser.getMiddleName()==null ? false : true);
    }
}
