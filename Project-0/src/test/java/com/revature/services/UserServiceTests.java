package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserServiceTests {


    private UserService sut;
    //Allows us to mock speicific methods within sut's class as needed
    private UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    private UserService sutSpy;

    AppUser userWithAllCredentials = new AppUser(1, "Pepita", "Delos", "Palotes", "ppalotes", "contrasena123", "ppalotes@pepamail.com", Role.LOCKED);
        AppUser userWithoutMiddleName = new AppUser(2, "Manny", "Josue", "manumanu", "pasapasa", "correo@email.com", Role.BASIC_USER);
        AppUser userWithoutFirstName = new AppUser(7, "", "Anderson", "aanderson", "password", "admin@app.com", "", Role.TELLER);


    @Before
    public void setup() {
        sut = new UserService(mockUserRepo);
        sutSpy = Mockito.spy(sut);
    }

    @After
    public void tearDown() {
        sut = null;
        sutSpy = null;
    }

    @Test
    public void userWithAllCredentialsHasMiddleName(){

        //Arrange
        boolean expectedResult = true;
        //Act
        boolean actualResult = sut.hasMiddleName(userWithAllCredentials.getMiddleName());
        //Assert
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void userWithoutMiddleNameDoesNotHaveMiddleName(){

        //Arrange
        boolean expectedResult = false;
        //Act
        boolean actualResult = sut.hasMiddleName(userWithoutMiddleName.getMiddleName());
        //Assert
        Assert.assertEquals(expectedResult, actualResult);
    }
    @Test
    public void userWithoutMiddleNameIsValid(){

        //Arrange
        boolean expectedResult = true;
        // Act
        boolean actualResult = sut.isUserValid(userWithoutMiddleName);
        // Assert
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void userWithFirstNameIsInvalid(){

        //Arrange
        boolean expectedResult = false;
        // Act
        boolean actualResult = sut.isUserValid(userWithoutFirstName);
        // Assert
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void userWithAllCredentialsIsValid(){

        //Arrange
        boolean expectedResult = true;
        // Act
        boolean actualResult = sut.isUserValid(userWithAllCredentials);
        // Assert
        Assert.assertEquals(expectedResult, actualResult);

    }


    @Test(expected = InvalidRequestException.class)
    public void authenticationWithInvalidCredentials() {

        // Arrange
        // nothing to do here for this test; nothing to mock or expect

        // Act
        sut.authenticate("", "");

        // Assert
        // nothing here, because the method should have raised an exception

    }

    // @Test(expected = InvalidRequestException.class)

    @Test(expected = AuthenticationException.class)
    public void authenticationWithUnknownCredentials() {
        sut.authenticate("garbage", "user");
    }

    @Test(expected = InvalidRequestException.class)
    public void registerWithInvalidUserWillRaiseException() {
        Mockito.doReturn(false).when(sutSpy).isUserValid(userWithoutFirstName);
        sutSpy.register(userWithoutFirstName);
    }
}
